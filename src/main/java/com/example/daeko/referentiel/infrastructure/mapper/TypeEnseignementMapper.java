package com.example.daeko.referentiel.infrastructure.mapper;

import com.example.daeko.referentiel.application.dto.CreerTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ModifierTypeEnseignementCommand;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.TypeEnseignementRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.TypeEnseignementResponse;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class TypeEnseignementMapper {

    // --- Vers les COMMANDS (Entrées de l'Application) ---

    public CreerTypeEnseignementCommand toCreerCommand(TypeEnseignementRequest request) {
        if (request == null) return null;

        return new CreerTypeEnseignementCommand(
                request.code(),
                request.libelle(),
                1, // Rang par défaut si besoin de le passer à la commande
                request.dateEntreeVigueur(),
                request.utilisateurId()
        );
    }

    public ModifierTypeEnseignementCommand toModifierCommand(UUID id, TypeEnseignementRequest request) {
        if (request == null) return null;

        return new ModifierTypeEnseignementCommand(
                id,
                request.code(),
                request.libelle(),
                1, // Rang par défaut
                request.dateEntreeVigueur(),
                request.utilisateurId()
        );
    }


    public TypeEnseignementResponse toResponse(TypeEnseignement domain) {
        if (domain == null) return null;

        return new TypeEnseignementResponse(
                domain.getCode(),
                domain.getLibelle(),
                domain.getDescription(),
                domain.getDateEntreeVigueur()
        );
    }
}