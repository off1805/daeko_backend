package com.example.daeko.referentiel.infrastructure.config;

import com.example.daeko.referentiel.domain.port.CycleRepository;
import com.example.daeko.referentiel.domain.port.FiliereRepository;
import com.example.daeko.referentiel.domain.port.MatiereReferentielRepository;
import com.example.daeko.referentiel.domain.port.NiveauRepository;
import com.example.daeko.referentiel.domain.service.ReferentielValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfigReferentiel {

    @Bean
    public ReferentielValidationService referentielValidationService(
            NiveauRepository niveauRepository,
            CycleRepository cycleRepository,
            FiliereRepository filiereRepository,
            MatiereReferentielRepository matiereRepository) {
        return new ReferentielValidationService(
                niveauRepository, cycleRepository, filiereRepository, matiereRepository);
    }
}
