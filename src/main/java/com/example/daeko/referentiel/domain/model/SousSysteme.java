package com.example.daeko.referentiel.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class SousSysteme extends EntiteReferentiel {

    private String code;
    private String libelle;
    private String libelleCourt;
    private String description;
    private String languePrincipale;

    public SousSysteme() {
        super();
    }

    public SousSysteme(String code, String libelle, String libelleCourt,
                       String description, String languePrincipale,
                       LocalDate dateEntreeVigueur, UUID creePar) {
        super();
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.description = description;
        this.languePrincipale = languePrincipale;
        if (dateEntreeVigueur != null) setDateEntreeVigueur(dateEntreeVigueur);
        if (creePar != null) {
            setCreePar(creePar);
            setModifiePar(creePar);
        }
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getLibelleCourt() { return libelleCourt; }
    public void setLibelleCourt(String libelleCourt) { this.libelleCourt = libelleCourt; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLanguePrincipale() { return languePrincipale; }
    public void setLanguePrincipale(String languePrincipale) { this.languePrincipale = languePrincipale; }
}