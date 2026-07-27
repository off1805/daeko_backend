package com.example.daeko.structure_pedagogique.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class FiliereActive {

    private UUID id;
    private final UUID configurationId;
    private final UUID filiereId; // reference referentiel.filiere
    private Instant dateCreation;
    private UUID creePar;

    private FiliereActive(UUID configurationId, UUID filiereId, UUID creePar) {
        this.configurationId = Objects.requireNonNull(configurationId, "configurationId requis");
        this.filiereId = Objects.requireNonNull(filiereId, "filiereId requis");
        this.creePar = Objects.requireNonNull(creePar, "creePar requis");
    }

    public static FiliereActive activer(UUID configurationId, UUID filiereId, UUID creePar) {
        return new FiliereActive(configurationId, filiereId, creePar);
    }

    public static FiliereActive reconstituer(UUID id, UUID configurationId, UUID filiereId,
                                              Instant dateCreation, UUID creePar) {
        FiliereActive f = new FiliereActive(configurationId, filiereId, creePar);
        f.id = id;
        f.dateCreation = dateCreation;
        return f;
    }

    public UUID getId() { return id; }
    public UUID getConfigurationId() { return configurationId; }
    public UUID getFiliereId() { return filiereId; }
    public Instant getDateCreation() { return dateCreation; }
    public UUID getCreePar() { return creePar; }
}
