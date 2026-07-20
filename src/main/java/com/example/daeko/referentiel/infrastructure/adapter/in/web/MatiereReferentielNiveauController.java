package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.MatiereNiveauUseCase;
import com.example.daeko.referentiel.domain.exception.SuppressionInterditeException;
import com.example.daeko.referentiel.domain.model.MatiereReferentielNiveau;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerMatiereNiveauRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.DeprecierRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.MatiereReferentielNiveauResponse;
import com.example.daeko.referentiel.infrastructure.mapper.MatiereReferentielNiveauMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/referentiel/matieres-niveaux")
public class MatiereReferentielNiveauController {

    private final MatiereNiveauUseCase useCase;
    private final MatiereReferentielNiveauMapper mapper;

    public MatiereReferentielNiveauController(MatiereNiveauUseCase useCase, MatiereReferentielNiveauMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MatiereReferentielNiveauResponse> lister(
            @RequestParam UUID niveauId,
            @RequestParam(required = false) UUID serieId) {
        return useCase.rechercher(niveauId, serieId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MatiereReferentielNiveauResponse consulter(@PathVariable UUID id) {
        return mapper.toResponse(useCase.consulterParId(id));
    }

    @PostMapping
    public ResponseEntity<MatiereReferentielNiveauResponse> creer(
            @Valid @RequestBody CreerMatiereNiveauRequest request) {
        MatiereReferentielNiveau cree = useCase.creer(mapper.toCommand(request, null));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(cree));
    }

    @PostMapping("/{id}/deprecier")
    public MatiereReferentielNiveauResponse deprecier(
            @PathVariable UUID id,
            @Valid @RequestBody DeprecierRequest request) {
        DeprecierCommand command = new DeprecierCommand(id, request.getMotif(),
                request.getDateEffet(), null);
        return mapper.toResponse(useCase.deprecier(command));
    }

    @PostMapping("/{id}/reactiver")
    public MatiereReferentielNiveauResponse reactiver(@PathVariable UUID id) {
        ReactiverCommand command = new ReactiverCommand(id, null);
        return mapper.toResponse(useCase.reactiver(command));
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable UUID id) {
        throw new SuppressionInterditeException();
    }
}
