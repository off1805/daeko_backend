package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.application.port.in.TypeEnseignementUseCase;
import com.example.daeko.referentiel.application.dto.CreerTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ModifierTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.DeprecierTypeEnseignementCommand;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request.TypeEnseignementRequest;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.TypeEnseignementResponse;
import com.example.daeko.referentiel.infrastructure.mapper.TypeEnseignementMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/types-enseignement")
public class TypeEnseignementController {

    private final TypeEnseignementUseCase typeEnseignementUseCase;
    private final TypeEnseignementMapper typeEnseignementMapper;

    public TypeEnseignementController(TypeEnseignementUseCase typeEnseignementUseCase,
                                      TypeEnseignementMapper typeEnseignementMapper) {
        this.typeEnseignementUseCase = typeEnseignementUseCase;
        this.typeEnseignementMapper = typeEnseignementMapper;
    }

    @PostMapping
    public ResponseEntity<TypeEnseignementResponse> creer(@RequestBody TypeEnseignementRequest request) {
        CreerTypeEnseignementCommand command = typeEnseignementMapper.toCreerCommand(request);
        TypeEnseignement typeCree = typeEnseignementUseCase.creer(command);
        return new ResponseEntity<>(typeEnseignementMapper.toResponse(typeCree), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeEnseignementResponse> modifier(@PathVariable UUID id,
                                                             @RequestBody TypeEnseignementRequest request) {
        ModifierTypeEnseignementCommand command = typeEnseignementMapper.toModifierCommand(id, request);
        TypeEnseignement typeModifie = typeEnseignementUseCase.modifier(command);
        return ResponseEntity.ok(typeEnseignementMapper.toResponse(typeModifie));
    }

    @PutMapping("/{id}/deprecier")
    public ResponseEntity<TypeEnseignementResponse> deprecier(@PathVariable UUID id,
                                                              @RequestParam UUID utilisateurId) {
        DeprecierTypeEnseignementCommand command = new DeprecierTypeEnseignementCommand(id, utilisateurId);
        TypeEnseignement typeDeprecie = typeEnseignementUseCase.deprecier(command);
        return ResponseEntity.ok(typeEnseignementMapper.toResponse(typeDeprecie));
    }
}