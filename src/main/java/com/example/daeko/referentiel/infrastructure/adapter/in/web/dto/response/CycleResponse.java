package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

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
public class CycleResponse {

    private UUID id;
    private UUID sousSystemeId;
    private UUID ordreEnseignementId;
    private String etat;
    private String code;
    private String libelle;
    private String libelleEn;
    private Integer rang;
    private Integer dureeTheoriqueAnnees;
    private String description;
    private LocalDate dateEntreeVigueur;
    private LocalDate dateDepreciation;
}