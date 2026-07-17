package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class CreerOrdreEnseignementCommand {
    private final String code;
    private final String libelle;
    private final UUID utilisateurId;

    public CreerOrdreEnseignementCommand(String code, String libelle, UUID utilisateurId) {
        this.code = code;
        this.libelle = libelle;
        this.utilisateurId = utilisateurId;
    }

    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public UUID getUtilisateurId() { return utilisateurId; }
}