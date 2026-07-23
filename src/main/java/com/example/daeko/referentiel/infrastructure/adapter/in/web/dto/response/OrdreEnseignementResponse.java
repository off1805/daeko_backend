package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public class OrdreEnseignementResponse {

    private UUID id;
    private String code;
    private String libelle;
    private String tutelleMinisterielle;
    private String tutelleMinisterielleEn;
    private Integer rang;
    private String description;
    private LocalDate dateEntreeVigueur;
    private String etat;


    public OrdreEnseignementResponse() {}


    public OrdreEnseignementResponse(UUID id, String code, String libelle, String tutelleMinisterielle,
                                     String tutelleMinisterielleEn, Integer rang, String description,
                                     LocalDate dateEntreeVigueur, String etat) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.tutelleMinisterielle = tutelleMinisterielle;
        this.tutelleMinisterielleEn = tutelleMinisterielleEn;
        this.rang = rang;
        this.description = description;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.etat = etat;
    }

    // Getters et Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

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

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}