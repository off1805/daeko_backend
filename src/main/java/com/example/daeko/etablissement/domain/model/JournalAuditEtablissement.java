package com.example.daeko.etablissement.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class JournalAuditEtablissement extends EntiteEtablissement {

    public static final String SCHEMA = "etablissement";

    private UUID etablissementId;
    private String action;
    private String details;
    private UUID effectuePar;
    private OffsetDateTime dateAction;

    public JournalAuditEtablissement() {
        super();
        this.dateAction = OffsetDateTime.now();
    }

    public JournalAuditEtablissement(UUID etablissementId, String action, String details, UUID effectuePar) {
        super();
        this.etablissementId = etablissementId;
        this.action = action;
        this.details = details;
        this.effectuePar = effectuePar;
        this.dateAction = OffsetDateTime.now();
    }

    public UUID getEtablissementId() { return etablissementId; }
    public void setEtablissementId(UUID etablissementId) { this.etablissementId = etablissementId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public UUID getEffectuePar() { return effectuePar; }
    public void setEffectuePar(UUID effectuePar) { this.effectuePar = effectuePar; }

    public OffsetDateTime getDateAction() { return dateAction; }
    public void setDateAction(OffsetDateTime dateAction) { this.dateAction = dateAction; }
}