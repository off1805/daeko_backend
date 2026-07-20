package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record OrdreEnseignementRequest(
        String code,
        String libelle,
        Integer rang,
        LocalDate dateEntreeVigueur,
        UUID utilisateurId
) {}