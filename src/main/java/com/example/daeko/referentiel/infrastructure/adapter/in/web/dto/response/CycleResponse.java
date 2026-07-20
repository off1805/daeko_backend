package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record CycleResponse(
        UUID sousSystemeId,
        UUID ordreEnseignementId,
        String code,
        String libelle,
        String libelleEn,
        Integer rang,
        Integer dureeTheoriqueAnnees,
        String description,
        LocalDate dateEntreeVigueur
) {}