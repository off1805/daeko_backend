package com.example.daeko.structure_pedagogique.application.service;

import com.example.daeko.structure_pedagogique.application.dto.ResultatActivation;
import com.example.daeko.structure_pedagogique.application.port.in.SerieActiveUseCase;
import com.example.daeko.structure_pedagogique.application.port.out.AuditPort;
import com.example.daeko.structure_pedagogique.domain.exception.RessourceIntrouvableException;
import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;
import com.example.daeko.structure_pedagogique.domain.model.AuditEntreeStructure;
import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;
import com.example.daeko.structure_pedagogique.domain.model.FiliereActive;
import com.example.daeko.structure_pedagogique.domain.model.NiveauActive;
import com.example.daeko.structure_pedagogique.domain.model.OperationAuditSp;
import com.example.daeko.structure_pedagogique.domain.model.SerieActive;
import com.example.daeko.structure_pedagogique.domain.port.AnneeAcademiqueRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.ConfigurationBrancheAnneeRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.FiliereActiveRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.NiveauActiveRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.SerieActiveRepositoryPort;
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
public class SerieActiveApplicationService implements SerieActiveUseCase {

    private final SerieActiveRepositoryPort serieActiveRepositoryPort;
    private final NiveauActiveRepositoryPort niveauActiveRepositoryPort;
    private final FiliereActiveRepositoryPort filiereActiveRepositoryPort;
    private final ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort;
    private final BrancheRepositoryPort brancheRepositoryPort;
    private final AnneeAcademiqueRepositoryPort anneeAcademiqueRepositoryPort;
    private final StructureValidationService validationService;
    private final GardeEtablissementService gardeEtablissementService;
    private final GardeEtatConfigurationService gardeEtatConfigurationService;
    private final AuditPort auditPort;

    public SerieActiveApplicationService(SerieActiveRepositoryPort serieActiveRepositoryPort,
                                          NiveauActiveRepositoryPort niveauActiveRepositoryPort,
                                          FiliereActiveRepositoryPort filiereActiveRepositoryPort,
                                          ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort,
                                          BrancheRepositoryPort brancheRepositoryPort,
                                          AnneeAcademiqueRepositoryPort anneeAcademiqueRepositoryPort,
                                          StructureValidationService validationService,
                                          GardeEtablissementService gardeEtablissementService,
                                          GardeEtatConfigurationService gardeEtatConfigurationService,
                                          AuditPort auditPort) {
        this.serieActiveRepositoryPort = serieActiveRepositoryPort;
        this.niveauActiveRepositoryPort = niveauActiveRepositoryPort;
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
    public List<SerieActive> lister(UUID niveauActiveId, UUID etablissementId) {
        chargerNiveauActiveEtConfiguration(niveauActiveId, etablissementId);
        return serieActiveRepositoryPort.findByNiveauActive(niveauActiveId);
    }

    @Override
    @Transactional
    public ResultatActivation<SerieActive> definirEtatCible(UUID niveauActiveId, UUID etablissementId,
                                                             Set<UUID> serieIds, UUID acteur) {
        NiveauActive niveauActive = niveauActiveRepositoryPort.findById(niveauActiveId)
            .orElseThrow(() -> new RessourceIntrouvableException("NiveauActive", niveauActiveId));
        ConfigurationBrancheAnnee configuration = chargerNiveauActiveEtConfiguration(niveauActiveId, etablissementId);

        gardeEtablissementService.exigerEtablissementActif(etablissementId);
        gardeEtatConfigurationService.exigerConfigurationOuverte(configuration);

        AnneeAcademique annee = anneeAcademiqueRepositoryPort.findById(configuration.getAnneeAcademiqueId())
            .orElseThrow(() -> new RessourceIntrouvableException("AnneeAcademique", configuration.getAnneeAcademiqueId()));

        Set<UUID> filiereActiveIds = filiereActiveRepositoryPort.findByConfiguration(configuration.getId())
            .stream().map(FiliereActive::getFiliereId).collect(Collectors.toSet());

        List<SerieActive> actuelles = serieActiveRepositoryPort.findByNiveauActive(niveauActiveId);
        Map<UUID, SerieActive> parSerieId = actuelles.stream()
            .collect(Collectors.toMap(SerieActive::getSerieId, s -> s));

        DifferentielCalculator.Resultat diff = DifferentielCalculator.calculer(parSerieId.keySet(), serieIds);

        if (!diff.getARetirer().isEmpty()) {
            gardeEtatConfigurationService.exigerRetraitAutorise(annee);
        }

        List<SerieActive> ajoutees = new ArrayList<>();
        for (UUID serieId : diff.getAAjouter()) {
            validationService.validerSerieActive(niveauActive, serieId, filiereActiveIds);
            SerieActive nouvelle = serieActiveRepositoryPort.save(SerieActive.activer(niveauActiveId, serieId, acteur));
            ajoutees.add(nouvelle);

            auditPort.enregistrer(AuditEntreeStructure.nouvelle(
                etablissementId, OperationAuditSp.ACTIVATION_ELEMENT, "SerieActive", nouvelle.getId(),
                acteur, null, Map.of("serieId", serieId.toString()), null));
        }

        List<SerieActive> retirees = new ArrayList<>();
        for (UUID serieId : diff.getARetirer()) {
            SerieActive existante = parSerieId.get(serieId);
            serieActiveRepositoryPort.supprimer(existante.getId());
            retirees.add(existante);

            auditPort.enregistrer(AuditEntreeStructure.nouvelle(
                etablissementId, OperationAuditSp.RETRAIT_ELEMENT, "SerieActive", existante.getId(),
                acteur, Map.of("serieId", serieId.toString()), null, null));
        }

        List<SerieActive> inchangees = diff.getInchanges().stream().map(parSerieId::get).toList();

        return new ResultatActivation<>(ajoutees, List.of(), inchangees, retirees);
    }

    /** Remonte niveauActive -> configuration -> branche pour verifier le perimetre etablissement, et retourne la configuration. */
    private ConfigurationBrancheAnnee chargerNiveauActiveEtConfiguration(UUID niveauActiveId, UUID etablissementId) {
        NiveauActive niveauActive = niveauActiveRepositoryPort.findById(niveauActiveId)
            .orElseThrow(() -> new RessourceIntrouvableException("NiveauActive", niveauActiveId));

        ConfigurationBrancheAnnee configuration = configurationRepositoryPort.findById(niveauActive.getConfigurationId())
            .orElseThrow(() -> new RessourceIntrouvableException("NiveauActive", niveauActiveId));

        brancheRepositoryPort.findByIdEtEtablissement(configuration.getBrancheId(), etablissementId)
            .orElseThrow(() -> new RessourceIntrouvableException("NiveauActive", niveauActiveId));

        return configuration;
    }
}
