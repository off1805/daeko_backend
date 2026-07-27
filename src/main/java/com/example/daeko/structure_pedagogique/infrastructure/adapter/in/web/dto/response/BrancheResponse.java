package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response;

import java.time.Instant;
import java.util.UUID;

public class BrancheResponse {

    private UUID id;
    private UUID etablissementId;
    private UUID sousSystemeId;
    private UUID ordreEnseignementId;
    private UUID typeEnseignementId;
    private String libelle;
    private String etat;
    private Instant dateCreation;
    private Instant dateModification;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getEtablissementId() { return etablissementId; }
    public void setEtablissementId(UUID etablissementId) { this.etablissementId = etablissementId; }

    public UUID getSousSystemeId() { return sousSystemeId; }
    public void setSousSystemeId(UUID sousSystemeId) { this.sousSystemeId = sousSystemeId; }

    public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
    public void setOrdreEnseignementId(UUID ordreEnseignementId) { this.ordreEnseignementId = ordreEnseignementId; }

    public UUID getTypeEnseignementId() { return typeEnseignementId; }
    public void setTypeEnseignementId(UUID typeEnseignementId) { this.typeEnseignementId = typeEnseignementId; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public Instant getDateCreation() { return dateCreation; }
    public void setDateCreation(Instant dateCreation) { this.dateCreation = dateCreation; }

    public Instant getDateModification() { return dateModification; }
    public void setDateModification(Instant dateModification) { this.dateModification = dateModification; }
}
