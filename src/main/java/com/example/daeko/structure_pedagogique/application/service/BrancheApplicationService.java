package com.example.daeko.structure_pedagogique.application.service;

import com.example.daeko.structure_pedagogique.application.dto.CreerBrancheCommand;
import com.example.daeko.structure_pedagogique.application.dto.ModifierLibelleBrancheCommand;
import com.example.daeko.structure_pedagogique.application.port.in.BrancheUseCase;
import com.example.daeko.structure_pedagogique.application.port.out.AuditPort;
import com.example.daeko.structure_pedagogique.application.port.out.EvenementPublisherPort;
import com.example.daeko.structure_pedagogique.domain.exception.RessourceIntrouvableException;
import com.example.daeko.structure_pedagogique.domain.model.AuditEntreeStructure;
import com.example.daeko.structure_pedagogique.domain.model.Branche;
import com.example.daeko.structure_pedagogique.domain.model.EvenementSortant;
import com.example.daeko.structure_pedagogique.domain.model.OperationAuditSp;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.service.GardeEtablissementService;
import com.example.daeko.structure_pedagogique.domain.service.StructureValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BrancheApplicationService implements BrancheUseCase {

    private final BrancheRepositoryPort brancheRepositoryPort;
    private final StructureValidationService validationService;
    private final GardeEtablissementService gardeEtablissementService;
    private final LibelleBrancheGenerator libelleBrancheGenerator;
    private final AuditPort auditPort;
    private final EvenementPublisherPort evenementPublisherPort;

    public BrancheApplicationService(BrancheRepositoryPort brancheRepositoryPort,
                                      StructureValidationService validationService,
                                      GardeEtablissementService gardeEtablissementService,
                                      LibelleBrancheGenerator libelleBrancheGenerator,
                                      AuditPort auditPort,
                                      EvenementPublisherPort evenementPublisherPort) {
        this.brancheRepositoryPort = brancheRepositoryPort;
        this.validationService = validationService;
        this.gardeEtablissementService = gardeEtablissementService;
        this.libelleBrancheGenerator = libelleBrancheGenerator;
        this.auditPort = auditPort;
        this.evenementPublisherPort = evenementPublisherPort;
    }

    /** Erreurs : SP-001, SP-020, SP-021. */
    @Override
    @Transactional
    public Branche creer(CreerBrancheCommand commande) {
        gardeEtablissementService.exigerEtablissementActif(commande.getEtablissementId());
        validationService.validerRacinesActives(
            commande.getSousSystemeId(), commande.getOrdreEnseignementId(), commande.getTypeEnseignementId());
        validationService.validerTripletBranche(
            commande.getEtablissementId(), commande.getSousSystemeId(), commande.getOrdreEnseignementId(),
            commande.getTypeEnseignementId());

        String libelle = commande.getLibelle() != null && !commande.getLibelle().isBlank()
            ? commande.getLibelle()
            : libelleBrancheGenerator.genererLibelle(
                commande.getSousSystemeId(), commande.getOrdreEnseignementId(), commande.getTypeEnseignementId());

        Branche branche = Branche.creer(
            commande.getEtablissementId(), commande.getSousSystemeId(), commande.getOrdreEnseignementId(),
            commande.getTypeEnseignementId(), libelle, commande.getActeur());

        Branche sauvegardee = brancheRepositoryPort.save(branche);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            sauvegardee.getEtablissementId(), OperationAuditSp.CREATION, "Branche", sauvegardee.getId(),
            commande.getActeur(), null, Map.of("libelle", libelle, "etat", sauvegardee.getEtat().name()), null));

        evenementPublisherPort.publier(EvenementSortant.de("structure.branche_creee", Map.of(
            "branche_id", sauvegardee.getId().toString(),
            "etablissement_id", sauvegardee.getEtablissementId().toString()
        )));

        return sauvegardee;
    }

    /** Seul le libelle est modifiable (SP-019). */
    @Override
    @Transactional
    public Branche modifierLibelle(ModifierLibelleBrancheCommand commande) {
        Branche branche = charger(commande.getBrancheId(), commande.getEtablissementId());
        gardeEtablissementService.exigerEtablissementActif(branche.getEtablissementId());

        String ancienLibelle = branche.getLibelle();
        branche.modifierLibelle(commande.getNouveauLibelle(), commande.getActeur());
        Branche sauvegardee = brancheRepositoryPort.save(branche);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            sauvegardee.getEtablissementId(), OperationAuditSp.MODIFICATION, "Branche", sauvegardee.getId(),
            commande.getActeur(), Map.of("libelle", ancienLibelle), Map.of("libelle", commande.getNouveauLibelle()), null));

        return sauvegardee;
    }

    @Override
    @Transactional
    public Branche activer(UUID brancheId, UUID etablissementId, UUID acteur) {
        Branche branche = charger(brancheId, etablissementId);
        gardeEtablissementService.exigerEtablissementActif(branche.getEtablissementId());

        branche.activer(acteur);
        Branche sauvegardee = brancheRepositoryPort.save(branche);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            sauvegardee.getEtablissementId(), OperationAuditSp.ACTIVATION_ELEMENT, "Branche", sauvegardee.getId(),
            acteur, null, Map.of("etat", sauvegardee.getEtat().name()), null));

        return sauvegardee;
    }

    @Override
    @Transactional
    public Branche suspendre(UUID brancheId, UUID etablissementId, UUID acteur) {
        Branche branche = charger(brancheId, etablissementId);
        gardeEtablissementService.exigerEtablissementActif(branche.getEtablissementId());

        branche.suspendre(acteur);
        Branche sauvegardee = brancheRepositoryPort.save(branche);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            sauvegardee.getEtablissementId(), OperationAuditSp.SUSPENSION, "Branche", sauvegardee.getId(),
            acteur, null, Map.of("etat", sauvegardee.getEtat().name()), null));

        return sauvegardee;
    }

    @Override
    @Transactional
    public Branche reactiver(UUID brancheId, UUID etablissementId, UUID acteur) {
        Branche branche = charger(brancheId, etablissementId);
        gardeEtablissementService.exigerEtablissementActif(branche.getEtablissementId());

        branche.reactiver(acteur);
        Branche sauvegardee = brancheRepositoryPort.save(branche);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            sauvegardee.getEtablissementId(), OperationAuditSp.REACTIVATION, "Branche", sauvegardee.getId(),
            acteur, null, Map.of("etat", sauvegardee.getEtat().name()), null));

        return sauvegardee;
    }

    /** POST /branches/{id}/archiver -- transition terminale (section 4.5). Motif obligatoire. */
    @Override
    @Transactional
    public Branche archiver(UUID brancheId, UUID etablissementId, String motif, UUID acteur) {
        if (motif == null || motif.isBlank()) {
            throw new com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException(
                com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure.SP_010,
                "Le motif d'archivage est obligatoire.");
        }

        Branche branche = charger(brancheId, etablissementId);
        gardeEtablissementService.exigerEtablissementActif(branche.getEtablissementId());

        branche.archiver(acteur);
        Branche sauvegardee = brancheRepositoryPort.save(branche);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            sauvegardee.getEtablissementId(), OperationAuditSp.ARCHIVAGE, "Branche", sauvegardee.getId(),
            acteur, null, Map.of("etat", sauvegardee.getEtat().name()), motif));

        return sauvegardee;
    }

    @Override
    public Branche consulterParId(UUID brancheId, UUID etablissementId) {
        return charger(brancheId, etablissementId);
    }

    @Override
    public List<Branche> rechercher(UUID etablissementId) {
        return brancheRepositoryPort.findByEtablissement(etablissementId);
    }

    private Branche charger(UUID brancheId, UUID etablissementId) {
        return brancheRepositoryPort.findByIdEtEtablissement(brancheId, etablissementId)
            .orElseThrow(() -> new RessourceIntrouvableException("Branche", brancheId));
    }
}
