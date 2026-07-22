package com.example.daeko.referentiel.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CreerTypeEnseignementCommand {

    private final String code;
    private final String libelle;
    private final String description;
    private final LocalDate dateEntreeVigueur;
    private final UUID utilisateurId;

    public CreerTypeEnseignementCommand(String code, String libelle, String description,
                                        LocalDate dateEntreeVigueur, UUID utilisateurId) {
        this.code = code;
        this.libelle = libelle;
        this.description = description;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.utilisateurId = utilisateurId;
    }

    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getDescription() { return description; }
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public UUID getUtilisateurId() { return utilisateurId; }
}
