package com.example.daeko.referentiel.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CreerFiliereCommand {

    private final UUID ordreEnseignementId;
    private final UUID typeEnseignementId;
    private final String code;
    private final String libelle;
    private final String libelleEn;
    private final String description;
    private final LocalDate dateEntreeVigueur;
    private final UUID utilisateurId;

    public CreerFiliereCommand(UUID ordreEnseignementId, UUID typeEnseignementId, String code,
                               String libelle, String libelleEn, String description,
                               LocalDate dateEntreeVigueur, UUID utilisateurId) {
        this.ordreEnseignementId = ordreEnseignementId;
        this.typeEnseignementId = typeEnseignementId;
        this.code = code;
        this.libelle = libelle;
        this.libelleEn = libelleEn;
        this.description = description;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.utilisateurId = utilisateurId;
    }

    public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
    public UUID getTypeEnseignementId() { return typeEnseignementId; }
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getLibelleEn() { return libelleEn; }
    public String getDescription() { return description; }
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public UUID getUtilisateurId() { return utilisateurId; }
}
