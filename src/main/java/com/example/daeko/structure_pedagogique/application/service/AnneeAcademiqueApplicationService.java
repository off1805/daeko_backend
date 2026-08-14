package com.example.daeko.structure_pedagogique.application.service;

import com.example.daeko.structure_pedagogique.application.dto.CreerAnneeAcademiqueCommand;
import com.example.daeko.structure_pedagogique.application.port.in.AnneeAcademiqueUseCase;
import com.example.daeko.structure_pedagogique.application.port.out.AuditPort;
import com.example.daeko.structure_pedagogique.application.port.out.EvenementPublisherPort;
import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.RessourceIntrouvableException;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;
import com.example.daeko.structure_pedagogique.domain.model.AuditEntreeStructure;
import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;
import com.example.daeko.structure_pedagogique.domain.model.EvenementSortant;
import com.example.daeko.structure_pedagogique.domain.model.OperationAuditSp;
import com.example.daeko.structure_pedagogique.domain.port.AnneeAcademiqueRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.ConfigurationBrancheAnneeRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.service.GardeEtablissementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnneeAcademiqueApplicationService implements AnneeAcademiqueUseCase {

    private final AnneeAcademiqueRepositoryPort anneeAcademiqueRepositoryPort;
    private final ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort;
    private final GardeEtablissementService gardeEtablissementService;
    private final AuditPort auditPort;
    private final EvenementPublisherPort evenementPublisherPort;

    public AnneeAcademiqueApplicationService(AnneeAcademiqueRepositoryPort anneeAcademiqueRepositoryPort,
                                              ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort,
                                              GardeEtablissementService gardeEtablissementService,
                                              AuditPort auditPort,
                                              EvenementPublisherPort evenementPublisherPort) {
        this.anneeAcademiqueRepositoryPort = anneeAcademiqueRepositoryPort;
        this.configurationRepositoryPort = configurationRepositoryPort;
        this.gardeEtablissementService = gardeEtablissementService;
        this.auditPort = auditPort;
        this.evenementPublisherPort = evenementPublisherPort;
    }

    /**
     * Controles SP-012 : libelle unique par etablissement, dates coherentes,
     * et absence de chevauchement de dates avec une autre annee du meme etablissement.
     */
    @Override
    @Transactional
    public AnneeAcademique creer(CreerAnneeAcademiqueCommand commande) {
        gardeEtablissementService.exigerEtablissementActif(commande.getEtablissementId());

        if (anneeAcademiqueRepositoryPort.existsByEtablissementAndLibelle(commande.getEtablissementId(), commande.getLibelle())) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_012,
                "Une annee academique '" + commande.getLibelle() + "' existe deja pour cet etablissement."
            );
        }

        AnneeAcademique nouvelle = AnneeAcademique.creer(
            commande.getEtablissementId(), commande.getLibelle(), commande.getDateDebut(), commande.getDateFin(),
            commande.getActeur());

        List<AnneeAcademique> existantes = anneeAcademiqueRepositoryPort.findByEtablissement(commande.getEtablissementId());
        boolean chevauchement = existantes.stream().anyMatch(nouvelle::chevauche);
        if (chevauchement) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_012,
                "Les dates de cette annee chevauchent une autre annee academique existante de l'etablissement."
            );
        }

        AnneeAcademique sauvegardee = anneeAcademiqueRepositoryPort.save(nouvelle);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            sauvegardee.getEtablissementId(), OperationAuditSp.CREATION, "AnneeAcademique", sauvegardee.getId(),
            commande.getActeur(), null, Map.of("libelle", sauvegardee.getLibelle(), "etat", sauvegardee.getEtat().name()), null));

        return sauvegardee;
    }

    /**
     * Revalide SP-012 (aucune autre annee EN_COURS) en amont
     */
    @Override
    @Transactional
    public AnneeAcademique demarrer(UUID anneeId, UUID etablissementId, UUID acteur) {
        AnneeAcademique annee = charger(anneeId, etablissementId);
        gardeEtablissementService.exigerEtablissementActif(annee.getEtablissementId());

        anneeAcademiqueRepositoryPort.findEnCoursByEtablissement(etablissementId).ifPresent(autre -> {
            if (!autre.getId().equals(annee.getId())) {
                throw new StructureMetierException(
                    CodeErreurStructure.SP_012,
                    "Une autre annee (" + autre.getLibelle() + ") est deja EN_COURS pour cet etablissement."
                );
            }
        });

        annee.demarrer(acteur);
        AnneeAcademique sauvegardee = anneeAcademiqueRepositoryPort.save(annee);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            sauvegardee.getEtablissementId(), OperationAuditSp.DEMARRAGE_ANNEE, "AnneeAcademique", sauvegardee.getId(),
            acteur, null, Map.of("etat", sauvegardee.getEtat().name()), null));

        evenementPublisherPort.publier(EvenementSortant.de("structure.annee_demarree", Map.of(
            "annee_academique_id", sauvegardee.getId().toString(),
            "etablissement_id", sauvegardee.getEtablissementId().toString()
        )));

        return sauvegardee;
    }

    /**
     * Exige un champ de confirmation egal au libelle exact de l'annee. Scelle TOUTES
     * les configurations de l'annee dans la MEME transaction -- coeur du cas TC-21.
     */
    @Override
    @Transactional
    public AnneeAcademique cloturer(UUID anneeId, UUID etablissementId, String confirmation, UUID acteur) {
        AnneeAcademique annee = charger(anneeId, etablissementId);
        gardeEtablissementService.exigerEtablissementActif(annee.getEtablissementId());

        if (confirmation == null || !confirmation.equals(annee.getLibelle())) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_013,
                "La confirmation ne correspond pas au libelle exact de l'annee ('" + annee.getLibelle() + "')."
            );
        }

        // Peut lever SP-013 si l'annee est deja CLOTUREE
        annee.cloturer(acteur);
        AnneeAcademique anneeSauvegardee = anneeAcademiqueRepositoryPort.save(annee);

        List<ConfigurationBrancheAnnee> configurations = configurationRepositoryPort.findByAnneeAcademique(anneeId);
        for (ConfigurationBrancheAnnee configuration : configurations) {
            configuration.sceller(acteur); // idempotent : ne fait rien si deja SCELLEE
            configurationRepositoryPort.save(configuration);
        }

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            anneeSauvegardee.getEtablissementId(), OperationAuditSp.CLOTURE_ANNEE, "AnneeAcademique", anneeSauvegardee.getId(),
            acteur, null, Map.of(
                "etat", anneeSauvegardee.getEtat().name(),
                "configurations_scellees", configurations.size()
            ), null));

        List<String> idsConfigurations = configurations.stream()
            .map(c -> c.getId().toString()).collect(Collectors.toList());

        evenementPublisherPort.publier(EvenementSortant.de("structure.annee_cloturee", Map.of(
            "annee_academique_id", anneeSauvegardee.getId().toString(),
            "etablissement_id", anneeSauvegardee.getEtablissementId().toString(),
            "configurations_scellees", idsConfigurations
        )));

        return anneeSauvegardee;
    }

    @Override
    public AnneeAcademique consulterParId(UUID anneeId, UUID etablissementId) {
        return charger(anneeId, etablissementId);
    }

    @Override
    public List<AnneeAcademique> rechercher(UUID etablissementId) {
        return anneeAcademiqueRepositoryPort.findByEtablissement(etablissementId);
    }

    private AnneeAcademique charger(UUID anneeId, UUID etablissementId) {
        return anneeAcademiqueRepositoryPort.findByIdEtEtablissement(anneeId, etablissementId)
            .orElseThrow(() -> new RessourceIntrouvableException("AnneeAcademique", anneeId));
    }
}
