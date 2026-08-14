package com.example.daeko.structure_pedagogique.application.dto;

import java.util.UUID;

/** Commande de modification du libelle d'une Branche -- seul champ modifiable (SP-019). */
public final class ModifierLibelleBrancheCommand {
    private final UUID brancheId;
    private final UUID etablissementId;
    private final String nouveauLibelle;
    private final UUID acteur;

    public ModifierLibelleBrancheCommand(UUID brancheId, UUID etablissementId, String nouveauLibelle, UUID acteur) {
        this.brancheId = brancheId;
        this.etablissementId = etablissementId;
        this.nouveauLibelle = nouveauLibelle;
        this.acteur = acteur;
    }

    public UUID getBrancheId() { return brancheId; }
    public UUID getEtablissementId() { return etablissementId; }
    public String getNouveauLibelle() { return nouveauLibelle; }
    public UUID getActeur() { return acteur; }
}
