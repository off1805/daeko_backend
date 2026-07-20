package com.example.daeko.referentiel.domain.event;

import java.time.Instant;
import java.util.UUID;

public class EntiteReactiveeEvent {

    private final String typeEntite;
    private final UUID entiteId;
    private final String code;
    private final Instant emisLe;

    public EntiteReactiveeEvent(String typeEntite, UUID entiteId, String code) {
        this.typeEntite = typeEntite;
        this.entiteId = entiteId;
        this.code = code;
        this.emisLe = Instant.now();
    }

    public String getTypeEntite() { return typeEntite; }
    public UUID getEntiteId() { return entiteId; }
    public String getCode() { return code; }
    public Instant getEmisLe() { return emisLe; }
}