package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreerNiveauRequest {

    @NotNull
    private UUID cycleId;

    @NotBlank
    private String code;

    @NotBlank
    private String libelle;

    private String libelleCourt;
    private String libelleEn;

    @NotNull
    private Integer rangDansCycle;

    private Integer ageTheoriqueDebut;
    private String description;
}