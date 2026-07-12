package com.example.daeko.referentiel.domain.model;

import java.time.LocalDate;

/** Représente un ordre d'enseignement (ex : primaire, secondaire, supérieur). */
public class OrdreEnseignement extends EntiteReferentiel {

    private String code;
    private String libelle;
    private String tutelleMinisterielle;
    private String tutelleMinisterielleEn;
    private Integer rang;
    private String description;

    public OrdreEnseignement() {
        super();
    }

    public OrdreEnseignement(String code, String libelle, String tutelleMinisterielle,
                              String tutelleMinisterielleEn, Integer rang, String description,
                              LocalDate dateEntreeVigueur) {
        super();
        this.code = code;
        this.libelle = libelle;
        this.tutelleMinisterielle = tutelleMinisterielle;
        this.tutelleMinisterielleEn = tutelleMinisterielleEn;
        this.rang = rang;
        this.description = description;
        if (dateEntreeVigueur != null) setDateEntreeVigueur(dateEntreeVigueur);
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getTutelleMinisterielle() { return tutelleMinisterielle; }
    public void setTutelleMinisterielle(String tutelleMinisterielle) { this.tutelleMinisterielle = tutelleMinisterielle; }

    public String getTutelleMinisterielleEn() { return tutelleMinisterielleEn; }
    public void setTutelleMinisterielleEn(String tutelleMinisterielleEn) { this.tutelleMinisterielleEn = tutelleMinisterielleEn; }

    public Integer getRang() { return rang; }
    public void setRang(Integer rang) { this.rang = rang; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
