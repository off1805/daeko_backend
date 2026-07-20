package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.port.in.AuditUseCase;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.AuditResponse;
import com.example.daeko.referentiel.infrastructure.mapper.AuditMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/referentiel/audit")
public class AuditController {

    private final AuditUseCase useCase;
    private final AuditMapper mapper;

    public AuditController(AuditUseCase useCase, AuditMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public List<AuditResponse> consulter(
            @RequestParam(required = false) String typeEntite,
            @RequestParam(required = false) UUID entiteId,
            @RequestParam(required = false) UUID utilisateurId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant du,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant au) {
        return useCase.consulter(typeEntite, entiteId, utilisateurId, du, au)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}