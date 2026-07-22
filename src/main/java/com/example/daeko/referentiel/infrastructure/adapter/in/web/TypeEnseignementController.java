package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.TypeEnseignementUseCase;
import com.example.daeko.referentiel.domain.exception.SuppressionInterditeException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerTypeEnseignementRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.DeprecierRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierTypeEnseignementRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.TypeEnseignementResponse;
import com.example.daeko.referentiel.infrastructure.mapper.TypeEnseignementMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/referentiel/types-enseignement")
public class TypeEnseignementController {

    private final TypeEnseignementUseCase useCase;
    private final TypeEnseignementMapper mapper;

    public TypeEnseignementController(TypeEnseignementUseCase useCase, TypeEnseignementMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public List<TypeEnseignementResponse> lister(
            @RequestParam(defaultValue = "ACTIVE") EtatReferentiel etat) {
        return useCase.rechercher(etat).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TypeEnseignementResponse consulter(@PathVariable UUID id) {
        return mapper.toResponse(useCase.consulterParId(id));
    }

    @PostMapping
    public ResponseEntity<TypeEnseignementResponse> creer(@Valid @RequestBody CreerTypeEnseignementRequest request) {
        TypeEnseignement cree = useCase.creer(mapper.toCommand(request, null));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(cree));
    }

    @PatchMapping("/{id}")
    public TypeEnseignementResponse modifier(@PathVariable UUID id,
                                             @Valid @RequestBody ModifierTypeEnseignementRequest request) {
        return mapper.toResponse(useCase.modifier(mapper.toCommand(id, request, null)));
    }

    @PostMapping("/{id}/deprecier")
    public TypeEnseignementResponse deprecier(@PathVariable UUID id,
                                              @Valid @RequestBody DeprecierRequest request) {
        DeprecierCommand command = new DeprecierCommand(id, request.getMotif(),
                request.getDateEffet(), null);
        return mapper.toResponse(useCase.deprecier(command));
    }

    @PostMapping("/{id}/reactiver")
    public TypeEnseignementResponse reactiver(@PathVariable UUID id) {
        ReactiverCommand command = new ReactiverCommand(id, null);
        return mapper.toResponse(useCase.reactiver(command));
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable UUID id) {
        throw new SuppressionInterditeException();
    }
}
