package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierMatiereCommand;
import com.example.daeko.referentiel.application.port.in.MatiereReferentielUseCase;
import com.example.daeko.referentiel.domain.exception.SuppressionInterditeException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentiel;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerMatiereReferentielRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.DeprecierRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.ModifierMatiereReferentielRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.MatiereReferentielResponse;
import com.example.daeko.referentiel.infrastructure.mapper.MatiereReferentielMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/referentiel/matieres")
public class MatiereReferentielController {

    private final MatiereReferentielUseCase useCase;
    private final MatiereReferentielMapper mapper;

    public MatiereReferentielController(MatiereReferentielUseCase useCase, MatiereReferentielMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MatiereReferentielResponse> lister(
            @RequestParam(defaultValue = "ACTIVE") EtatReferentiel etat) {
        return useCase.rechercher(etat).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MatiereReferentielResponse consulter(@PathVariable UUID id) {
        return mapper.toResponse(useCase.consulterParId(id));
    }

    @PostMapping
    public ResponseEntity<MatiereReferentielResponse> creer(
            @Valid @RequestBody CreerMatiereReferentielRequest request) {
        MatiereReferentiel cree = useCase.creer(mapper.toCommand(request, null));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(cree));
    }

    @PatchMapping("/{id}")
    public MatiereReferentielResponse modifier(
            @PathVariable UUID id,
            @Valid @RequestBody ModifierMatiereReferentielRequest request) {
        ModifierMatiereCommand command = new ModifierMatiereCommand(
                id,
                request.getLibelle(),
                request.getLibelleCourt(),
                request.getLibelleEn(),
                request.getDescription(),
                null
        );
        return mapper.toResponse(useCase.modifier(command));
    }

    @PostMapping("/{id}/deprecier")
    public MatiereReferentielResponse deprecier(
            @PathVariable UUID id,
            @Valid @RequestBody DeprecierRequest request) {
        DeprecierCommand command = new DeprecierCommand(
                id,
                request.getMotif(),
                request.getDateEffet(),
                null
        );
        return mapper.toResponse(useCase.deprecier(command));
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable UUID id) {
        throw new SuppressionInterditeException();
    }
}