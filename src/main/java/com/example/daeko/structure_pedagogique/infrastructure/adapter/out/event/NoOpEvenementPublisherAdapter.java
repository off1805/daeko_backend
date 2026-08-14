package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.event;

import com.example.daeko.structure_pedagogique.domain.model.EvenementSortant;
import com.example.daeko.structure_pedagogique.application.port.out.EvenementPublisherPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(name = "outboxPublisherAdapter")
public class NoOpEvenementPublisherAdapter implements EvenementPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(NoOpEvenementPublisherAdapter.class);

    @Override
    public void publier(EvenementSortant evenement) {
        log.debug("[evenement-noop] {} -> {}", evenement.getNom(), evenement.getPayload());
    }
}
