package com.example.daeko.referentiel.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CreerNiveauCommand {

    private final UUID cycleId;
    private final String code;
    private final String libelle;
    private final String libelleCourt;
    private final String libelleEn;
    private final Integer rangDansCycle;
    private final Integer ageTheoriqueDebut;
    private final String description;
    private final LocalDate dateEntreeVigueur;
    private final UUID utilisateurId;

    public CreerNiveauCommand(UUID cycleId, String code, String libelle, String libelleCourt,
                              String libelleEn, Integer rangDansCycle, Integer ageTheoriqueDebut,
                              String description, LocalDate dateEntreeVigueur, UUID utilisateurId) {
        this.cycleId = cycleId;
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.rangDansCycle = rangDansCycle;
        this.ageTheoriqueDebut = ageTheoriqueDebut;
        this.description = description;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.utilisateurId = utilisateurId;
    }

    public UUID getCycleId() { return cycleId; }
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public String getLibelleEn() { return libelleEn; }
    public Integer getRangDansCycle() { return rangDansCycle; }
    public Integer getAgeTheoriqueDebut() { return ageTheoriqueDebut; }
    public String getDescription() { return description; }
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public UUID getUtilisateurId() { return utilisateurId; }
}