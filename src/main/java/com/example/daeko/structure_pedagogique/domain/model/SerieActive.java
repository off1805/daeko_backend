package com.example.daeko.structure_pedagogique.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class SerieActive {

    private UUID id;
    private final UUID niveauActiveId;
    private final UUID serieId; // reference referentiel.serie
    private Instant dateCreation;
    private UUID creePar;

    private SerieActive(UUID niveauActiveId, UUID serieId, UUID creePar) {
        this.niveauActiveId = Objects.requireNonNull(niveauActiveId, "niveauActiveId requis");
        this.serieId = Objects.requireNonNull(serieId, "serieId requis");
        this.creePar = Objects.requireNonNull(creePar, "creePar requis");
    }

    public static SerieActive activer(UUID niveauActiveId, UUID serieId, UUID creePar) {
        return new SerieActive(niveauActiveId, serieId, creePar);
    }

    public static SerieActive reconstituer(UUID id, UUID niveauActiveId, UUID serieId,
                                            Instant dateCreation, UUID creePar) {
        SerieActive s = new SerieActive(niveauActiveId, serieId, creePar);
        s.id = id;
        s.dateCreation = dateCreation;
        return s;
    }

    public UUID getId() { return id; }
    public UUID getNiveauActiveId() { return niveauActiveId; }
    public UUID getSerieId() { return serieId; }
    public Instant getDateCreation() { return dateCreation; }
    public UUID getCreePar() { return creePar; }
}
