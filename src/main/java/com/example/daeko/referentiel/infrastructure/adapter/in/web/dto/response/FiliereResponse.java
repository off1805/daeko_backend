package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FiliereResponse {
    private UUID id;
    private String etat;
    private UUID ordreEnseignementId;
    private UUID typeEnseignementId;
    private String code;
    private String libelle;
    private String libelleEn;
    private String description;
    private LocalDate dateEntreeVigueur;
    private LocalDate dateDepreciation;
}
