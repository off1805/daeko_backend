package com.example.daeko.structure_pedagogique.application.dto;

import java.util.UUID;

/** Commande d'entree du use case de creation de Branche -- classe simple, pas de record. */
public final class CreerBrancheCommand {
    private final UUID etablissementId;
    private final UUID sousSystemeId;
    private final UUID ordreEnseignementId;
    private final UUID typeEnseignementId;
    private final String libelle; // nullable : auto-genere si absent
    private final UUID acteur;

    public CreerBrancheCommand(UUID etablissementId, UUID sousSystemeId, UUID ordreEnseignementId,
                                UUID typeEnseignementId, String libelle, UUID acteur) {
        this.etablissementId = etablissementId;
        this.sousSystemeId = sousSystemeId;
        this.ordreEnseignementId = ordreEnseignementId;
        this.typeEnseignementId = typeEnseignementId;
        this.libelle = libelle;
        this.acteur = acteur;
    }

    public UUID getEtablissementId() { return etablissementId; }
    public UUID getSousSystemeId() { return sousSystemeId; }
    public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
    public UUID getTypeEnseignementId() { return typeEnseignementId; }
    public String getLibelle() { return libelle; }
    public UUID getActeur() { return acteur; }
}
