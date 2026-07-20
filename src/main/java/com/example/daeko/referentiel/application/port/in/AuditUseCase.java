package com.example.daeko.referentiel.application.port.in;

import com.example.daeko.referentiel.domain.model.AuditEntree;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AuditUseCase {
    List<AuditEntree> consulter(String typeEntite, UUID entiteId, UUID utilisateurId,
                                Instant du, Instant au);
}