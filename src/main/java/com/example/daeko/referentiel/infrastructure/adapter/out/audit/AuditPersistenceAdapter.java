package com.example.daeko.referentiel.infrastructure.adapter.out.audit;

import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.domain.model.AuditEntree;
import com.example.daeko.referentiel.domain.model.OperationAudit;
import com.example.daeko.referentiel.infrastructure.entity.AuditReferentielJpaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AuditPersistenceAdapter implements AuditPort {

    private final AuditJpaRepository auditJpaRepository;
    private final ObjectMapper objectMapper;

    public AuditPersistenceAdapter(AuditJpaRepository auditJpaRepository, ObjectMapper objectMapper) {
        this.auditJpaRepository = auditJpaRepository;
        this.objectMapper = objectMapper;
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

}