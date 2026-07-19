package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreerMatiereNiveauRequest {

    @NotNull
    private UUID matiereReferentielId;

    @NotNull
    private UUID niveauId;

    private UUID serieId;

    private Boolean estObligatoire;

    private BigDecimal coefficientSuggere;

    private String sourceCoefficient;

    private Integer baremeSpecifique;

    private String description;
}
