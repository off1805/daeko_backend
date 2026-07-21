package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreerCycleRequest {

    @NotNull
    private UUID sousSystemeId;

    @NotNull
    private UUID ordreEnseignementId;

    @NotBlank
    private String code;

    @NotBlank
    private String libelle;

    private String libelleEn;

    private Integer rang;

    private Integer dureeTheoriqueAnnees;

    private String description;

    private LocalDate dateEntreeVigueur;

    private UUID utilisateurId;
}