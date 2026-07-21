package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class ModifierMatiereCommand {

    private final UUID id;
    private final String libelle;
    private final String libelleEn;
    private final String description;
    private final UUID utilisateurId;

    public ModifierMatiereCommand(UUID id, String libelle, String libelleEn,
                                  String description, String requestDescription, UUID utilisateurId) {
        this.id = id;
        this.libelle = libelle;
        this.libelleEn = libelleEn;
        this.description = description;
        this.utilisateurId = utilisateurId;
    }

    public UUID getId() { return id; }
    public String getLibelle() { return libelle; }
    public String getLibelleEn() { return libelleEn; }
    public String getDescription() { return description; }
    public UUID getUtilisateurId() { return utilisateurId; }
}