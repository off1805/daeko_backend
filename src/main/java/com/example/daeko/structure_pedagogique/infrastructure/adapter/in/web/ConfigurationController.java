package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web;

import com.example.daeko.structure_pedagogique.application.dto.CreerConfigurationCommand;
import com.example.daeko.structure_pedagogique.application.port.in.ConfigurationUseCase;
import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request.CreerConfigurationRequest;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.ConfigurationBrancheAnneeResponse;
import com.example.daeko.structure_pedagogique.infrastructure.config.EtablissementContextHolder;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.ConfigurationBrancheAnneeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ConfigurationController {

    private final ConfigurationUseCase configurationUseCase;
    private final ConfigurationBrancheAnneeMapper mapper;

    public ConfigurationController(ConfigurationUseCase configurationUseCase, ConfigurationBrancheAnneeMapper mapper) {
        this.configurationUseCase = configurationUseCase;
        this.mapper = mapper;
    }

    @PostMapping("/branches/{brancheId}/configurations")
    // @PreAuthorize("hasAuthority('structure.configurer')")
    @ResponseStatus(HttpStatus.CREATED)
    public ConfigurationBrancheAnneeResponse creer(@PathVariable UUID brancheId, @RequestBody CreerConfigurationRequest requete) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        ConfigurationBrancheAnnee configuration = configurationUseCase.creer(new CreerConfigurationCommand(
            brancheId, etablissementId, requete.getAnneeAcademiqueId(),
            requete.isDupliquerDepuisPrecedente(), requete.isCopierClasses(), acteurCourant()));
        return mapper.toResponse(configuration);
    }

    @GetMapping("/configurations/{id}")
    public ConfigurationBrancheAnneeResponse detail(@PathVariable UUID id) {
        UUID etablissementId = EtablissementContextHolder.etablissementCourant();
        return mapper.toResponse(configurationUseCase.consulterParId(id, etablissementId));
    }

    private UUID acteurCourant() {
        // TODO(jour ulterieur, integration JWT) : remplacer par l'identite reelle issue du token.
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
