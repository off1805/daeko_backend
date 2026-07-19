package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MatiereReferentielNiveauResponse {

    private UUID id;
    private String etat;
    private UUID matiereReferentielId;
    private UUID niveauId;
    private UUID serieId;
    private boolean estObligatoire;
    private BigDecimal coefficientSuggere;
    private String sourceCoefficient;
    private Integer baremeSpecifique;
    private String description;
    private LocalDate dateEntreeVigueur;
    private LocalDate dateDepreciation;
}
