package com.example.daeko.etablissement.domain.model;

import java.util.UUID;

public class Signataire extends EntiteEtablissement {

    public static final String SCHEMA = "etablissement";

    private UUID etablissementId;
    private String nomComplet;
    private String titreOfficiel;
    private String titreOfficielEn;
    private boolean estPrincipal;
    private String signatureUrl;

    public Signataire() {
        super();
    }

    public Signataire(UUID etablissementId, String nomComplet, String titreOfficiel, boolean estPrincipal) {
        super();
        this.etablissementId = etablissementId;
        this.nomComplet = nomComplet;
        this.titreOfficiel = titreOfficiel;
        this.estPrincipal = estPrincipal;
    }

    public UUID getEtablissementId() { return etablissementId; }
    public void setEtablissementId(UUID etablissementId) { this.etablissementId = etablissementId; }

    public String getNomComplet() { return nomComplet; }
    public void setNomComplet(String nomComplet) { this.nomComplet = nomComplet; }

    public String getTitreOfficiel() { return titreOfficiel; }
    public void setTitreOfficiel(String titreOfficiel) { this.titreOfficiel = titreOfficiel; }

    public String getTitreOfficielEn() { return titreOfficielEn; }
    public void setTitreOfficielEn(String titreOfficielEn) { this.titreOfficielEn = titreOfficielEn; }

    public boolean isEstPrincipal() { return estPrincipal; }
    public void setEstPrincipal(boolean estPrincipal) { this.estPrincipal = estPrincipal; }

    public String getSignatureUrl() { return signatureUrl; }
    public void setSignatureUrl(String signatureUrl) { this.signatureUrl = signatureUrl; }
}