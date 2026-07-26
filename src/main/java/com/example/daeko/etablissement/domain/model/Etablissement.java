package com.example.daeko.etablissement.domain.model;

import java.util.UUID;

public class Etablissement extends EntiteEtablissement {

    public static final String SCHEMA = "etablissement";

    private String nomOfficiel;
    private String nomOfficielEn;
    private String sigle;
    private StatutJuridique statutJuridique;
    private String numeroAgrement;
    private String codeEtablissement;
    private String devisePropre;

    // Localisation
    private UUID regionId;
    private UUID departementId;
    private UUID arrondissementId;
    private String ville;
    private String boitePostale;

    // Coordonnées
    private String telephones;
    private String email;
    private String siteWeb;

    // Identité visuelle
    private String logoUrl;
    private String logoSha256;

    // Paramètres opérationnels
    private String langueParDefaut = "fr";
    private String formatDateDefaut = "DD/MM/YYYY";

    // Constructeur minimal
    public Etablissement(String nomOfficiel, StatutJuridique statutJuridique, UUID regionId,
                         UUID departementId, UUID arrondissementId, String ville) {
        super();
        this.nomOfficiel = nomOfficiel;
        this.statutJuridique = statutJuridique;
        this.regionId = regionId;
        this.departementId = departementId;
        this.arrondissementId = arrondissementId;
        this.ville = ville;
    }

    // Constructeur complet
    public Etablissement(String nomOfficiel, String nomOfficielEn, String sigle, StatutJuridique statutJuridique,
                         String numeroAgrement, String codeEtablissement, String devisePropre,
                         UUID regionId, UUID departementId, UUID arrondissementId, String ville,
                         String boitePostale, String telephones, String email, String siteWeb) {
        super();
        this.nomOfficiel = nomOfficiel;
        this.nomOfficielEn = nomOfficielEn;
        this.sigle = sigle;
        this.statutJuridique = statutJuridique;
        this.numeroAgrement = numeroAgrement;
        this.codeEtablissement = codeEtablissement;
        this.devisePropre = devisePropre;
        this.regionId = regionId;
        this.departementId = departementId;
        this.arrondissementId = arrondissementId;
        this.ville = ville;
        this.boitePostale = boitePostale;
        this.telephones = telephones;
        setEmail(email);
        this.siteWeb = siteWeb;
    }

    // Getters et Setters
    public String getNomOfficiel() { return nomOfficiel; }
    public void setNomOfficiel(String nomOfficiel) { this.nomOfficiel = nomOfficiel; }

    public String getNomOfficielEn() { return nomOfficielEn; }
    public void setNomOfficielEn(String nomOfficielEn) { this.nomOfficielEn = nomOfficielEn; }

    public String getSigle() { return sigle; }
    public void setSigle(String sigle) { this.sigle = sigle; }

    public StatutJuridique getStatutJuridique() { return statutJuridique; }
    public void setStatutJuridique(StatutJuridique statutJuridique) { this.statutJuridique = statutJuridique; }

    public String getNumeroAgrement() { return numeroAgrement; }
    public void setNumeroAgrement(String numeroAgrement) { this.numeroAgrement = numeroAgrement; }

    public String getCodeEtablissement() { return codeEtablissement; }
    public void setCodeEtablissement(String codeEtablissement) { this.codeEtablissement = codeEtablissement; }

    public String getDevisePropre() { return devisePropre; }
    public void setDevisePropre(String devisePropre) { this.devisePropre = devisePropre; }

    public UUID getRegionId() { return regionId; }
    public void setRegionId(UUID regionId) { this.regionId = regionId; }

    public UUID getDepartementId() { return departementId; }
    public void setDepartementId(UUID departementId) { this.departementId = departementId; }

    public UUID getArrondissementId() { return arrondissementId; }
    public void setArrondissementId(UUID arrondissementId) { this.arrondissementId = arrondissementId; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getBoitePostale() { return boitePostale; }
    public void setBoitePostale(String boitePostale) { this.boitePostale = boitePostale; }

    public String getTelephones() { return telephones; }
    public void setTelephones(String telephones) { this.telephones = telephones; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email != null ? email.toLowerCase() : null; }

    public String getSiteWeb() { return siteWeb; }
    public void setSiteWeb(String siteWeb) { this.siteWeb = siteWeb; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getLogoSha256() { return logoSha256; }
    public void setLogoSha256(String logoSha256) { this.logoSha256 = logoSha256; }

    public String getLangueParDefaut() { return langueParDefaut; }
    public void setLangueParDefaut(String langueParDefaut) { this.langueParDefaut = langueParDefaut; }

    public String getFormatDateDefaut() { return formatDateDefaut; }
    public void setFormatDateDefaut(String formatDateDefaut) { this.formatDateDefaut = formatDateDefaut; }
}