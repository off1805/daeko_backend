package com.example.daeko.referentiel.infrastructure.adapter.out.audit;

import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.domain.model.AuditEntree;
import com.example.daeko.referentiel.domain.model.OperationAudit;
import com.example.daeko.referentiel.infrastructure.entity.AuditReferentielJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.AuditMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AuditPersistenceAdapter implements AuditPort {

    private final AuditJpaRepository auditJpaRepository;
    private final ObjectMapper objectMapper;
    private final AuditMapper auditMapper;

    public AuditPersistenceAdapter(AuditJpaRepository auditJpaRepository,
                                   ObjectMapper objectMapper,
                                   AuditMapper auditMapper) {
        this.auditJpaRepository = auditJpaRepository;
        this.objectMapper = objectMapper;
        this.auditMapper = auditMapper;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void enregistrer(String typeEntite, UUID entiteId, String operation,
                            UUID utilisateurId, String motif, Object avant, Object apres) {
        AuditReferentielJpaEntity entite = new AuditReferentielJpaEntity();
        entite.setTypeEntite(typeEntite);
        entite.setEntiteId(entiteId);
        entite.setOperation(OperationAudit.valueOf(operation));
        entite.setUtilisateurId(utilisateurId);
        entite.setMotif(motif);
        entite.setValeursAvant(toJson(avant));
        entite.setValeursApres(toJson(apres));
        auditJpaRepository.save(entite);
    }

    @Override
    public List<AuditEntree> rechercher(String typeEntite, UUID entiteId, UUID utilisateurId,
                                        Instant du, Instant au) {
        return auditJpaRepository.rechercher(typeEntite, entiteId, utilisateurId, du, au)
                .stream()
                .map(auditMapper::toDomain)
                .collect(Collectors.toList());
    }

    private String toJson(Object obj) {
        if (obj == null) return null;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Erreur de sérialisation JSON pour l'audit", e);
        }
    }
}

interface AuditJpaRepository extends JpaRepository<AuditReferentielJpaEntity, UUID> {

    @Query(value = """
        SELECT *
        FROM audit_referentiel a
        WHERE
            (:typeEntite IS NULL OR a.type_entite = :typeEntite)
        AND (:entiteId IS NULL OR a.entite_id = :entiteId)
        AND (:utilisateurId IS NULL OR a.utilisateur_id = :utilisateurId)
        AND (CAST(:du AS timestamptz) IS NULL OR a.horodatage >= CAST(:du AS timestamptz))
        AND (CAST(:au AS timestamptz) IS NULL OR a.horodatage <= CAST(:au AS timestamptz))
        ORDER BY a.horodatage DESC
        """, nativeQuery = true)
    List<AuditReferentielJpaEntity> rechercher(
            @Param("typeEntite") String typeEntite,
            @Param("entiteId") UUID entiteId,
            @Param("utilisateurId") UUID utilisateurId,
            @Param("du") Instant du,
            @Param("au") Instant au);
}