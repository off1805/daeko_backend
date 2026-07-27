package com.example.daeko.structure_pedagogique.application.dto;

import java.util.UUID;

public final class CreerConfigurationCommand {
    private final UUID brancheId;
    private final UUID etablissementId;
    private final UUID anneeAcademiqueId;
    private final boolean dupliquerDepuisPrecedente;
    private final boolean copierClasses;
    private final UUID acteur;

    public CreerConfigurationCommand(UUID brancheId, UUID etablissementId, UUID anneeAcademiqueId,
                                      boolean dupliquerDepuisPrecedente, boolean copierClasses, UUID acteur) {
        this.brancheId = brancheId;
        this.etablissementId = etablissementId;
        this.anneeAcademiqueId = anneeAcademiqueId;
        this.dupliquerDepuisPrecedente = dupliquerDepuisPrecedente;
        this.copierClasses = copierClasses;
        this.acteur = acteur;
    }

    public UUID getBrancheId() { return brancheId; }
    public UUID getEtablissementId() { return etablissementId; }
    public UUID getAnneeAcademiqueId() { return anneeAcademiqueId; }
    public boolean isDupliquerDepuisPrecedente() { return dupliquerDepuisPrecedente; }
    public boolean isCopierClasses() { return copierClasses; }
    public UUID getActeur() { return acteur; }
}
