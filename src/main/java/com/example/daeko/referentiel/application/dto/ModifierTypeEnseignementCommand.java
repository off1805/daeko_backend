package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class ModifierTypeEnseignementCommand {

    private final UUID id;
    private final String libelle;
    private final String description;
    private final UUID utilisateurId;

    public ModifierTypeEnseignementCommand(UUID id, String libelle, String description, UUID utilisateurId) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.utilisateurId = utilisateurId;
    }

    public UUID getId() { return id; }
    public String getLibelle() { return libelle; }
    public String getDescription() { return description; }
    public UUID getUtilisateurId() { return utilisateurId; }
}
