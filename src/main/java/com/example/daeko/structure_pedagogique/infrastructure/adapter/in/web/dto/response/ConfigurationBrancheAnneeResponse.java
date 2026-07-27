package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.response;

import java.util.UUID;

public class ConfigurationBrancheAnneeResponse {

    private UUID id;
    private UUID brancheId;
    private UUID anneeAcademiqueId;
    private String etat;
    private UUID dupliqueeDepuisId;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getBrancheId() { return brancheId; }
    public void setBrancheId(UUID brancheId) { this.brancheId = brancheId; }

    public UUID getAnneeAcademiqueId() { return anneeAcademiqueId; }
    public void setAnneeAcademiqueId(UUID anneeAcademiqueId) { this.anneeAcademiqueId = anneeAcademiqueId; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public UUID getDupliqueeDepuisId() { return dupliqueeDepuisId; }
    public void setDupliqueeDepuisId(UUID dupliqueeDepuisId) { this.dupliqueeDepuisId = dupliqueeDepuisId; }
}
