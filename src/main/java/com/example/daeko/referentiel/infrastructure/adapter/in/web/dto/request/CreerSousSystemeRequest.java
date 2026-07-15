package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreerSousSystemeRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String libelle;

    private String libelleCourt;

    private String description;

    @NotBlank
    @Size(min = 2, max = 2)
    private String languePrincipale;

    public CreerSousSystemeRequest() {}

    public CreerSousSystemeRequest(String code, String libelle, String libelleCourt,
                                   String description, String languePrincipale) {
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.description = description;
        this.languePrincipale = languePrincipale;
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
