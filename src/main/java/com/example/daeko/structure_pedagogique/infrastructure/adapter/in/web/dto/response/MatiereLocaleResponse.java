package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response;

import java.util.UUID;

public class MatiereLocaleResponse {

    private UUID id;
    private UUID brancheId;
    private String code;
    private String libelle;
    private String libelleCourt;
    private String libelleEn;
    private String domaine;
    private String typeMatiere;
    private int baremeParDefaut;
    private String etat;
    private String motifDepreciation;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getBrancheId() { return brancheId; }
    public void setBrancheId(UUID brancheId) { this.brancheId = brancheId; }

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

    public int getBaremeParDefaut() { return baremeParDefaut; }
    public void setBaremeParDefaut(int baremeParDefaut) { this.baremeParDefaut = baremeParDefaut; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public String getMotifDepreciation() { return motifDepreciation; }
    public void setMotifDepreciation(String motifDepreciation) { this.motifDepreciation = motifDepreciation; }
}
