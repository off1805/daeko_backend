package com.example.daeko.structure_pedagogique.infrastructure.config;

import com.example.daeko.structure_pedagogique.application.port.out.EtablissementPort;
import com.example.daeko.structure_pedagogique.application.port.out.ReferentielPort;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;
import com.example.daeko.structure_pedagogique.domain.service.GardeEtablissementService;
import com.example.daeko.structure_pedagogique.domain.service.GardeEtatConfigurationService;
import com.example.daeko.structure_pedagogique.domain.service.StructureValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfigStructurePedagogique {

    @Bean
    public GardeEtablissementService gardeEtablissementService(EtablissementPort etablissementPort) {
        return new GardeEtablissementService(etablissementPort);
    }

    @Bean
    public GardeEtatConfigurationService gardeEtatConfigurationService() {
        return new GardeEtatConfigurationService();
    }

    @Bean
    public StructureValidationService structureValidationService(BrancheRepositoryPort brancheRepositoryPort,
                                                                   ReferentielPort referentielPort) {
        return new StructureValidationService(brancheRepositoryPort, referentielPort);
    }
}
