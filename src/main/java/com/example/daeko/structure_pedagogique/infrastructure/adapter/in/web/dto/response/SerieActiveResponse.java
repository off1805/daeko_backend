package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;
import java.util.UUID;

public class SerieActiveResponse {

    private UUID id;
    private UUID niveauActiveId;
    private UUID serieId;
    private Instant dateCreation;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getNiveauActiveId() { return niveauActiveId; }
    public void setNiveauActiveId(UUID niveauActiveId) { this.niveauActiveId = niveauActiveId; }

    public UUID getSerieId() { return serieId; }
    public void setSerieId(UUID serieId) { this.serieId = serieId; }

    public Instant getDateCreation() { return dateCreation; }
    public void setDateCreation(Instant dateCreation) { this.dateCreation = dateCreation; }
}
