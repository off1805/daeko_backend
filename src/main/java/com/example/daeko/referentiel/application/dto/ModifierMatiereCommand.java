package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class ModifierMatiereCommand {

    private final UUID id;
    private final String libelle;
    private final String libelleCourt;
    private final String libelleEn;
    private final String description;
    private final UUID utilisateurId;

    public ModifierMatiereCommand(UUID id, String libelle, String libelleCourt, String libelleEn,
                                  String description, UUID utilisateurId) {
        this.id = id;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.description = description;
        this.utilisateurId = utilisateurId;
    }

    public UUID getId() { return id; }
    public String getLibelle() { return libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public String getLibelleEn() { return libelleEn; }
    public String getDescription() { return description; }
    public UUID getUtilisateurId() { return utilisateurId; }
}