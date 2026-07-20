package com.example.daeko.referentiel.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Cycle extends EntiteReferentiel {

    private UUID sousSystemeId;
    private UUID ordreEnseignementId;
    private String code;
    private String libelle;
    private String libelleEn;
    private Integer rang;
    private Integer dureeTheoriqueAnnees;
    private String description;

    public Cycle() {
        super();
    }

    public Cycle(UUID sousSystemeId, UUID ordreEnseignementId, String code, String libelle,
                 String libelleEn, Integer rang, Integer dureeTheoriqueAnnees,
                 String description, LocalDate dateEntreeVigueur, UUID creePar) {
        super();
        this.sousSystemeId = sousSystemeId;
        this.ordreEnseignementId = ordreEnseignementId;
        this.code = code;
        this.libelle = libelle;
        this.libelleEn = libelleEn;
        this.rang = rang;
        this.dureeTheoriqueAnnees = dureeTheoriqueAnnees;
        this.description = description;
        if (dateEntreeVigueur != null) setDateEntreeVigueur(dateEntreeVigueur);
        if (creePar != null) {
            setCreePar(creePar);
            setModifiePar(creePar);
        }
    }

    public UUID getSousSystemeId() { return sousSystemeId; }
    public void setSousSystemeId(UUID sousSystemeId) { this.sousSystemeId = sousSystemeId; }

    public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
    public void setOrdreEnseignementId(UUID ordreEnseignementId) { this.ordreEnseignementId = ordreEnseignementId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getLibelleEn() { return libelleEn; }
    public void setLibelleEn(String libelleEn) { this.libelleEn = libelleEn; }

    public Integer getRang() { return rang; }
    public void setRang(Integer rang) { this.rang = rang; }

    public Integer getDureeTheoriqueAnnees() { return dureeTheoriqueAnnees; }
    public void setDureeTheoriqueAnnees(Integer dureeTheoriqueAnnees) { this.dureeTheoriqueAnnees = dureeTheoriqueAnnees; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}