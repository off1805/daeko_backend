package com.example.daeko.structure_pedagogique.application.dto;

import java.util.UUID;

public final class ModifierMatiereLocaleCommand {
    private final UUID matiereLocaleId;
    private final UUID etablissementId;
    private final String nouveauCode;
    private final String libelle;
    private final String libelleCourt;
    private final String libelleEn;
    private final String domaine;
    private final String typeMatiere;
    private final Integer baremeParDefaut;
    private final UUID acteur;

    public ModifierMatiereLocaleCommand(UUID matiereLocaleId, UUID etablissementId, String nouveauCode, String libelle,
                                         String libelleCourt, String libelleEn, String domaine, String typeMatiere,
                                         Integer baremeParDefaut, UUID acteur) {
        this.matiereLocaleId = matiereLocaleId;
        this.etablissementId = etablissementId;
        this.nouveauCode = nouveauCode;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.domaine = domaine;
        this.typeMatiere = typeMatiere;
        this.baremeParDefaut = baremeParDefaut;
        this.acteur = acteur;
    }

    public UUID getMatiereLocaleId() { return matiereLocaleId; }
    public UUID getEtablissementId() { return etablissementId; }
    public String getNouveauCode() { return nouveauCode; }
    public String getLibelle() { return libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public String getLibelleEn() { return libelleEn; }
    public String getDomaine() { return domaine; }
    public String getTypeMatiere() { return typeMatiere; }
    public Integer getBaremeParDefaut() { return baremeParDefaut; }
    public UUID getActeur() { return acteur; }
}
