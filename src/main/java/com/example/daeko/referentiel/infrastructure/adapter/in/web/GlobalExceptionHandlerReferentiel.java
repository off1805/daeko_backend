package com.example.daeko.referentiel.infrastructure.adapter.in.web;

import com.example.daeko.referentiel.domain.exception.*;
import com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response.ErreurResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerReferentiel {

    @ExceptionHandler(ViolationUniciteException.class)
    public ResponseEntity<ErreurResponse> handleViolationUnicite(ViolationUniciteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(IncoherenceSousSystemeException.class)
    public ResponseEntity<ErreurResponse> handleIncoherenceSousSysteme(IncoherenceSousSystemeException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(SerieTropTotException.class)
    public ResponseEntity<ErreurResponse> handleSerieTropTot(SerieTropTotException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(IncoherenceOrdreException.class)
    public ResponseEntity<ErreurResponse> handleIncoherenceOrdre(IncoherenceOrdreException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(CoherenceSerieNiveauApparitionException.class)
    public ResponseEntity<ErreurResponse> handleCoherenceSerieNiveauApparition(CoherenceSerieNiveauApparitionException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(DeprecationSansMotifException.class)
    public ResponseEntity<ErreurResponse> handleDeprecationSansMotif(DeprecationSansMotifException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(ParentDeprecieException.class)
    public ResponseEntity<ErreurResponse> handleParentDepreciee(ParentDeprecieException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(SuppressionInterditeException.class)
    public ResponseEntity<ErreurResponse> handleSuppressionInterdite(SuppressionInterditeException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(ModificationStructurelleInterditeException.class)
    public ResponseEntity<ErreurResponse> handleModificationStructurelle(ModificationStructurelleInterditeException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(EntiteIntrouvableException.class)
    public ResponseEntity<ErreurResponse> handleEntiteIntrouvable(EntiteIntrouvableException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(ReferentielDomainException.class)
    public ResponseEntity<ErreurResponse> handleReferentielDomain(ReferentielDomainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErreurResponse(ex.getCode(), ex.getMessage()));
    }
}
