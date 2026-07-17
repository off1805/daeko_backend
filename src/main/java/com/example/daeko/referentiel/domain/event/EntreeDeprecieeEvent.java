package com.example.daeko.referentiel.domain.event;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class EntreeDeprecieeEvent {

    private final String typeEntite;
    private final UUID entiteId;
    private final String code;
    private final LocalDate dateEffet;
    private final String motif;
    private final Instant emisLe;

    public EntreeDeprecieeEvent(String typeEntite, UUID entiteId, String code,
                                LocalDate dateEffet, String motif) {
        this.typeEntite = typeEntite;
        this.entiteId = entiteId;
        this.code = code;
        this.dateEffet = dateEffet;
        this.motif = motif;
        this.emisLe = Instant.now();
    }

    public String getTypeEntite() { return typeEntite; }
    public UUID getEntiteId() { return entiteId; }
    public String getCode() { return code; }
    public LocalDate getDateEffet() { return dateEffet; }
    public String getMotif() { return motif; }
    public Instant getEmisLe() { return emisLe; }
}
