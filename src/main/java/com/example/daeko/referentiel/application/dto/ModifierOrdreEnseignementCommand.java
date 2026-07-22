package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class ModifierOrdreEnseignementCommand {

    private final UUID id;
    private final String libelle;
    private final String tutelleMinisterielle;
    private final String tutelleMinisterielleEn;
    private final String description;
    private final UUID utilisateurId;

    public ModifierOrdreEnseignementCommand(UUID id, String libelle, String tutelleMinisterielle,
                                            String tutelleMinisterielleEn, String description,
                                            UUID utilisateurId) {
        this.id = id;
        this.libelle = libelle;
        this.tutelleMinisterielle = tutelleMinisterielle;
        this.tutelleMinisterielleEn = tutelleMinisterielleEn;
        this.description = description;
        this.utilisateurId = utilisateurId;
    }

    public UUID getId() { return id; }
    public String getLibelle() { return libelle; }
    public String getTutelleMinisterielle() { return tutelleMinisterielle; }
    public String getTutelleMinisterielleEn() { return tutelleMinisterielleEn; }
    public String getDescription() { return description; }
    public UUID getUtilisateurId() { return utilisateurId; }
}