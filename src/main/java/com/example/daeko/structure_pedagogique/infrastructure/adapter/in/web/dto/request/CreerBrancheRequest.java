package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request;

import java.util.UUID;

public class CreerBrancheRequest {

    private UUID sousSystemeId;
    private UUID ordreEnseignementId;
    private UUID typeEnseignementId;
    private String libelle; // optionnel : auto-genere si absent

    public UUID getSousSystemeId() { return sousSystemeId; }
    public void setSousSystemeId(UUID sousSystemeId) { this.sousSystemeId = sousSystemeId; }

    public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
    public void setOrdreEnseignementId(UUID ordreEnseignementId) { this.ordreEnseignementId = ordreEnseignementId; }

    public UUID getTypeEnseignementId() { return typeEnseignementId; }
    public void setTypeEnseignementId(UUID typeEnseignementId) { this.typeEnseignementId = typeEnseignementId; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
