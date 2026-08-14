package com.example.daeko.structure_pedagogique.application.service;

import com.example.daeko.structure_pedagogique.application.dto.CreerConfigurationCommand;
import com.example.daeko.structure_pedagogique.application.port.in.ConfigurationUseCase;
import com.example.daeko.structure_pedagogique.application.port.out.AuditPort;
import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.RessourceIntrouvableException;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.model.AuditEntreeStructure;
import com.example.daeko.structure_pedagogique.domain.model.Branche;
import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;
import com.example.daeko.structure_pedagogique.domain.model.OperationAuditSp;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.ConfigurationBrancheAnneeRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.service.GardeEtablissementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class ConfigurationApplicationService implements ConfigurationUseCase {

    private final BrancheRepositoryPort brancheRepositoryPort;
    private final ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort;
    private final GardeEtablissementService gardeEtablissementService;
    private final AuditPort auditPort;

    public ConfigurationApplicationService(BrancheRepositoryPort brancheRepositoryPort,
                                            ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort,
                                            GardeEtablissementService gardeEtablissementService,
                                            AuditPort auditPort) {
        this.brancheRepositoryPort = brancheRepositoryPort;
        this.configurationRepositoryPort = configurationRepositoryPort;
        this.gardeEtablissementService = gardeEtablissementService;
        this.auditPort = auditPort;
    }

    /** Erreurs : SP-013, SP-015, SP-018. */
    @Override
    @Transactional
    public ConfigurationBrancheAnnee creer(CreerConfigurationCommand commande) {
        Branche branche = brancheRepositoryPort.findByIdEtEtablissement(commande.getBrancheId(), commande.getEtablissementId())
            .orElseThrow(() -> new RessourceIntrouvableException("Branche", commande.getBrancheId()));

        gardeEtablissementService.exigerEtablissementActif(branche.getEtablissementId());
        branche.exigerActive(); // SP-018

        if (configurationRepositoryPort.findByBrancheEtAnnee(commande.getBrancheId(), commande.getAnneeAcademiqueId()).isPresent()) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_013,
                "Une configuration existe deja pour cette branche sur cette annee academique."
            );
        }

        if (commande.isDupliquerDepuisPrecedente()) {
            // Complete au jour 7 -- cf. DupliquerConfigurationUseCase (section 4.3 du dossier).
            throw new UnsupportedOperationException(
                "Duplication non disponible avant le jour 7 (DupliquerConfigurationUseCase). "
                    + "Utilisez dupliquerDepuisPrecedente=false pour une creation simple en attendant.");
        }

        ConfigurationBrancheAnnee configuration = ConfigurationBrancheAnnee.creer(
            commande.getBrancheId(), commande.getAnneeAcademiqueId(), commande.getActeur());
        ConfigurationBrancheAnnee sauvegardee = configurationRepositoryPort.save(configuration);

        auditPort.enregistrer(AuditEntreeStructure.nouvelle(
            branche.getEtablissementId(), OperationAuditSp.CREATION, "ConfigurationBrancheAnnee", sauvegardee.getId(),
            commande.getActeur(), null, Map.of("etat", sauvegardee.getEtat().name()), null));

        return sauvegardee;
    }

    @Override
    public ConfigurationBrancheAnnee consulterParId(UUID configurationId, UUID etablissementId) {
        ConfigurationBrancheAnnee configuration = configurationRepositoryPort.findById(configurationId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));

        brancheRepositoryPort.findByIdEtEtablissement(configuration.getBrancheId(), etablissementId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));

        return configuration;
    }
}
