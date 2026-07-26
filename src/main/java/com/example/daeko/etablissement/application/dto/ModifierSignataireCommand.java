package com.example.daeko.etablissement.application.dto;

import java.util.UUID;

public class ModifierSignataireCommand {

    private final UUID id;
    private final String nomComplet;
    private final String titreOfficiel;
    private final String titreOfficielEn;
    private final boolean estPrincipal;
    private final String signatureUrl;
    private final UUID utilisateurId;

    public ModifierSignataireCommand(UUID id, String nomComplet, String titreOfficiel,
                                     String titreOfficielEn, boolean estPrincipal,
                                     String signatureUrl, UUID utilisateurId) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.titreOfficiel = titreOfficiel;
        this.titreOfficielEn = titreOfficielEn;
        this.estPrincipal = estPrincipal;
        this.signatureUrl = signatureUrl;
        this.utilisateurId = utilisateurId;
    }

    public UUID getId() { return id; }
    public String getNomComplet() { return nomComplet; }
    public String getTitreOfficiel() { return titreOfficiel; }
    public String getTitreOfficielEn() { return titreOfficielEn; }
    public boolean isEstPrincipal() { return estPrincipal; }
    public String getSignatureUrl() { return signatureUrl; }
    public UUID getUtilisateurId() { return utilisateurId; }
}