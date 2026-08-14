package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web;

import com.example.daeko.structure_pedagogique.application.dto.ResultatActivation;
import com.example.daeko.structure_pedagogique.application.port.in.FiliereActiveUseCase;
import com.example.daeko.structure_pedagogique.application.port.in.NiveauActiveUseCase;
import com.example.daeko.structure_pedagogique.application.port.in.SerieActiveUseCase;
import com.example.daeko.structure_pedagogique.domain.model.FiliereActive;
import com.example.daeko.structure_pedagogique.domain.model.NiveauActive;
import com.example.daeko.structure_pedagogique.domain.model.SerieActive;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.DefinirFilieresActivesRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.DefinirNiveauxActifsRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.DefinirSeriesActivesRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.FiliereActiveResponse;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.NiveauActiveResponse;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.SerieActiveResponse;
import com.example.daeko.structure_pedagogique.infrastructure.config.EtablissementContextHolder;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.FiliereActiveMapper;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.NiveauActiveMapper;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.SerieActiveMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/structure")
public class ActivationController {

    private final FiliereActiveUseCase filiereActiveUseCase;
    private final NiveauActiveUseCase niveauActiveUseCase;
    private final SerieActiveUseCase serieActiveUseCase;
    private final FiliereActiveMapper filiereActiveMapper;
    private final NiveauActiveMapper niveauActiveMapper;
    private final SerieActiveMapper serieActiveMapper;

    public ActivationController(FiliereActiveUseCase filiereActiveUseCase, NiveauActiveUseCase niveauActiveUseCase,
                                 SerieActiveUseCase serieActiveUseCase, FiliereActiveMapper filiereActiveMapper,
                                 NiveauActiveMapper niveauActiveMapper, SerieActiveMapper serieActiveMapper) {
        this.filiereActiveUseCase = filiereActiveUseCase;
        this.niveauActiveUseCase = niveauActiveUseCase;
        this.serieActiveUseCase = serieActiveUseCase;
        this.filiereActiveMapper = filiereActiveMapper;
        this.niveauActiveMapper = niveauActiveMapper;
        this.serieActiveMapper = serieActiveMapper;
    }

    @GetMapping("/configurations/{id}/filieres")
    public List<FiliereActiveResponse> listerFilieres(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return filiereActiveUseCase.lister(id, etablissementId).stream().map(filiereActiveMapper::toResponse).toList();
    }

    @PutMapping("/configurations/{id}/filieres")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public ResultatActivation<FiliereActiveResponse> definirFilieres(@PathVariable UUID id,
                                                                      @RequestBody DefinirFilieresActivesRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        Set<UUID> filiereIds = requete.getFiliereIds() != null ? requete.getFiliereIds() : Set.of();
        ResultatActivation<FiliereActive> resultat = filiereActiveUseCase.definirEtatCible(
            id, etablissementId, filiereIds, acteurCourant());
        return mapResultat(resultat, filiereActiveMapper::toResponse);
    }

    @GetMapping("/configurations/{id}/niveaux")
    public List<NiveauActiveResponse> listerNiveaux(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return niveauActiveUseCase.lister(id, etablissementId).stream().map(niveauActiveMapper::toResponse).toList();
    }

    @PutMapping("/configurations/{id}/niveaux")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public ResultatActivation<NiveauActiveResponse> definirNiveaux(@PathVariable UUID id,
                                                                    @RequestBody DefinirNiveauxActifsRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        Set<UUID> niveauIds = requete.getNiveauIds() != null ? requete.getNiveauIds() : Set.of();
        ResultatActivation<NiveauActive> resultat = niveauActiveUseCase.definirEtatCible(
            id, etablissementId, niveauIds, acteurCourant());
        return mapResultat(resultat, niveauActiveMapper::toResponse);
    }

    @GetMapping("/niveaux-actifs/{id}/series")
    public List<SerieActiveResponse> listerSeries(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return serieActiveUseCase.lister(id, etablissementId).stream().map(serieActiveMapper::toResponse).toList();
    }

    @PutMapping("/niveaux-actifs/{id}/series")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    public ResultatActivation<SerieActiveResponse> definirSeries(@PathVariable UUID id,
                                                                  @RequestBody DefinirSeriesActivesRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        Set<UUID> serieIds = requete.getSerieIds() != null ? requete.getSerieIds() : Set.of();
        ResultatActivation<SerieActive> resultat = serieActiveUseCase.definirEtatCible(
            id, etablissementId, serieIds, acteurCourant());
        return mapResultat(resultat, serieActiveMapper::toResponse);
    }

    private <T, R> ResultatActivation<R> mapResultat(ResultatActivation<T> resultat, java.util.function.Function<T, R> mapper) {
        return new ResultatActivation<>(
            resultat.getAjoutees().stream().map(mapper).toList(),
            resultat.getModifiees().stream().map(mapper).toList(),
            resultat.getInchangees().stream().map(mapper).toList(),
            resultat.getRetirees().stream().map(mapper).toList()
        );
    }

    private UUID acteurCourant() {
        // TODO(jour ulterieur, integration JWT) : remplacer par l'identite reelle issue du token.
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
