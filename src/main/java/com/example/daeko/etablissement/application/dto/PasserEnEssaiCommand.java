package com.example.daeko.etablissement.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PasserEnEssaiCommand {

    private final UUID etablissementId;
    private final LocalDate dateDebut;
    private final LocalDate dateFin;
    private final String motif;
    private final UUID utilisateurId;

    public PasserEnEssaiCommand(UUID etablissementId, LocalDate dateDebut, LocalDate dateFin, String motif, UUID utilisateurId) {
        this.etablissementId = etablissementId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.motif = motif;
        this.utilisateurId = utilisateurId;
    }

    public UUID getEtablissementId() { return etablissementId; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public String getMotif() { return motif; }
    public UUID getUtilisateurId() { return utilisateurId; }
}