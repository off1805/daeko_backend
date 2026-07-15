package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class CreerSousSystemeCommand {

    private final String code;
    private final String libelle;
    private final String libelleCourt;
    private final String description;
    private final String languePrincipale;
    private final UUID utilisateurId;

    public CreerSousSystemeCommand(String code, String libelle, String libelleCourt,
                                   String description, String languePrincipale, UUID utilisateurId) {
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.description = description;
        this.languePrincipale = languePrincipale;
        this.utilisateurId = utilisateurId;
    }

    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public String getDescription() { return description; }
    public String getLanguePrincipale() { return languePrincipale; }
    public UUID getUtilisateurId() { return utilisateurId; }
}
