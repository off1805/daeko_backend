package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import java.time.LocalDate;

public record TypeEnseignementResponse(
        String code,
        String libelle,
        String description,
        LocalDate dateEntreeVigueur
) {}