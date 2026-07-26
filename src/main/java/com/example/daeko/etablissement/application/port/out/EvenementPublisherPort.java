package com.example.daeko.etablissement.application.port.out;

import com.example.daeko.etablissement.domain.model.StatutEtablissement;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface EvenementPublisherPort {

    void publierChangementStatut(UUID etablissementId,
                                 String codeEtablissement,
                                 StatutEtablissement ancienStatut,
                                 StatutEtablissement nouveauStatut,
                                 OffsetDateTime dateEffet,
                                 String motif);
}