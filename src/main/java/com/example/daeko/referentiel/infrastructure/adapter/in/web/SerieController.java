package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierSerieCommand;
import com.example.daeko.referentiel.application.port.in.SerieUseCase;
import com.example.daeko.referentiel.domain.exception.SuppressionInterditeException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Serie;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerSerieRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.DeprecierRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierSerieRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.SerieResponse;
import com.example.daeko.referentiel.infrastructure.mapper.SerieMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/referentiel/series")
public class SerieController {

    private final SerieUseCase useCase;
    private final SerieMapper mapper;

    public SerieController(SerieUseCase useCase, SerieMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public List<SerieResponse> lister(
            @RequestParam(defaultValue = "ACTIVE") EtatReferentiel etat) {
        return useCase.rechercher(etat).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SerieResponse consulter(@PathVariable UUID id) {
        return mapper.toResponse(useCase.consulterParId(id));
    }

    @PostMapping
    public ResponseEntity<SerieResponse> creer(
            @Valid @RequestBody CreerSerieRequest request) {
        Serie cree = useCase.creer(mapper.toCommand(request, null));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(cree));
    }

    @PatchMapping("/{id}")
    public SerieResponse modifier(
            @PathVariable UUID id,
            @Valid @RequestBody ModifierSerieRequest request) {
        ModifierSerieCommand command = new ModifierSerieCommand(
                id, request.getLibelle(), request.getLibelleEn(),
                request.getDescription(), null);
        return mapper.toResponse(useCase.modifier(command));
    }

    @PostMapping("/{id}/deprecier")
    public SerieResponse deprecier(
            @PathVariable UUID id,
            @Valid @RequestBody DeprecierRequest request) {
        DeprecierCommand command = new DeprecierCommand(id, request.getMotif(),
                request.getDateEffet(), null);
        return mapper.toResponse(useCase.deprecier(command));
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable UUID id) {
        throw new SuppressionInterditeException();
    }
}