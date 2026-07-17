package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public class SousSystemeResponse {

    private UUID id;
    private String etat;
    private String code;
    private String libelle;
    private String libelleCourt;
    private String description;
    private String languePrincipale;
    private LocalDate dateEntreeVigueur;
    private LocalDate dateDepreciation;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
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
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public void setDateEntreeVigueur(LocalDate dateEntreeVigueur) { this.dateEntreeVigueur = dateEntreeVigueur; }
    public LocalDate getDateDepreciation() { return dateDepreciation; }
    public void setDateDepreciation(LocalDate dateDepreciation) { this.dateDepreciation = dateDepreciation; }
}
