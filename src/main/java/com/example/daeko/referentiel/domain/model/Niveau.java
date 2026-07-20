package com.example.daeko.referentiel.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Niveau extends EntiteReferentiel {

    private UUID cycleId;
    private String code;
    private String libelle;
    private String libelleCourt;
    private String libelleEn;
    private Integer rangDansCycle;
    private Integer ageTheoriqueDebut;
    private String description;

    public Niveau() {
        super();
    }

    public Niveau(UUID cycleId, String code, String libelle, String libelleCourt,
                  String libelleEn, Integer rangDansCycle, Integer ageTheoriqueDebut,
                  String description, LocalDate dateEntreeVigueur, UUID creePar) {
        super();
        this.cycleId = cycleId;
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.rangDansCycle = rangDansCycle;
        this.ageTheoriqueDebut = ageTheoriqueDebut;
        this.description = description;
        if (dateEntreeVigueur != null) setDateEntreeVigueur(dateEntreeVigueur);
        if (creePar != null) {
            setCreePar(creePar);
            setModifiePar(creePar);
        }
    }

    public UUID getCycleId() { return cycleId; }
    public void setCycleId(UUID cycleId) { this.cycleId = cycleId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getLibelleCourt() { return libelleCourt; }
    public void setLibelleCourt(String libelleCourt) { this.libelleCourt = libelleCourt; }

    public String getLibelleEn() { return libelleEn; }
    public void setLibelleEn(String libelleEn) { this.libelleEn = libelleEn; }

    public Integer getRangDansCycle() { return rangDansCycle; }
    public void setRangDansCycle(Integer rangDansCycle) { this.rangDansCycle = rangDansCycle; }

    public Integer getAgeTheoriqueDebut() { return ageTheoriqueDebut; }
    public void setAgeTheoriqueDebut(Integer ageTheoriqueDebut) { this.ageTheoriqueDebut = ageTheoriqueDebut; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}