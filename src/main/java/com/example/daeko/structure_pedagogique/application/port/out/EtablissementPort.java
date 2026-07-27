package com.example.daeko.structure_pedagogique.application.port.out;

import java.util.UUID;

/**
 * Port de lecture seule vers le module Etablissement. Appele en tete de
 * chaque use case d'ecriture pour faire respecter SP-021 (ecriture refusee
 * si l'etablissement est SUSPENDU ou ARCHIVE).
 */
public interface EtablissementPort {

    boolean estActif(UUID etablissementId);
}
