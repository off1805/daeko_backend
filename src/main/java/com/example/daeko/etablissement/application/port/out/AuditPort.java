package com.example.daeko.etablissement.application.port.out;

import java.util.UUID;

public interface AuditPort {
    void enregistrer(String typeEntite, UUID entiteId, String operation,
                     UUID utilisateurId, Object avant, Object apres);
}