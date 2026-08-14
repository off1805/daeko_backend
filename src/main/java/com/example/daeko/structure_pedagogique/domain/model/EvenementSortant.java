package com.example.daeko.structure_pedagogique.domain.model;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

public final class EvenementSortant {

    private final String nom;
    private final Map<String, Object> payload;
    private final Instant creeLe;

    private EvenementSortant(String nom, Map<String, Object> payload, Instant creeLe) {
        this.nom = Objects.requireNonNull(nom, "nom d'evenement requis");
        this.payload = Objects.requireNonNull(payload, "payload requis");
        this.creeLe = creeLe != null ? creeLe : Instant.now();
    }

    public static EvenementSortant de(String nom, Map<String, Object> payload) {
        return new EvenementSortant(nom, payload, null);
    }

    public String getNom() { return nom; }
    public Map<String, Object> getPayload() { return payload; }
    public Instant getCreeLe() { return creeLe; }
}
