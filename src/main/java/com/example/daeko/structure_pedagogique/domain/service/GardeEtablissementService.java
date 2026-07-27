package com.example.daeko.structure_pedagogique.domain.service;

import com.example.daeko.structure_pedagogique.application.port.out.EtablissementPort;
import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;

import java.util.UUID;

/**
 * Verifie SP-021 en tete de chaque use case d'ecriture : aucune ecriture
 * n'est autorisee si l'etablissement est SUSPENDU ou ARCHIVE.
 */
public class GardeEtablissementService {

    private final EtablissementPort etablissementPort;

    public GardeEtablissementService(EtablissementPort etablissementPort) {
        this.etablissementPort = etablissementPort;
    }

    public void exigerEtablissementActif(UUID etablissementId) {
        if (!etablissementPort.estActif(etablissementId)) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_021,
                "L'etablissement " + etablissementId + " est suspendu ou archive : ecriture refusee."
            );
        }
    }
}
