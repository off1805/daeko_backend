package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web;

import com.example.daeko.structure_pedagogique.application.dto.CreerBrancheCommand;
import com.example.daeko.structure_pedagogique.application.dto.ModifierLibelleBrancheCommand;
import com.example.daeko.structure_pedagogique.application.port.in.BrancheUseCase;
import com.example.daeko.structure_pedagogique.domain.model.Branche;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.ArchiverBrancheRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.CreerBrancheRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.ModifierLibelleBrancheRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.BrancheResponse;
import com.example.daeko.structure_pedagogique.infrastructure.config.EtablissementContextHolder;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.BrancheMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/branches")
public class BrancheController {

    private final BrancheUseCase brancheUseCase;
    private final BrancheMapper mapper;

    public BrancheController(BrancheUseCase brancheUseCase, BrancheMapper mapper) {
        this.brancheUseCase = brancheUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('structure.configurer')")
    @ResponseStatus(HttpStatus.CREATED)
    public BrancheResponse creer(@RequestBody CreerBrancheRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        Branche branche = brancheUseCase.creer(new CreerBrancheCommand(
            etablissementId, requete.getSousSystemeId(), requete.getOrdreEnseignementId(),
            requete.getTypeEnseignementId(), requete.getLibelle(), acteurCourant()));
        return mapper.toResponse(branche);
    }

    @GetMapping
    public List<BrancheResponse> lister() {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return brancheUseCase.rechercher(etablissementId).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public BrancheResponse detail(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return mapper.toResponse(brancheUseCase.consulterParId(id, etablissementId));
    }

    @PatchMapping("/{id}")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public BrancheResponse modifierLibelle(@PathVariable UUID id, @RequestBody ModifierLibelleBrancheRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        Branche branche = brancheUseCase.modifierLibelle(new ModifierLibelleBrancheCommand(
            id, etablissementId, requete.getLibelle(), acteurCourant()));
        return mapper.toResponse(branche);
    }

    @PostMapping("/{id}/activer")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public BrancheResponse activer(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return mapper.toResponse(brancheUseCase.activer(id, etablissementId, acteurCourant()));
    }

    @PostMapping("/{id}/suspendre")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public BrancheResponse suspendre(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return mapper.toResponse(brancheUseCase.suspendre(id, etablissementId, acteurCourant()));
    }

    @PostMapping("/{id}/reactiver")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public BrancheResponse reactiver(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return mapper.toResponse(brancheUseCase.reactiver(id, etablissementId, acteurCourant()));
    }

    @PostMapping("/{id}/archiver")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public BrancheResponse archiver(@PathVariable UUID id, @RequestBody ArchiverBrancheRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return mapper.toResponse(
            brancheUseCase.archiver(id, etablissementId, requete.getMotif(), acteurCourant()));
    }

    private UUID acteurCourant() {
        // TODO(jour ulterieur, integration JWT) : remplacer par l'identite reelle issue du token.
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
