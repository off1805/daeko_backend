package com.example.daeko.referentiel.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class MatiereReferentiel extends EntiteReferentiel {

    private UUID sousSystemeId;
    private String code;
    private String libelle;
    private String libelleCourt;
    private String libelleEn;
    private DomaineMatiere domaine;
    private TypeMatiereReferentiel typeMatiere;
    private Integer baremeParDefaut;
    private String description;

    public MatiereReferentiel() {
        super();
    }

    public MatiereReferentiel(UUID sousSystemeId, String code, String libelle, String libelleCourt,
                               String libelleEn, DomaineMatiere domaine, TypeMatiereReferentiel typeMatiere,
                               Integer baremeParDefaut, String description, LocalDate dateEntreeVigueur) {
        super();
        this.sousSystemeId = sousSystemeId;
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.domaine = domaine;
        this.typeMatiere = typeMatiere;
        this.baremeParDefaut = baremeParDefaut != null ? baremeParDefaut : 20;
        this.description = description;
        if (dateEntreeVigueur != null) setDateEntreeVigueur(dateEntreeVigueur);
    }

    public UUID getSousSystemeId() { return sousSystemeId; }
    public void setSousSystemeId(UUID sousSystemeId) { this.sousSystemeId = sousSystemeId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getLibelleCourt() { return libelleCourt; }
    public void setLibelleCourt(String libelleCourt) { this.libelleCourt = libelleCourt; }

    public String getLibelleEn() { return libelleEn; }
    public void setLibelleEn(String libelleEn) { this.libelleEn = libelleEn; }

    public DomaineMatiere getDomaine() { return domaine; }
    public void setDomaine(DomaineMatiere domaine) { this.domaine = domaine; }

    public TypeMatiereReferentiel getTypeMatiere() { return typeMatiere; }
    public void setTypeMatiere(TypeMatiereReferentiel typeMatiere) { this.typeMatiere = typeMatiere; }

    public Integer getBaremeParDefaut() { return baremeParDefaut; }
    public void setBaremeParDefaut(Integer baremeParDefaut) { this.baremeParDefaut = baremeParDefaut; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
