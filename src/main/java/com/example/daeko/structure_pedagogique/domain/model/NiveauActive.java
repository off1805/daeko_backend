package com.example.daeko.structure_pedagogique.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class NiveauActive {

    private UUID id;
    private final UUID configurationId;
    private final UUID niveauId; // reference referentiel.niveau
    private Instant dateCreation;
    private UUID creePar;

    private NiveauActive(UUID configurationId, UUID niveauId, UUID creePar) {
        this.configurationId = Objects.requireNonNull(configurationId, "configurationId requis");
        this.niveauId = Objects.requireNonNull(niveauId, "niveauId requis");
        this.creePar = Objects.requireNonNull(creePar, "creePar requis");
    }

    public static NiveauActive activer(UUID configurationId, UUID niveauId, UUID creePar) {
        return new NiveauActive(configurationId, niveauId, creePar);
    }

    public static NiveauActive reconstituer(UUID id, UUID configurationId, UUID niveauId,
                                             Instant dateCreation, UUID creePar) {
        NiveauActive n = new NiveauActive(configurationId, niveauId, creePar);
        n.id = id;
        n.dateCreation = dateCreation;
        return n;
    }

    public UUID getId() { return id; }
    public UUID getConfigurationId() { return configurationId; }
    public UUID getNiveauId() { return niveauId; }
    public Instant getDateCreation() { return dateCreation; }
    public UUID getCreePar() { return creePar; }
}
