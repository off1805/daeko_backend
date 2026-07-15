package com.example.daeko.referentiel.infrastructure.adapter.out.event;

import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.event.EntreeDeprecieeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class EvenementPublisherAdapter implements EvenementPublisherPort {

    private final ApplicationEventPublisher publisher;

    public EvenementPublisherAdapter(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publierDepreciation(String typeEntite, UUID entiteId, String code,
                                    LocalDate dateEffet, String motif) {
        publisher.publishEvent(new EntreeDeprecieeEvent(typeEntite, entiteId, code, dateEffet, motif));
    }
}
