package com.example.daeko.referentiel.domain.model;

import java.time.Instant;
import java.util.UUID;

public class AuditEntree {

    private final UUID id;
    private final String typeEntite;
    private final UUID entiteId;
    private final OperationAudit operation;
    private final UUID utilisateurId;
    private final Instant horodatage;
    private final String motif;
    private final String valeursAvant; // JSON brut
    private final String valeursApres; // JSON brut

    public AuditEntree(UUID id, String typeEntite, UUID entiteId, OperationAudit operation,
                       UUID utilisateurId, Instant horodatage, String motif,
                       String valeursAvant, String valeursApres) {
        this.id = id;
        this.typeEntite = typeEntite;
        this.entiteId = entiteId;
        this.operation = operation;
        this.utilisateurId = utilisateurId;
        this.horodatage = horodatage;
        this.motif = motif;
        this.valeursAvant = valeursAvant;
        this.valeursApres = valeursApres;
    }

    public UUID getId() { return id; }
    public String getTypeEntite() { return typeEntite; }
    public UUID getEntiteId() { return entiteId; }
    public OperationAudit getOperation() { return operation; }
    public UUID getUtilisateurId() { return utilisateurId; }
    public Instant getHorodatage() { return horodatage; }
    public String getMotif() { return motif; }
    public String getValeursAvant() { return valeursAvant; }
    public String getValeursApres() { return valeursApres; }
}