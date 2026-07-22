package com.example.daeko.referentiel.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CreerOrdreEnseignementCommand {

    private final String code;
    private final String libelle;
    private final String tutelleMinisterielle;
    private final String tutelleMinisterielleEn;
    private final Integer rang;
    private final String description;
    private final LocalDate dateEntreeVigueur;
    private final UUID utilisateurId;

    // Constructeur complet mis à jour
    public CreerOrdreEnseignementCommand(String code, String libelle, String tutelleMinisterielle,
                                         String tutelleMinisterielleEn, Integer rang, String description,
                                         LocalDate dateEntreeVigueur, UUID utilisateurId) {
        this.code = code;
        this.libelle = libelle;
        this.tutelleMinisterielle = tutelleMinisterielle;
        this.tutelleMinisterielleEn = tutelleMinisterielleEn;
        this.rang = rang;
        this.description = description;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.utilisateurId = utilisateurId;
    }

    // Getters
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getTutelleMinisterielle() { return tutelleMinisterielle; }
    public String getTutelleMinisterielleEn() { return tutelleMinisterielleEn; }
    public Integer getRang() { return rang; }
    public String getDescription() { return description; }
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public UUID getUtilisateurId() { return utilisateurId; }
}