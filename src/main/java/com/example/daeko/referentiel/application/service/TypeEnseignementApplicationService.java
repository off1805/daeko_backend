package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.application.port.in.TypeEnseignementUseCase;
import com.example.daeko.referentiel.application.dto.CreerTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ModifierTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.DeprecierTypeEnseignementCommand;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TypeEnseignementApplicationService implements TypeEnseignementUseCase {

    public TypeEnseignementApplicationService() {

    }

    @Override
    public TypeEnseignement creer(CreerTypeEnseignementCommand command) {

        TypeEnseignement type = new TypeEnseignement(
                command.getCode(),
                command.getLibelle(),
                "Création initiale",
                command.getDateEntreeVigueur()
        );
        return type;
    }

    @Override
    public TypeEnseignement modifier(ModifierTypeEnseignementCommand command) {
        // 4 paramètres exacts : code, libelle, description, dateEntreeVigueur
        TypeEnseignement typeModifie = new TypeEnseignement(
                command.getCode(),
                command.getLibelle(),
                "Modification",
                command.getDateEntreeVigueur()
        );
        return typeModifie;
    }

    @Override
    public TypeEnseignement deprecier(DeprecierTypeEnseignementCommand command) {
        // 4 paramètres exacts : code, libelle, description, dateEntreeVigueur
        TypeEnseignement typeDeprecie = new TypeEnseignement(
                "CODE_TEMP",
                "Libellé Temporaire",
                "Type enseignement déprécié",
                java.time.LocalDate.now()
        );
        return typeDeprecie;
    }

    @Override
    @Transactional(readOnly = true)
    public TypeEnseignement consulterParId(UUID id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeEnseignement> rechercher(EtatReferentiel etat) {
        return List.of();
    }
}