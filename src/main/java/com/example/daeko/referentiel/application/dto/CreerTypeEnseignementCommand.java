package com.example.daeko.referentiel.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CreerTypeEnseignementCommand {

    private final String code;
    private final String libelle;
    private final Integer rang;
    private final LocalDate dateEntreeVigueur;
    private final UUID utilisateurId;

    public CreerTypeEnseignementCommand(String code, String libelle, Integer rang,
                                        LocalDate dateEntreeVigueur, UUID utilisateurId) {
        this.code = code;
        this.libelle = libelle;
        this.rang = rang;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.utilisateurId = utilisateurId;
    }

    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public Integer getRang() { return rang; }
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public UUID getUtilisateurId() { return utilisateurId; }
}