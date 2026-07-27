package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request;

public class ModifierMatiereLocaleRequest {

    private String code;
    private String libelle;
    private String libelleCourt;
    private String libelleEn;
    private String domaine;
    private String typeMatiere;
    private Integer baremeParDefaut;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getLibelleCourt() { return libelleCourt; }
    public void setLibelleCourt(String libelleCourt) { this.libelleCourt = libelleCourt; }

    public String getLibelleEn() { return libelleEn; }
    public void setLibelleEn(String libelleEn) { this.libelleEn = libelleEn; }

    public String getDomaine() { return domaine; }
    public void setDomaine(String domaine) { this.domaine = domaine; }

    public String getTypeMatiere() { return typeMatiere; }
    public void setTypeMatiere(String typeMatiere) { this.typeMatiere = typeMatiere; }

    public Integer getBaremeParDefaut() { return baremeParDefaut; }
    public void setBaremeParDefaut(Integer baremeParDefaut) { this.baremeParDefaut = baremeParDefaut; }
}
