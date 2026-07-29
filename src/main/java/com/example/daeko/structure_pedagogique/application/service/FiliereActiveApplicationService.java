package com.example.daeko.structure_pedagogique.application.service;

import com.example.daeko.structure_pedagogique.application.dto.ResultatActivation;
import com.example.daeko.structure_pedagogique.application.port.in.FiliereActiveUseCase;
import com.example.daeko.structure_pedagogique.application.port.out.AuditPort;
import com.example.daeko.structure_pedagogique.domain.exception.RessourceIntrouvableException;
import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;
import com.example.daeko.structure_pedagogique.domain.model.AuditEntreeStructure;
import com.example.daeko.structure_pedagogique.domain.model.Branche;
import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;
import com.example.daeko.structure_pedagogique.domain.model.FiliereActive;
import com.example.daeko.structure_pedagogique.domain.model.OperationAuditSp;
import com.example.daeko.structure_pedagogique.domain.port.AnneeAcademiqueRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.ConfigurationBrancheAnneeRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.FiliereActiveRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.service.GardeEtablissementService;
import com.example.daeko.structure_pedagogique.domain.service.GardeEtatConfigurationService;
import com.example.daeko.structure_pedagogique.domain.service.StructureValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FiliereActiveApplicationService implements FiliereActiveUseCase {

    private final FiliereActiveRepositoryPort filiereActiveRepositoryPort;
    private final ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort;
    private final BrancheRepositoryPort brancheRepositoryPort;
    private final AnneeAcademiqueRepositoryPort anneeAcademiqueRepositoryPort;
    private final StructureValidationService validationService;
    private final GardeEtablissementService gardeEtablissementService;
    private final GardeEtatConfigurationService gardeEtatConfigurationService;
    private final AuditPort auditPort;

    public FiliereActiveApplicationService(FiliereActiveRepositoryPort filiereActiveRepositoryPort,
                                            ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort,
                                            BrancheRepositoryPort brancheRepositoryPort,
                                            AnneeAcademiqueRepositoryPort anneeAcademiqueRepositoryPort,
                                            StructureValidationService validationService,
                                            GardeEtablissementService gardeEtablissementService,
                                            GardeEtatConfigurationService gardeEtatConfigurationService,
                                            AuditPort auditPort) {
        this.filiereActiveRepositoryPort = filiereActiveRepositoryPort;
        this.configurationRepositoryPort = configurationRepositoryPort;
        this.brancheRepositoryPort = brancheRepositoryPort;
        this.anneeAcademiqueRepositoryPort = anneeAcademiqueRepositoryPort;
        this.validationService = validationService;
        this.gardeEtablissementService = gardeEtablissementService;
        this.gardeEtatConfigurationService = gardeEtatConfigurationService;
        this.auditPort = auditPort;
    }

    @Override
    public List<FiliereActive> lister(UUID configurationId, UUID etablissementId) {
        chargerConfigurationEtBranche(configurationId, etablissementId);
        return filiereActiveRepositoryPort.findByConfiguration(configurationId);
    }

    @Override
    @Transactional
    public ResultatActivation<FiliereActive> definirEtatCible(UUID configurationId, UUID etablissementId,
                                                               Set<UUID> filiereIds, UUID acteur) {
        ConfigurationBrancheAnnee configuration = configurationRepositoryPort.findById(configurationId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));
        Branche branche = brancheRepositoryPort.findByIdEtEtablissement(configuration.getBrancheId(), etablissementId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));

        gardeEtablissementService.exigerEtablissementActif(etablissementId);
        gardeEtatConfigurationService.exigerConfigurationOuverte(configuration);

        AnneeAcademique annee = anneeAcademiqueRepositoryPort.findById(configuration.getAnneeAcademiqueId())
            .orElseThrow(() -> new RessourceIntrouvableException("AnneeAcademique", configuration.getAnneeAcademiqueId()));

        List<FiliereActive> actuelles = filiereActiveRepositoryPort.findByConfiguration(configurationId);
        Map<UUID, FiliereActive> parFiliereId = actuelles.stream()
            .collect(Collectors.toMap(FiliereActive::getFiliereId, f -> f));

        DifferentielCalculator.Resultat diff = DifferentielCalculator.calculer(parFiliereId.keySet(), filiereIds);

        if (!diff.getARetirer().isEmpty()) {
            gardeEtatConfigurationService.exigerRetraitAutorise(annee);
        }

        List<FiliereActive> ajoutees = new ArrayList<>();
        for (UUID filiereId : diff.getAAjouter()) {
            validationService.validerFiliereActive(branche, filiereId);
            FiliereActive nouvelle = filiereActiveRepositoryPort.save(FiliereActive.activer(configurationId, filiereId, acteur));
            ajoutees.add(nouvelle);

            auditPort.enregistrer(AuditEntreeStructure.nouvelle(
                etablissementId, OperationAuditSp.ACTIVATION_ELEMENT, "FiliereActive", nouvelle.getId(),
                acteur, null, Map.of("filiereId", filiereId.toString()), null));
        }

        List<FiliereActive> retirees = new ArrayList<>();
        for (UUID filiereId : diff.getARetirer()) {
            FiliereActive existante = parFiliereId.get(filiereId);
            filiereActiveRepositoryPort.supprimer(existante.getId());
            retirees.add(existante);

            auditPort.enregistrer(AuditEntreeStructure.nouvelle(
                etablissementId, OperationAuditSp.RETRAIT_ELEMENT, "FiliereActive", existante.getId(),
                acteur, Map.of("filiereId", filiereId.toString()), null, null));
        }

        List<FiliereActive> inchangees = diff.getInchanges().stream().map(parFiliereId::get).toList();

        return new ResultatActivation<>(ajoutees, List.of(), inchangees, retirees);
    }

    private void chargerConfigurationEtBranche(UUID configurationId, UUID etablissementId) {
        ConfigurationBrancheAnnee configuration = configurationRepositoryPort.findById(configurationId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));
        brancheRepositoryPort.findByIdEtEtablissement(configuration.getBrancheId(), etablissementId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));
    }
}
