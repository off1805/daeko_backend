package com.example.daeko.referentiel.application.dto;

import java.util.UUID;

public class ModifierOrdreEnseignementCommand {
    private final UUID id;
    private final String libelle;

    public ModifierOrdreEnseignementCommand(UUID id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public UUID getId() { return id; }
    public String getLibelle() { return libelle; }
}