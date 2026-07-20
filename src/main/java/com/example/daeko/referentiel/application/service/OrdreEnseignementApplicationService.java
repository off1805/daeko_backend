package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.application.port.in.OrdreEnseignementUseCase;
import com.example.daeko.referentiel.application.dto.CreerOrdreEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ModifierOrdreEnseignementCommand;
import com.example.daeko.referentiel.application.dto.DeprecierOrdreEnseignementCommand;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrdreEnseignementApplicationService implements OrdreEnseignementUseCase {

    public OrdreEnseignementApplicationService() {
        // Constructeur temporaire
    }

    @Override
    public OrdreEnseignement creer(CreerOrdreEnseignementCommand command) {
        // Correspond exactement aux 7 paramètres de la bulle
        OrdreEnseignement ordre = new OrdreEnseignement(
                command.getCode(),
                command.getLibelle(),
                "Tutelle par défaut",           // tutelleMinisterielle
                "Default Tutelage",             // tutelleMinisterielleEn
                command.getRang(),
                "Création initiale",            // description
                command.getDateEntreeVigueur()
        );
        return ordre;
    }

    @Override
    public OrdreEnseignement modifier(ModifierOrdreEnseignementCommand command) {
        // Correspond exactement aux 7 paramètres de la bulle
        OrdreEnseignement ordreModifie = new OrdreEnseignement(
                command.getCode(),
                command.getLibelle(),
                "Tutelle modifiée",             // tutelleMinisterielle
                "Modified Tutelage",            // tutelleMinisterielleEn
                command.getRang(),
                "Modification",                 // description
                command.getDateEntreeVigueur()
        );
        return ordreModifie;
    }

    @Override
    public OrdreEnseignement deprecier(DeprecierOrdreEnseignementCommand command) {
        // Correspond exactement aux 7 paramètres de la bulle
        OrdreEnseignement ordreDeprecie = new OrdreEnseignement(
                "CODE_TEMP",
                "Libellé Temporaire",
                "Tutelle Temporaire",           // tutelleMinisterielle
                "Temporary Tutelage",           // tutelleMinisterielleEn
                1,
                "Ordre enseignement déprécié",  // description
                java.time.LocalDate.now()
        );
        return ordreDeprecie;
    }

    @Override
    @Transactional(readOnly = true)
    public OrdreEnseignement consulterParId(UUID id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdreEnseignement> rechercher(EtatReferentiel etat) {
        return List.of();
    }
}