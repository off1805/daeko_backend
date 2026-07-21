package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierCycleCommand;
import com.example.daeko.referentiel.application.port.in.CycleUseCase;
import com.example.daeko.referentiel.domain.exception.SuppressionInterditeException;
import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerCycleRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.DeprecierRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierCycleRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.CycleResponse;
import com.example.daeko.referentiel.infrastructure.mapper.CycleMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/referentiel/cycles")
public class CycleController {

    private final CycleUseCase cycleUseCase;
    private final CycleMapper cycleMapper;

    public CycleController(CycleUseCase cycleUseCase, CycleMapper cycleMapper) {
        this.cycleUseCase = cycleUseCase;
        this.cycleMapper = cycleMapper;
    }

    @GetMapping
    public List<CycleResponse> lister(@RequestParam(defaultValue = "ACTIVE") EtatReferentiel etat) {
        return cycleUseCase.rechercher(etat).stream()
                .map(cycleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CycleResponse consulter(@PathVariable UUID id) {
        return cycleMapper.toResponse(cycleUseCase.consulterParId(id));
    }

    @PostMapping
    public ResponseEntity<CycleResponse> creer(@Valid @RequestBody CreerCycleRequest request) {
        Cycle cree = cycleUseCase.creer(cycleMapper.toCommand(request, request.getUtilisateurId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(cycleMapper.toResponse(cree));
    }

    @PatchMapping("/{id}")
    public CycleResponse modifier(
            @PathVariable UUID id,
            @Valid @RequestBody ModifierCycleRequest request) {
        ModifierCycleCommand command = new ModifierCycleCommand(
                id, request.getLibelle(), request.getLibelleEn(),
                request.getDescription(), null);
        return cycleMapper.toResponse(cycleUseCase.modifier(command));
    }

    @PostMapping("/{id}/deprecier")
    public CycleResponse deprecier(
            @PathVariable UUID id,
            @Valid @RequestBody DeprecierRequest request) {
        DeprecierCommand command = new DeprecierCommand(
                id, request.getMotif(), request.getDateEffet(), null);
        return cycleMapper.toResponse(cycleUseCase.deprecier(command));
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable UUID id) {
        throw new SuppressionInterditeException();
    }
}