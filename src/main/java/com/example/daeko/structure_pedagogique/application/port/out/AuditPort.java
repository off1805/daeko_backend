package com.example.daeko.structure_pedagogique.application.port.out;

import com.example.daeko.structure_pedagogique.domain.model.AuditEntreeStructure;

public interface AuditPort {

    void enregistrer(AuditEntreeStructure entree);
}
