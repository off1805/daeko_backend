package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.port.in.AuditUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.domain.model.AuditEntree;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Consultation de la piste d'audit (5.1 et 6.3 du dossier — réservée à SUPER_ADMIN).
 * Ne dépréciera jamais, n'étend donc pas AbstractReferentielApplicationService :
 * l'audit est un journal append-only pur, sans cycle de vie ACTIVE/DEPRECATED.
 */
@Service
public class AuditApplicationService implements AuditUseCase {

    private final AuditPort auditPort;

    public AuditApplicationService(AuditPort auditPort) {
        this.auditPort = auditPort;
    }

    @Override
    public List<AuditEntree> consulter(String typeEntite, UUID entiteId, UUID utilisateurId,
                                       Instant du, Instant au) {
        return auditPort.rechercher(typeEntite, entiteId, utilisateurId, du, au);
    }
}