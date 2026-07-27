package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request;

import java.util.UUID;

public class CreerConfigurationRequest {

    private UUID anneeAcademiqueId;
    private boolean dupliquerDepuisPrecedente;
    private boolean copierClasses;

    public UUID getAnneeAcademiqueId() { return anneeAcademiqueId; }
    public void setAnneeAcademiqueId(UUID anneeAcademiqueId) { this.anneeAcademiqueId = anneeAcademiqueId; }

    public boolean isDupliquerDepuisPrecedente() { return dupliquerDepuisPrecedente; }
    public void setDupliquerDepuisPrecedente(boolean dupliquerDepuisPrecedente) { this.dupliquerDepuisPrecedente = dupliquerDepuisPrecedente; }

    public boolean isCopierClasses() { return copierClasses; }
    public void setCopierClasses(boolean copierClasses) { this.copierClasses = copierClasses; }
}
