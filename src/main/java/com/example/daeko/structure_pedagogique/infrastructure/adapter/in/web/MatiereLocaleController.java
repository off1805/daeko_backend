package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web;

import com.example.daeko.structure_pedagogique.application.dto.CreerMatiereLocaleCommand;
import com.example.daeko.structure_pedagogique.application.dto.ModifierMatiereLocaleCommand;
import com.example.daeko.structure_pedagogique.application.port.in.MatiereLocaleUseCase;
import com.example.daeko.structure_pedagogique.domain.model.MatiereLocale;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.CreerMatiereLocaleRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.DeprecierMatiereLocaleRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.ModifierMatiereLocaleRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.MatiereLocaleResponse;
import com.example.daeko.structure_pedagogique.infrastructure.config.EtablissementContextHolder;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.MatiereLocaleMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/structure")
public class MatiereLocaleController {

    private final MatiereLocaleUseCase matiereLocaleUseCase;
    private final MatiereLocaleMapper mapper;

    public MatiereLocaleController(MatiereLocaleUseCase matiereLocaleUseCase, MatiereLocaleMapper mapper) {
        this.matiereLocaleUseCase = matiereLocaleUseCase;
        this.mapper = mapper;
    }

    @PostMapping("/branches/{brancheId}/matieres-locales")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    @ResponseStatus(HttpStatus.CREATED)
    public MatiereLocaleResponse creer(@PathVariable UUID brancheId, @RequestBody CreerMatiereLocaleRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        MatiereLocale matiere = matiereLocaleUseCase.creer(new CreerMatiereLocaleCommand(
            brancheId, etablissementId, requete.getCode(), requete.getLibelle(), requete.getLibelleCourt(),
            requete.getLibelleEn(), requete.getDomaine(), requete.getTypeMatiere(), requete.getBaremeParDefaut(),
            acteurCourant()));
        return mapper.toResponse(matiere);
    }

    @GetMapping("/branches/{brancheId}/matieres-locales")
    public List<MatiereLocaleResponse> lister(@PathVariable UUID brancheId) {
        return matiereLocaleUseCase.rechercher(brancheId).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/matieres-locales/{id}")
    public MatiereLocaleResponse detail(@PathVariable UUID id) {
        return mapper.toResponse(matiereLocaleUseCase.consulterParId(id));
    }

    @PatchMapping("/matieres-locales/{id}")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public MatiereLocaleResponse modifier(@PathVariable UUID id, @RequestBody ModifierMatiereLocaleRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        MatiereLocale matiere = matiereLocaleUseCase.modifier(new ModifierMatiereLocaleCommand(
            id, etablissementId, requete.getCode(), requete.getLibelle(), requete.getLibelleCourt(),
            requete.getLibelleEn(), requete.getDomaine(), requete.getTypeMatiere(), requete.getBaremeParDefaut(),
            acteurCourant()));
        return mapper.toResponse(matiere);
    }

    @PostMapping("/matieres-locales/{id}/deprecier")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public MatiereLocaleResponse deprecier(@PathVariable UUID id, @RequestBody DeprecierMatiereLocaleRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        MatiereLocale matiere = matiereLocaleUseCase.deprecier(id, etablissementId, requete.getMotif(), acteurCourant());
        return mapper.toResponse(matiere);
    }

    private UUID acteurCourant() {
        // TODO(jour ulterieur, integration JWT) : remplacer par l'identite reelle issue du token.
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
