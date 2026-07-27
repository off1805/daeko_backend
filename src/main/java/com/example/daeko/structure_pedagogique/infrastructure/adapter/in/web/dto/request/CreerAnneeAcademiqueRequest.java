package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request;

import java.time.LocalDate;

public class CreerAnneeAcademiqueRequest {

    private String libelle;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
}
