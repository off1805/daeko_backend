package com.example.daeko.structure_pedagogique.application.port.out;

import java.util.UUID;

/**
 * Port de lecture seule vers le module Referentiel Educatif. Le module
 * Structure ne possede jamais ces entites, il les consulte uniquement --
 * d'ou un port dedie plutot qu'un repository classique.
 */
public interface ReferentielPort {

    EntiteReferentielVue chargerSousSysteme(UUID id);

    EntiteReferentielVue chargerOrdreEnseignement(UUID id);

    EntiteReferentielVue chargerTypeEnseignement(UUID id);

    NiveauVue chargerNiveau(UUID id);

    CycleVue chargerCycle(UUID id);

    FiliereVue chargerFiliere(UUID id);

    SerieVue chargerSerie(UUID id);

    MatiereVue chargerMatiere(UUID id);

    boolean estActive(UUID entiteId);

    /** Position globale d'un niveau dans la hierarchie academique -- utilisee pour SP-005. */
    int positionGlobale(UUID niveauId);

    interface EntiteReferentielVue {
        UUID getId();
        String getLibelle();
        boolean isActive();
    }

    interface NiveauVue extends EntiteReferentielVue {
        UUID getCycleId();
    }

    interface CycleVue {
        UUID getId();
        UUID getSousSystemeId();
        UUID getOrdreEnseignementId();
    }

    interface FiliereVue extends EntiteReferentielVue {
        UUID getOrdreEnseignementId();
        UUID getTypeEnseignementId();
    }

    interface SerieVue extends EntiteReferentielVue {
        UUID getFiliereId();
        UUID getNiveauApparitionId();
    }

    interface MatiereVue extends EntiteReferentielVue {
        UUID getSousSystemeId();
    }
}
