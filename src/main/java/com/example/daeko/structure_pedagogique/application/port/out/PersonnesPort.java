package com.example.daeko.structure_pedagogique.application.port.out;

import java.util.UUID;

/**
 * Port de lecture seule vers le futur module Personnes
 */
public interface PersonnesPort {

    /** Utilise par DesactiverClasseUseCase en EN_COURS (matrice 4.6) : appel synchrone. */
    boolean aZeroInscriptionActive(UUID classeId);
}
