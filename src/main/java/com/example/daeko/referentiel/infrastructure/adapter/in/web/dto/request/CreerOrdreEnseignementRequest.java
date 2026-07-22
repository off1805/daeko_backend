package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import java.time.LocalDate;

public class CreerOrdreEnseignementRequest {

    private String code;
    private String libelle;
    private String tutelleMinisterielle;
    private String tutelleMinisterielleEn;
    private Integer rang;
    private String description;
    private LocalDate dateEntreeVigueur;


    public CreerOrdreEnseignementRequest() {}

    // Getters et Setters
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

    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public void setDateEntreeVigueur(LocalDate dateEntreeVigueur) { this.dateEntreeVigueur = dateEntreeVigueur; }
}