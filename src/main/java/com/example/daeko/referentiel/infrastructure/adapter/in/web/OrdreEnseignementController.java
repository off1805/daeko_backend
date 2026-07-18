package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.port.in.OrdreEnseignementUseCase;
import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.CreerOrdreEnseignementRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.OrdreEnseignementResponse;
import com.example.daeko.referentiel.infrastructure.mapper.OrdreEnseignementMapper;
import com.example.daeko.referentiel.application.dto.CreerOrdreEnseignementCommand;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ordres-enseignement")
public class OrdreEnseignementController {

    private final OrdreEnseignementUseCase ordreEnseignementUseCase;
    private final OrdreEnseignementMapper ordreEnseignementMapper;

    public OrdreEnseignementController(OrdreEnseignementUseCase ordreEnseignementUseCase,
                                       OrdreEnseignementMapper ordreEnseignementMapper) {
        this.ordreEnseignementUseCase = ordreEnseignementUseCase;
        this.ordreEnseignementMapper = ordreEnseignementMapper;
    }

    @PostMapping
    public ResponseEntity<OrdreEnseignementResponse> creer(@RequestBody CreerOrdreEnseignementRequest request) {

        CreerOrdreEnseignementCommand command = new CreerOrdreEnseignementCommand(
                request.getCode(),
                request.getLibelle(),
                request.getTutelleMinisterielle(),
                request.getTutelleMinisterielleEn(),
                request.getRang(),
                request.getDescription(),
                request.getDateEntreeVigueur(),
                java.util.UUID.randomUUID() // ID utilisateur temporaire
        );

        OrdreEnseignement ordreCree = ordreEnseignementUseCase.creer(command);

        OrdreEnseignementResponse response = new OrdreEnseignementResponse();
        response.setId(ordreCree.getId());
        response.setCode(ordreCree.getCode());
        response.setLibelle(ordreCree.getLibelle());
        response.setTutelleMinisterielle(ordreCree.getTutelleMinisterielle());
        response.setTutelleMinisterielleEn(ordreCree.getTutelleMinisterielleEn());
        response.setRang(ordreCree.getRang());
        response.setDescription(ordreCree.getDescription());
        response.setDateEntreeVigueur(ordreCree.getDateEntreeVigueur());

        if (ordreCree.getEtat() != null) {
            response.setEtat(ordreCree.getEtat().name());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}