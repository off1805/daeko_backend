package com.example.daeko.referentiel.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CreerCycleCommand {

    private final UUID sousSystemeId;
    private final UUID ordreEnseignementId;
    private final String code;
    private final String libelle;
    private final String libelleEn;
    private final Integer rang;
    private final Integer dureeTheoriqueAnnees;
    private final String description;
    private final LocalDate dateEntreeVigueur;
    private final UUID utilisateurId;

    public CreerCycleCommand(UUID sousSystemeId, UUID ordreEnseignementId, String code, String libelle,
                             String libelleEn, Integer rang, Integer dureeTheoriqueAnnees, String description,
                             LocalDate dateEntreeVigueur, UUID utilisateurId) {
        this.sousSystemeId = sousSystemeId;
        this.ordreEnseignementId = ordreEnseignementId;
        this.code = code;
        this.libelle = libelle;
        this.libelleEn = libelleEn;
        this.rang = rang;
        this.dureeTheoriqueAnnees = dureeTheoriqueAnnees;
        this.description = description;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.utilisateurId = utilisateurId;
    }

    public UUID getSousSystemeId() { return sousSystemeId; }
    public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getLibelleEn() { return libelleEn; }
    public Integer getRang() { return rang; }
    public Integer getDureeTheoriqueAnnees() { return dureeTheoriqueAnnees; }
    public String getDescription() { return description; }
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public UUID getUtilisateurId() { return utilisateurId; }
}