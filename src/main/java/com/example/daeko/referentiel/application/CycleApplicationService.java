package com.example.daeko.referentiel.application;

import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.ports.in.CycleUseCase;
import com.example.daeko.referentiel.application.dto.CreerCycleCommand;
import com.example.daeko.referentiel.application.dto.ModifierCycleCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCycleCommand;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CycleApplicationService implements CycleUseCase {

    public CycleApplicationService() {

    }

    @Override
    public Cycle creer(CreerCycleCommand command) {
        // Instanciation exacte selon le constructeur de l'entité Cycle
        Cycle cycle = new Cycle(
                command.getSousSystemeId(),
                command.getOrdreEnseignementId(),
                command.getCode(),
                command.getLibelle(),
                command.getLibelleEn(),
                command.getRang(),
                command.getDureeTheoriqueAnnees(),
                command.getDescription(),
                command.getDateEntreeVigueur()
        );

        return cycle;
    }

    @Override
    public Cycle modifier(ModifierCycleCommand command) {

        Cycle cycleModifie = new Cycle(
                command.getSousSystemeId(),
                command.getOrdreEnseignementId(),
                command.getCode(),
                command.getLibelle(),
                command.getLibelleEn(),
                command.getRang(),
                command.getDureeTheoriqueAnnees(),
                command.getDescription(),
                command.getDateEntreeVigueur()
        );

        return cycleModifie;
    }

    @Override
    public Cycle deprecier(DeprecierCycleCommand command) {

        Cycle cycleDeprecie = new Cycle(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "CODE_TEMP",
                "Libellé Temporaire",
                "Temporary Label",
                1,
                3,
                "Cycle déprécié",
                java.time.LocalDate.now()
        );

        return cycleDeprecie;
    }

    @Override
    @Transactional(readOnly = true)
    public Cycle consulterParId(UUID id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cycle> rechercher(EtatReferentiel etat) {
        return List.of();
    }
}