package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.NiveauUseCase;
import com.example.daeko.referentiel.domain.exception.SuppressionInterditeException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Niveau;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerNiveauRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.DeprecierRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierNiveauRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.NiveauResponse;
import com.example.daeko.referentiel.infrastructure.mapper.NiveauMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/referentiel/niveaux")
public class NiveauController {

    private final NiveauUseCase useCase;
    private final NiveauMapper mapper;

    public NiveauController(NiveauUseCase useCase, NiveauMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public List<NiveauResponse> lister(
            @RequestParam(required = false) UUID cycleId,
            @RequestParam(defaultValue = "ACTIVE") EtatReferentiel etat) {
        return useCase.rechercher(cycleId, etat).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NiveauResponse consulter(@PathVariable UUID id) {
        return mapper.toResponse(useCase.consulterParId(id));
    }

    @PostMapping
    public ResponseEntity<NiveauResponse> creer(@Valid @RequestBody CreerNiveauRequest request) {
        Niveau cree = useCase.creer(mapper.toCommand(request, null));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(cree));
    }

    @PatchMapping("/{id}")
    public NiveauResponse modifier(@PathVariable UUID id,
                                   @Valid @RequestBody ModifierNiveauRequest request) {
        return mapper.toResponse(useCase.modifier(mapper.toCommand(id, request, null)));
    }

    @PostMapping("/{id}/deprecier")
    public NiveauResponse deprecier(@PathVariable UUID id,
                                    @Valid @RequestBody DeprecierRequest request) {
        DeprecierCommand command = new DeprecierCommand(id, request.getMotif(),
                request.getDateEffet(), null);
        return mapper.toResponse(useCase.deprecier(command));
    }

    @PostMapping("/{id}/reactiver")
    public NiveauResponse reactiver(@PathVariable UUID id) {
        ReactiverCommand command = new ReactiverCommand(id, null);
        return mapper.toResponse(useCase.reactiver(command));
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable UUID id) {
        throw new SuppressionInterditeException();
    }
}