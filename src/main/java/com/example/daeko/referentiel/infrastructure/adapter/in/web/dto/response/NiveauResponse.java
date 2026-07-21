package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class NiveauResponse {
    private UUID id;
    private String etat;
    private UUID cycleId;
    private String code;
    private String libelle;
    private String libelleCourt;
    private String libelleEn;
    private Integer rangDansCycle;
    private Integer ageTheoriqueDebut;
    private String description;
    private LocalDate dateEntreeVigueur;
    private LocalDate dateDepreciation;
}