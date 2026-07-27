package com.example.daeko.structure_pedagogique.domain.exception;

import java.util.Map;
import java.util.UUID;

/**
 * Cas particulier SP-024 : une ressource demandee n'existe pas dans le
 * perimetre de l'etablissement courant (soit elle n'existe pas du tout, soit
 * elle appartient a un autre etablissement -- dans les deux cas la reponse
 * doit etre identique, pour ne jamais confirmer l'existence d'une ressource
 * d'un tenant tiers).
 */
public class RessourceIntrouvableException extends StructureMetierException {

    public RessourceIntrouvableException(String typeRessource, UUID id) {
        super(
            CodeErreurStructure.SP_024,
            "Ressource introuvable : " + typeRessource + " (" + id + ") n'existe pas dans ce perimetre.",
            Map.of("type", typeRessource, "id", id.toString())
        );
    }
}
