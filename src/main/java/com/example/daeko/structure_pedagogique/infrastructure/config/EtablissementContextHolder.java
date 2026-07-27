package com.example.daeko.structure_pedagogique.infrastructure.config;

import java.util.UUID;

public final class EtablissementContextHolder {

    private static final ThreadLocal<UUID> CONTEXTE = new ThreadLocal<>();

    private EtablissementContextHolder() {
    }

    public static void definir(UUID etablissementId) {
        CONTEXTE.set(etablissementId);
    }

    public static UUID etablissementCourant() {
        UUID id = CONTEXTE.get();
        if (id == null) {
            throw new IllegalStateException(
                "Aucun etablissement_id dans le contexte -- EtablissementContextFilter n'a pas ete execute.");
        }
        return id;
    }

    public static void effacer() {
        CONTEXTE.remove();
    }
}
