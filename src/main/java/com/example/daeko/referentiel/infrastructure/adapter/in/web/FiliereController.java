package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.FiliereUseCase;
import com.example.daeko.referentiel.domain.exception.SuppressionInterditeException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Filiere;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerFiliereRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.DeprecierRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierFiliereRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.FiliereResponse;
import com.example.daeko.referentiel.infrastructure.mapper.FiliereMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/referentiel/filieres")
public class FiliereController {

    private final FiliereUseCase useCase;
    private final FiliereMapper mapper;

    public FiliereController(FiliereUseCase useCase, FiliereMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public List<FiliereResponse> lister(
            @RequestParam(defaultValue = "ACTIVE") EtatReferentiel etat) {
        return useCase.rechercher(etat).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FiliereResponse consulter(@PathVariable UUID id) {
        return mapper.toResponse(useCase.consulterParId(id));
    }

    @PostMapping
    public ResponseEntity<FiliereResponse> creer(@Valid @RequestBody CreerFiliereRequest request) {
        Filiere creee = useCase.creer(mapper.toCommand(request, null));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creee));
    }

    @PatchMapping("/{id}")
    public FiliereResponse modifier(@PathVariable UUID id,
                                    @Valid @RequestBody ModifierFiliereRequest request) {
        return mapper.toResponse(useCase.modifier(mapper.toCommand(id, request, null)));
    }

    @PostMapping("/{id}/deprecier")
    public FiliereResponse deprecier(@PathVariable UUID id,
                                     @Valid @RequestBody DeprecierRequest request) {
        DeprecierCommand command = new DeprecierCommand(id, request.getMotif(),
                request.getDateEffet(), null);
        return mapper.toResponse(useCase.deprecier(command));
    }

    @PostMapping("/{id}/reactiver")
    public FiliereResponse reactiver(@PathVariable UUID id) {
        ReactiverCommand command = new ReactiverCommand(id, null);
        return mapper.toResponse(useCase.reactiver(command));
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable UUID id) {
        throw new SuppressionInterditeException();
    }
}
