package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ModifierSousSystemeRequest {

    @NotBlank
    private String libelle;

    private String libelleCourt;

    private String description;

    public ModifierSousSystemeRequest() {}

    public ModifierSousSystemeRequest(String libelle, String libelleCourt, String description) {
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.description = description;
    }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public void setLibelleCourt(String libelleCourt) { this.libelleCourt = libelleCourt; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
