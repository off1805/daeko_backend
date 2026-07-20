package com.example.daeko.referentiel.application.port.out;

import com.example.daeko.referentiel.domain.model.AuditEntree;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AuditPort {
    void enregistrer(String typeEntite, UUID entiteId, String operation,
                     UUID utilisateurId, String motif, Object avant, Object apres);

    List<AuditEntree> rechercher(String typeEntite, UUID entiteId, UUID utilisateurId,
                                 Instant du, Instant au);
}