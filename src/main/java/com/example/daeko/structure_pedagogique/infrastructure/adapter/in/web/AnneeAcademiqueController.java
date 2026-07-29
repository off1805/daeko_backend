package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web;

import com.example.daeko.structure_pedagogique.application.dto.CreerAnneeAcademiqueCommand;
import com.example.daeko.structure_pedagogique.application.port.in.AnneeAcademiqueUseCase;
import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.CloturerAnneeRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.CreerAnneeAcademiqueRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.AnneeAcademiqueResponse;
import com.example.daeko.structure_pedagogique.infrastructure.config.EtablissementContextHolder;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.AnneeAcademiqueMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/structure/annees-academiques")
public class AnneeAcademiqueController {

    private final AnneeAcademiqueUseCase anneeAcademiqueUseCase;
    private final AnneeAcademiqueMapper mapper;

    public AnneeAcademiqueController(AnneeAcademiqueUseCase anneeAcademiqueUseCase, AnneeAcademiqueMapper mapper) {
        this.anneeAcademiqueUseCase = anneeAcademiqueUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('structure.configurer')")
    @ResponseStatus(HttpStatus.CREATED)
    public AnneeAcademiqueResponse creer(@RequestBody CreerAnneeAcademiqueRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        AnneeAcademique annee = anneeAcademiqueUseCase.creer(new CreerAnneeAcademiqueCommand(
            etablissementId, requete.getLibelle(), requete.getDateDebut(), requete.getDateFin(), acteurCourant()));
        return mapper.toResponse(annee);
    }

    @GetMapping
    public List<AnneeAcademiqueResponse> lister() {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return anneeAcademiqueUseCase.rechercher(etablissementId).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public AnneeAcademiqueResponse detail(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return mapper.toResponse(anneeAcademiqueUseCase.consulterParId(id, etablissementId));
    }

    @PostMapping("/{id}/demarrer")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public AnneeAcademiqueResponse demarrer(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return mapper.toResponse(anneeAcademiqueUseCase.demarrer(id, etablissementId, acteurCourant()));
    }

    @PostMapping("/{id}/cloturer")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public AnneeAcademiqueResponse cloturer(@PathVariable UUID id, @RequestBody CloturerAnneeRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return mapper.toResponse(
            anneeAcademiqueUseCase.cloturer(id, etablissementId, requete.getConfirmation(), acteurCourant()));
    }

    private UUID acteurCourant() {
        // TODO(jour ulterieur, integration JWT) : remplacer par l'identite reelle issue du token.
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
