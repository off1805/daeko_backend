package com.example.daeko.referentiel.domain.model;

import java.time.LocalDate;

public class TypeEnseignement extends EntiteReferentiel {

    private String code;
    private String libelle;
    private String description;

    public TypeEnseignement() {
        super();
    }

    public TypeEnseignement(String code, String libelle, String description,
                             LocalDate dateEntreeVigueur) {
        super();
        this.code = code;
        this.libelle = libelle;
        this.description = description;
        if (dateEntreeVigueur != null) setDateEntreeVigueur(dateEntreeVigueur);
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
