package com.example.daeko.referentiel.application.dto;

import com.example.daeko.referentiel.domain.model.DomaineMatiere;
import com.example.daeko.referentiel.domain.model.TypeMatiereReferentiel;

import java.time.LocalDate;
import java.util.UUID;

public class CreerMatiereCommand {

    private final UUID sousSystemeId;
    private final String code;
    private final String libelle;
    private final String libelleCourt;
    private final String libelleEn;
    private final DomaineMatiere domaine;
    private final TypeMatiereReferentiel typeMatiere;
    private final Integer baremeParDefaut;
    private final String description;
    private final LocalDate dateEntreeVigueur;
    private final UUID utilisateurId;

    public CreerMatiereCommand(UUID sousSystemeId, String code, String libelle, String libelleCourt,
                               String libelleEn, DomaineMatiere domaine, TypeMatiereReferentiel typeMatiere,
                               Integer baremeParDefaut, String description, LocalDate dateEntreeVigueur,
                               UUID utilisateurId) {
        this.sousSystemeId = sousSystemeId;
        this.code = code;
        this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.domaine = domaine;
        this.typeMatiere = typeMatiere;
        this.baremeParDefaut = baremeParDefaut;
        this.description = description;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.utilisateurId = utilisateurId;
    }

    public UUID getSousSystemeId() { return sousSystemeId; }
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public String getLibelleEn() { return libelleEn; }
    public DomaineMatiere getDomaine() { return domaine; }
    public TypeMatiereReferentiel getTypeMatiere() { return typeMatiere; }
    public Integer getBaremeParDefaut() { return baremeParDefaut; }
    public String getDescription() { return description; }
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public UUID getUtilisateurId() { return utilisateurId; }
}