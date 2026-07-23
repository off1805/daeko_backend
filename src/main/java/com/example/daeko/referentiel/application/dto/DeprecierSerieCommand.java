package com.example.daeko.referentiel.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class DeprecierSerieCommand {

    private final UUID entiteId;
    private final String motif;
    private final LocalDate dateEffet;
    private final UUID utilisateurId;

    public DeprecierSerieCommand(UUID entiteId, String motif, LocalDate dateEffet, UUID utilisateurId) {
        this.entiteId = entiteId;
        this.motif = motif;
        this.dateEffet = dateEffet != null ? dateEffet : LocalDate.now();
        this.utilisateurId = utilisateurId;
    }

    public UUID getEntiteId() { return entiteId; }
    public String getMotif() { return motif; }
    public LocalDate getDateEffet() { return dateEffet; }
    public UUID getUtilisateurId() { return utilisateurId; }
}