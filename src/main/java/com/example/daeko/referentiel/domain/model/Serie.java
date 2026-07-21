package com.example.daeko.referentiel.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Serie extends EntiteReferentiel {

    private UUID filiereId;
    private UUID niveauApparitionId;
    private String code;
    private String libelle;
    private String libelleCourt;
    private String libelleEn;
    private String description;

    public Serie(UUID filiereId, String code, String libelle, String libelleEn, String description, LocalDate dateEntreeVigueur) {
        super();
    }

    public Serie(UUID filiereId, UUID niveauApparitionId, String code, String libelle,
                 String libelleCourt, String libelleEn, String description,
                 LocalDate dateEntreeVigueur) {
        super();
        this.filiereId = filiereId;
        this.niveauApparitionId = niveauApparitionId;
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.description = description;
        if (dateEntreeVigueur != null) setDateEntreeVigueur(dateEntreeVigueur);
    }

    public UUID getFiliereId() { return filiereId; }
    public void setFiliereId(UUID filiereId) { this.filiereId = filiereId; }

    public UUID getNiveauApparitionId() { return niveauApparitionId; }
    public void setNiveauApparitionId(UUID niveauApparitionId) { this.niveauApparitionId = niveauApparitionId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getLibelleCourt() { return libelleCourt; }
    public void setLibelleCourt(String libelleCourt) { this.libelleCourt = libelleCourt; }

    public String getLibelleEn() { return libelleEn; }
    public void setLibelleEn(String libelleEn) { this.libelleEn = libelleEn; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
