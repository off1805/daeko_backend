package com.example.daeko.structure_pedagogique.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public final class CreerAnneeAcademiqueCommand {
    private final UUID etablissementId;
    private final String libelle;
    private final LocalDate dateDebut;
    private final LocalDate dateFin;
    private final UUID acteur;

    public CreerAnneeAcademiqueCommand(UUID etablissementId, String libelle, LocalDate dateDebut, LocalDate dateFin, UUID acteur) {
        this.etablissementId = etablissementId;
        this.libelle = libelle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.acteur = acteur;
    }

    public UUID getEtablissementId() { return etablissementId; }
    public String getLibelle() { return libelle; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public UUID getActeur() { return acteur; }
}
