package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;
import java.util.UUID;

public class FiliereActiveResponse {

    private UUID id;
    private UUID configurationId;
    private UUID filiereId;
    private Instant dateCreation;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getConfigurationId() { return configurationId; }
    public void setConfigurationId(UUID configurationId) { this.configurationId = configurationId; }

    public UUID getFiliereId() { return filiereId; }
    public void setFiliereId(UUID filiereId) { this.filiereId = filiereId; }

    public Instant getDateCreation() { return dateCreation; }
    public void setDateCreation(Instant dateCreation) { this.dateCreation = dateCreation; }
}
