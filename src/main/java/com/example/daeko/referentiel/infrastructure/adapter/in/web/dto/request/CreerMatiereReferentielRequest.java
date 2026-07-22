package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import com.example.daeko.referentiel.domain.model.DomaineMatiere;
import com.example.daeko.referentiel.domain.model.TypeMatiereReferentiel;
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
public class CreerMatiereReferentielRequest {

    @NotNull
    private UUID sousSystemeId;

    @NotBlank
    private String code;

    @NotBlank
    private String libelle;

    private String libelleCourt;

    private String libelleEn;

    @NotNull
    private DomaineMatiere domaine;

    @NotNull
    private TypeMatiereReferentiel typeMatiere;

    private Integer baremeParDefaut;

    private String description;

    private LocalDate dateEntreeVigueur;
}