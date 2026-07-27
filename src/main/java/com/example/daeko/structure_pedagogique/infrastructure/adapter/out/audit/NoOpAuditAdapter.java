package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.audit;

import com.example.daeko.structure_pedagogique.domain.model.AuditEntreeStructure;
import com.example.daeko.structure_pedagogique.application.port.out.AuditPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(name = "auditStructureServiceAdapter")
public class NoOpAuditAdapter implements AuditPort {

    private static final Logger log = LoggerFactory.getLogger(NoOpAuditAdapter.class);

    @Override
    public void enregistrer(AuditEntreeStructure entree) {
        log.debug("[audit-noop] {} sur {} ({}) par {}",
            entree.getOperation(), entree.getCibleType(), entree.getCibleId(), entree.getUtilisateurId());
    }
}
