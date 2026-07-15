package com.example.daeko.referentiel.application.port.out;

import java.time.LocalDate;
import java.util.UUID;

public interface EvenementPublisherPort {
    void publierDepreciation(String typeEntite, UUID entiteId, String code,
                             LocalDate dateEffet, String motif);
}
