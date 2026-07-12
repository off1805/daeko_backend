package com.example.daeko.referentiel.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Filiere extends EntiteReferentiel {

    private UUID ordreEnseignementId;
    private UUID typeEnseignementId;
    private String code;
    private String libelle;
    private String libelleEn;
    private String description;

    public Filiere() {
        super();
    }

    public Filiere(UUID ordreEnseignementId, UUID typeEnseignementId, String code,
                   String libelle, String libelleEn, String description,
                   LocalDate dateEntreeVigueur) {
        super();
        this.ordreEnseignementId = ordreEnseignementId;
        this.typeEnseignementId = typeEnseignementId;
        this.code = code;
        this.libelle = libelle;
        this.libelleEn = libelleEn;
        this.description = description;
        if (dateEntreeVigueur != null) setDateEntreeVigueur(dateEntreeVigueur);
    }

    public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
    public void setOrdreEnseignementId(UUID ordreEnseignementId) { this.ordreEnseignementId = ordreEnseignementId; }

    public UUID getTypeEnseignementId() { return typeEnseignementId; }
    public void setTypeEnseignementId(UUID typeEnseignementId) { this.typeEnseignementId = typeEnseignementId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getLibelleEn() { return libelleEn; }
    public void setLibelleEn(String libelleEn) { this.libelleEn = libelleEn; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
