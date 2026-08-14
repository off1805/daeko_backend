package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web;

import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response.ErreurResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerStructurePedagogique {

    @ExceptionHandler(StructureMetierException.class)
    public ResponseEntity<ErreurResponse> gererErreurMetier(StructureMetierException exception) {
        ErreurResponse corps = new ErreurResponse(
            exception.getCode().name().replace('_', '-'),
            exception.getMessage(),
            exception.getDetails()
        );
        return ResponseEntity.status(exception.getCode().getStatutHttp()).body(corps);
    }
}
