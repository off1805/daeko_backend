package com.example.daeko.referentiel.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CreerSerieCommand {

    private final UUID filiereId;
    private final UUID niveauApparitionId;
    private final String code;
    private final String libelle;
    private final String libelleCourt;
    private final String libelleEn;
    private final String description;
    private final LocalDate dateEntreeVigueur;
    private final UUID utilisateurId;

    public CreerSerieCommand(UUID filiereId, UUID niveauApparitionId, String code, String libelle,
                             String libelleCourt, String libelleEn, String description,
                             LocalDate dateEntreeVigueur, UUID utilisateurId) {
        this.filiereId = filiereId;
        this.niveauApparitionId = niveauApparitionId;
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.description = description;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.utilisateurId = utilisateurId;
    }

    public UUID getFiliereId() { return filiereId; }
    public UUID getNiveauApparitionId() { return niveauApparitionId; }
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public String getLibelleEn() { return libelleEn; }
    public String getDescription() { return description; }
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public UUID getUtilisateurId() { return utilisateurId; }
}