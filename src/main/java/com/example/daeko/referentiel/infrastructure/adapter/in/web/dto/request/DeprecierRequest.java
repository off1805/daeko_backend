package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class DeprecierRequest {

    @NotBlank
    private String motif;

    private LocalDate dateEffet;

    public DeprecierRequest() {}

    public DeprecierRequest(String motif, LocalDate dateEffet) {
        this.motif = motif;
        this.dateEffet = dateEffet;
    }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    public LocalDate getDateEffet() { return dateEffet; }
    public void setDateEffet(LocalDate dateEffet) { this.dateEffet = dateEffet; }
}
