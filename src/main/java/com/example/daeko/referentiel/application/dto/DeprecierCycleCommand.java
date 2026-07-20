package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class DeprecierCycleCommand {

    private final UUID id;
    private final UUID utilisateurId;

    public DeprecierCycleCommand(UUID id, UUID utilisateurId) {
        this.id = id;
        this.utilisateurId = utilisateurId;
    }

    public UUID getId() { return id; }
    public UUID getUtilisateurId() { return utilisateurId; }
}