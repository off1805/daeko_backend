package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class AnneeAcademiqueResponse {

    private UUID id;
    private UUID etablissementId;
    private String libelle;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String etat;
    private Instant dateDemarrage;
    private Instant dateCloture;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getEtablissementId() { return etablissementId; }
    public void setEtablissementId(UUID etablissementId) { this.etablissementId = etablissementId; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public Instant getDateDemarrage() { return dateDemarrage; }
    public void setDateDemarrage(Instant dateDemarrage) { this.dateDemarrage = dateDemarrage; }

    public Instant getDateCloture() { return dateCloture; }
    public void setDateCloture(Instant dateCloture) { this.dateCloture = dateCloture; }
}
