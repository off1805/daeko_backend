package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class ModifierNiveauCommand {

    private final UUID id;
    private final String libelle;
    private final String libelleCourt;
    private final String libelleEn;
    private final Integer ageTheoriqueDebut;
    private final String description;
    private final UUID utilisateurId;

    public ModifierNiveauCommand(UUID id, String libelle, String libelleCourt, String libelleEn,
                                 Integer ageTheoriqueDebut, String description, UUID utilisateurId) {
        this.id = id;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.ageTheoriqueDebut = ageTheoriqueDebut;
        this.description = description;
        this.utilisateurId = utilisateurId;
    }

    public UUID getId() { return id; }
    public String getLibelle() { return libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public String getLibelleEn() { return libelleEn; }
    public Integer getAgeTheoriqueDebut() { return ageTheoriqueDebut; }
    public String getDescription() { return description; }
    public UUID getUtilisateurId() { return utilisateurId; }
}