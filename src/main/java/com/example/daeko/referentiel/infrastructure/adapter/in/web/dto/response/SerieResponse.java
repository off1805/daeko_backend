package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SerieResponse {

    private UUID id;
    private UUID filiereId;
    private UUID niveauApparitionId;
    private String etat;
    private String code;
    private String libelle;
    private String libelleCourt;
    private String libelleEn;
    private String description;
    private LocalDate dateEntreeVigueur;
    private LocalDate dateDepreciation;
}