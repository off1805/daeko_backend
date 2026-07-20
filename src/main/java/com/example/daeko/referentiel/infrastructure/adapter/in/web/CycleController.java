package com.example.daeko.referentiel.infrastructure.adapter.in.web;


import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.ports.in.CycleUseCase;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CycleRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.CycleResponse;
import com.example.daeko.referentiel.infrastructure.mapper.CycleMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cycles")
public class CycleController {

    private final CycleUseCase cycleUseCase;
    private final CycleMapper cycleMapper;

    public CycleController(CycleUseCase cycleUseCase, CycleMapper cycleMapper) {
        this.cycleUseCase = cycleUseCase;
        this.cycleMapper = cycleMapper;
    }

    @PostMapping
    public com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.CycleResponse toResponse(Cycle domaine) {
        if (domaine == null) {
            return null;
        }

        return new com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.CycleResponse(
                domaine.getSousSystemeId(),
                domaine.getOrdreEnseignementId(),
                domaine.getCode(),
                domaine.getLibelle(),
                domaine.getLibelleEn(),
                domaine.getRang(),
                domaine.getDureeTheoriqueAnnees(),
                domaine.getDescription(),
                domaine.getDateEntreeVigueur()
        );
    }
}