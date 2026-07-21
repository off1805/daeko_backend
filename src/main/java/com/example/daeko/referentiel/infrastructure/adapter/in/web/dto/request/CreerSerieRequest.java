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
public class CreerSerieRequest {

    @NotNull
    private UUID typeEnseignementId;

    @NotBlank
    private String code;

    @NotBlank
    private String libelle;

    private String libelleEn;

    private String description;

    @NotNull
    private LocalDate dateEntreeVigueur;

    @NotNull
    private UUID utilisateurId;
}