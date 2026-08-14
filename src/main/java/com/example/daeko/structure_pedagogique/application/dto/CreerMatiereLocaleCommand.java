package com.example.daeko.structure_pedagogique.application.dto;

import java.util.UUID;

public final class CreerMatiereLocaleCommand {
    private final UUID brancheId;
    private final UUID etablissementId;
    private final String code;
    private final String libelle;
    private final String libelleCourt;
    private final String libelleEn;
    private final String domaine;
    private final String typeMatiere;
    private final Integer baremeParDefaut;
    private final UUID acteur;

    public CreerMatiereLocaleCommand(UUID brancheId, UUID etablissementId, String code, String libelle,
                                      String libelleCourt, String libelleEn, String domaine, String typeMatiere,
                                      Integer baremeParDefaut, UUID acteur) {
        this.brancheId = brancheId;
        this.etablissementId = etablissementId;
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.domaine = domaine;
        this.typeMatiere = typeMatiere;
        this.baremeParDefaut = baremeParDefaut;
        this.acteur = acteur;
    }

    public UUID getBrancheId() { return brancheId; }
    public UUID getEtablissementId() { return etablissementId; }
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public String getLibelleEn() { return libelleEn; }
    public String getDomaine() { return domaine; }
    public String getTypeMatiere() { return typeMatiere; }
    public Integer getBaremeParDefaut() { return baremeParDefaut; }
    public UUID getActeur() { return acteur; }
}
