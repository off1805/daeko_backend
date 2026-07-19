package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreerSousSystemeRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String libelle;

    private String libelleCourt;

    private String description;

    @NotBlank
    @Size(min = 2, max = 2)
    private String languePrincipale;
}
