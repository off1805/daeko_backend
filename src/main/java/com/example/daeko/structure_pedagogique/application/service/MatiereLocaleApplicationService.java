package com.example.daeko.structure_pedagogique.application.service;

import com.example.daeko.structure_pedagogique.application.dto.CreerMatiereLocaleCommand;
import com.example.daeko.structure_pedagogique.application.dto.ModifierMatiereLocaleCommand;
import com.example.daeko.structure_pedagogique.application.port.in.MatiereLocaleUseCase;
import com.example.daeko.structure_pedagogique.application.port.out.AuditPort;
import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.RessourceIntrouvableException;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.model.AuditEntreeStructure;
import com.example.daeko.structure_pedagogique.domain.model.Branche;
import com.example.daeko.structure_pedagogique.domain.model.MatiereLocale;
import com.example.daeko.structure_pedagogique.domain.model.OperationAuditSp;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.MatiereActiveRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.MatiereLocaleRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.service.GardeEtablissementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MatiereLocaleApplicationService implements MatiereLocaleUseCase {

    private final BrancheRepositoryPort brancheRepositoryPort;
    private final MatiereLocaleRepositoryPort matiereLocaleRepositoryPort;
    private final MatiereActiveRepositoryPort matiereActiveRepositoryPort;
    private final GardeEtablissementService gardeEtablissementService;
    private final AuditPort auditPort;

    public MatiereLocaleApplicationService(BrancheRepositoryPort brancheRepositoryPort,
                                            MatiereLocaleRepositoryPort matiereLocaleRepositoryPort,
                                            MatiereActiveRepositoryPort matiereActiveRepositoryPort,
                                            GardeEtablissementService gardeEtablissementService,
                                            AuditPort auditPort) {
        this.brancheRepositoryPort = brancheRepositoryPort;
        this.matiereLocaleRepositoryPort = matiereLocaleRepositoryPort;
        this.matiereActiveRepositoryPort = matiereActiveRepositoryPort;
        this.gardeEtablissementService = gardeEtablissementService;
        this.auditPort = auditPort;
    }

    /** Unicite (branche_id, code). */
    @Override
    @Transactional
    public MatiereLocale creer(CreerMatiereLocaleCommand commande) {
        Branche branche = brancheRepositoryPort.findByIdEtEtablissement(commande.getBrancheId(), commande.getEtablissementId())
            .orElseThrow(() -> new RessourceIntrouvableException("Branche", commande.getBrancheId()));

        gardeEtablissementService.exigerEtablissementActif(branche.getEtablissementId());

        if (matiereLocaleRepositoryPort.existsByBrancheAndCode(commande.getBrancheId(), commande.getCode())) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_009,
                "Le code '" + commande.getCode() + "' est deja utilise par une autre matiere locale de cette branche."
            );
        }

        MatiereLocale matiere = MatiereLocale.creer(
            commande.getBrancheId(), commande.getCode(), commande.getLibelle(), commande.getLibelleCourt(),
            commande.getLibelleEn(), commande.getDomaine(), commande.getTypeMatiere(), commande.getBaremeParDefaut(),
            commande.getActeur());

        MatiereLocale sauvegardee = matiereLocaleRepositoryPort.save(matiere);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            branche.getEtablissementId(), OperationAuditSp.CREATION, "MatiereLocale", sauvegardee.getId(),
            commande.getActeur(), null, Map.of("code", sauvegardee.getCode(), "libelle", sauvegardee.getLibelle()), null));

        return sauvegardee;
    }

    /**
     * Le code reste modifiable tant que la matiere n'est activee dans aucune configuration
     */
    @Override
    @Transactional
    public MatiereLocale modifier(ModifierMatiereLocaleCommand commande) {
        MatiereLocale matiere = matiereLocaleRepositoryPort.findById(commande.getMatiereLocaleId())
            .orElseThrow(() -> new RessourceIntrouvableException("MatiereLocale", commande.getMatiereLocaleId()));

        gardeEtablissementService.exigerEtablissementActif(commande.getEtablissementId());

        String nouveauCode = commande.getNouveauCode();
        if (nouveauCode != null && !nouveauCode.equals(matiere.getCode())) {
            boolean dejaActivee = estActiveeQuelquePart(matiere.getId());
            if (dejaActivee) {
                throw new StructureMetierException(
                    CodeErreurStructure.SP_019,
                    "Le code de la matiere locale " + matiere.getId()
                        + " ne peut plus etre modifie : elle est deja activee dans au moins une configuration."
                );
            }
        }

        String ancienCode = matiere.getCode();
        matiere.modifierAttributs(nouveauCode, commande.getLibelle(), commande.getLibelleCourt(), commande.getLibelleEn(),
            commande.getDomaine(), commande.getTypeMatiere(), commande.getBaremeParDefaut(), commande.getActeur());

        MatiereLocale sauvegardee = matiereLocaleRepositoryPort.save(matiere);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            commande.getEtablissementId(), OperationAuditSp.MODIFICATION, "MatiereLocale", sauvegardee.getId(),
            commande.getActeur(), Map.of("code", ancienCode), Map.of("code", sauvegardee.getCode()), null));

        return sauvegardee;
    }

    /**
     * Motif obligatoire. La matiere reste utilisable la ou elle est deja activee ; plus
     * proposable ensuite (SP-020 applique lors de la prochaine tentative d'activation).
     */
    @Override
    @Transactional
    public MatiereLocale deprecier(UUID matiereLocaleId, UUID etablissementId, String motif, UUID acteur) {
        MatiereLocale matiere = matiereLocaleRepositoryPort.findById(matiereLocaleId)
            .orElseThrow(() -> new RessourceIntrouvableException("MatiereLocale", matiereLocaleId));

        gardeEtablissementService.exigerEtablissementActif(etablissementId);

        matiere.deprecier(motif, acteur);
        MatiereLocale sauvegardee = matiereLocaleRepositoryPort.save(matiere);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            etablissementId, OperationAuditSp.DEPRECIATION_MATIERE_LOCALE, "MatiereLocale", sauvegardee.getId(),
            acteur, null, Map.of("etat", sauvegardee.getEtat().name()), motif));

        return sauvegardee;
    }

    @Override
    public MatiereLocale consulterParId(UUID matiereLocaleId) {
        return matiereLocaleRepositoryPort.findById(matiereLocaleId)
            .orElseThrow(() -> new RessourceIntrouvableException("MatiereLocale", matiereLocaleId));
    }

    @Override
    public List<MatiereLocale> rechercher(UUID brancheId) {
        return matiereLocaleRepositoryPort.findByBranche(brancheId);
    }

    /**
     * NOTE : necessite en toute rigueur de parcourir tous les NiveauActive de
     * toutes les configurations de la branche pour verifier qu'aucune
     * MatiereActive ne pointe vers cette matiere locale. Une requete
     * dediee (findByMatiereLocaleId) sera ajoutee a MatiereActiveRepositoryPort
     * si ce parcours s'avere trop couteux en pratique --
     * a affiner au quand MatiereActive est pleinement implementee.
     */
    private boolean estActiveeQuelquePart(UUID matiereLocaleId) {
        return false; // TODO(jour 6) : requete reelle une fois MatiereActive cablee de bout en bout.
    }
}
