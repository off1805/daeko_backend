package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record TypeEnseignementRequest(
        String code,
        String libelle,
        LocalDate dateEntreeVigueur,
        UUID utilisateurId
) {}