package com.example.daeko.structure_pedagogique.application.service;

import com.example.daeko.structure_pedagogique.application.dto.ResultatActivation;
import com.example.daeko.structure_pedagogique.application.port.in.NiveauActiveUseCase;
import com.example.daeko.structure_pedagogique.application.port.out.AuditPort;
import com.example.daeko.structure_pedagogique.domain.exception.RessourceIntrouvableException;
import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;
import com.example.daeko.structure_pedagogique.domain.model.AuditEntreeStructure;
import com.example.daeko.structure_pedagogique.domain.model.Branche;
import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;
import com.example.daeko.structure_pedagogique.domain.model.NiveauActive;
import com.example.daeko.structure_pedagogique.domain.model.OperationAuditSp;
import com.example.daeko.structure_pedagogique.domain.model.SerieActive;
import com.example.daeko.structure_pedagogique.domain.port.AnneeAcademiqueRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.port.ConfigurationBrancheAnneeRepositoryPort;
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
public class NiveauActiveApplicationService implements NiveauActiveUseCase {

    private final NiveauActiveRepositoryPort niveauActiveRepositoryPort;
    private final SerieActiveRepositoryPort serieActiveRepositoryPort;
    private final ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort;
    private final BrancheRepositoryPort brancheRepositoryPort;
    private final AnneeAcademiqueRepositoryPort anneeAcademiqueRepositoryPort;
    private final StructureValidationService validationService;
    private final GardeEtablissementService gardeEtablissementService;
    private final GardeEtatConfigurationService gardeEtatConfigurationService;
    private final AuditPort auditPort;

    public NiveauActiveApplicationService(NiveauActiveRepositoryPort niveauActiveRepositoryPort,
                                           SerieActiveRepositoryPort serieActiveRepositoryPort,
                                           ConfigurationBrancheAnneeRepositoryPort configurationRepositoryPort,
                                           BrancheRepositoryPort brancheRepositoryPort,
                                           AnneeAcademiqueRepositoryPort anneeAcademiqueRepositoryPort,
                                           StructureValidationService validationService,
                                           GardeEtablissementService gardeEtablissementService,
                                           GardeEtatConfigurationService gardeEtatConfigurationService,
                                           AuditPort auditPort) {
        this.niveauActiveRepositoryPort = niveauActiveRepositoryPort;
        this.serieActiveRepositoryPort = serieActiveRepositoryPort;
        this.configurationRepositoryPort = configurationRepositoryPort;
        this.brancheRepositoryPort = brancheRepositoryPort;
        this.anneeAcademiqueRepositoryPort = anneeAcademiqueRepositoryPort;
        this.validationService = validationService;
        this.gardeEtablissementService = gardeEtablissementService;
        this.gardeEtatConfigurationService = gardeEtatConfigurationService;
        this.auditPort = auditPort;
    }

    @Override
    public List<NiveauActive> lister(UUID configurationId, UUID etablissementId) {
        chargerConfigurationEtBranche(configurationId, etablissementId);
        return niveauActiveRepositoryPort.findByConfiguration(configurationId);
    }

    @Override
    @Transactional
    public ResultatActivation<NiveauActive> definirEtatCible(UUID configurationId, UUID etablissementId,
                                                              Set<UUID> niveauIds, UUID acteur) {
        ConfigurationBrancheAnnee configuration = configurationRepositoryPort.findById(configurationId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));
        Branche branche = brancheRepositoryPort.findByIdEtEtablissement(configuration.getBrancheId(), etablissementId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));

        gardeEtablissementService.exigerEtablissementActif(etablissementId);
        gardeEtatConfigurationService.exigerConfigurationOuverte(configuration);

        AnneeAcademique annee = anneeAcademiqueRepositoryPort.findById(configuration.getAnneeAcademiqueId())
            .orElseThrow(() -> new RessourceIntrouvableException("AnneeAcademique", configuration.getAnneeAcademiqueId()));

        List<NiveauActive> actuels = niveauActiveRepositoryPort.findByConfiguration(configurationId);
        Map<UUID, NiveauActive> parNiveauId = actuels.stream()
            .collect(Collectors.toMap(NiveauActive::getNiveauId, n -> n));

        DifferentielCalculator.Resultat diff = DifferentielCalculator.calculer(parNiveauId.keySet(), niveauIds);

        if (!diff.getARetirer().isEmpty()) {
            gardeEtatConfigurationService.exigerRetraitAutorise(annee);
        }

        List<NiveauActive> ajoutes = new ArrayList<>();
        for (UUID niveauId : diff.getAAjouter()) {
            validationService.validerNiveauActive(branche, niveauId);
            NiveauActive nouveau = niveauActiveRepositoryPort.save(NiveauActive.activer(configurationId, niveauId, acteur));
            ajoutes.add(nouveau);

            auditPort.enregistrer(AuditEntreeStructure.nouvelle(
                etablissementId, OperationAuditSp.ACTIVATION_ELEMENT, "NiveauActive", nouveau.getId(),
                acteur, null, Map.of("niveauId", niveauId.toString()), null));
        }

        List<NiveauActive> retires = new ArrayList<>();
        for (UUID niveauId : diff.getARetirer()) {
            NiveauActive existant = parNiveauId.get(niveauId);

            for (SerieActive serieOrpheline : serieActiveRepositoryPort.findByNiveauActive(existant.getId())) {
                serieActiveRepositoryPort.supprimer(serieOrpheline.getId());
                auditPort.enregistrer(AuditEntreeStructure.nouvelle(
                    etablissementId, OperationAuditSp.RETRAIT_ELEMENT, "SerieActive", serieOrpheline.getId(),
                    acteur, Map.of("serieId", serieOrpheline.getSerieId().toString()),
                    null, "Retire en cascade avec le niveau " + niveauId));
            }

            niveauActiveRepositoryPort.supprimer(existant.getId());
            retires.add(existant);

            auditPort.enregistrer(AuditEntreeStructure.nouvelle(
                etablissementId, OperationAuditSp.RETRAIT_ELEMENT, "NiveauActive", existant.getId(),
                acteur, Map.of("niveauId", niveauId.toString()), null, null));
        }

        List<NiveauActive> inchanges = diff.getInchanges().stream().map(parNiveauId::get).toList();

        return new ResultatActivation<>(ajoutes, List.of(), inchanges, retires);
    }

    private void chargerConfigurationEtBranche(UUID configurationId, UUID etablissementId) {
        ConfigurationBrancheAnnee configuration = configurationRepositoryPort.findById(configurationId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));
        brancheRepositoryPort.findByIdEtEtablissement(configuration.getBrancheId(), etablissementId)
            .orElseThrow(() -> new RessourceIntrouvableException("ConfigurationBrancheAnnee", configurationId));
    }
}
