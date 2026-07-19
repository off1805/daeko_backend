package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierSousSystemeCommand;
import com.example.daeko.referentiel.application.port.in.SousSystemeUseCase;
import com.example.daeko.referentiel.domain.exception.SuppressionInterditeException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.SousSysteme;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerSousSystemeRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.DeprecierRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierSousSystemeRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.SousSystemeResponse;
import com.example.daeko.referentiel.infrastructure.mapper.SousSystemeMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/referentiel/sous-systemes")
public class SousSystemeController {

    private final SousSystemeUseCase useCase;
    private final SousSystemeMapper mapper;

    public SousSystemeController(SousSystemeUseCase useCase, SousSystemeMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    //private static final UUID UTILISATEUR_SYSTEME = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @GetMapping
    public List<SousSystemeResponse> lister(
            @RequestParam(defaultValue = "ACTIVE") EtatReferentiel etat) {
        return useCase.rechercher(etat).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SousSystemeResponse consulter(@PathVariable UUID id) {
        return mapper.toResponse(useCase.consulterParId(id));
    }

    @PostMapping
    public ResponseEntity<SousSystemeResponse> creer(
            @Valid @RequestBody CreerSousSystemeRequest request) {
        SousSysteme cree = useCase.creer(mapper.toCommand(request, null));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(cree));
    }

    @PatchMapping("/{id}")
    public SousSystemeResponse modifier(
            @PathVariable UUID id,
            @Valid @RequestBody ModifierSousSystemeRequest request) {
        ModifierSousSystemeCommand command = new ModifierSousSystemeCommand(
                id, request.getLibelle(), request.getLibelleCourt(),
                request.getDescription(), null);
        return mapper.toResponse(useCase.modifier(command));
    }

    @PostMapping("/{id}/deprecier")
    public SousSystemeResponse deprecier(
            @PathVariable UUID id,
            @Valid @RequestBody DeprecierRequest request) {
        DeprecierCommand command = new DeprecierCommand(id, request.getMotif(),
                request.getDateEffet(), null /*UTILISATEUR_SYSTEME*/);
        return mapper.toResponse(useCase.deprecier(command));
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable UUID id) {
        throw new SuppressionInterditeException();
    }
}
