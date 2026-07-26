package com.example.daeko.etablissement.application.dto;

import com.example.daeko.etablissement.domain.model.StatutJuridique;

import java.util.UUID;

public class CreerEtablissementCommand {

    private final String nomOfficiel;
    private final String nomOfficielEn;
    private final String sigle;
    private final StatutJuridique statutJuridique;
    private final String numeroAgrement;
    private final String codeEtablissement;
    private final String devisePropre;
    private final UUID regionId;
    private final UUID departementId;
    private final UUID arrondissementId;
    private final String ville;
    private final String boitePostale;
    private final String telephones;
    private final String email;
    private final String siteWeb;
    private final UUID utilisateurId;

    public CreerEtablissementCommand(String nomOfficiel, String nomOfficielEn, String sigle,
                                     StatutJuridique statutJuridique, String numeroAgrement,
                                     String codeEtablissement, String devisePropre, UUID regionId,
                                     UUID departementId, UUID arrondissementId, String ville,
                                     String boitePostale, String telephones, String email,
                                     String siteWeb, UUID utilisateurId) {
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
        this.email = email;
        this.siteWeb = siteWeb;
        this.utilisateurId = utilisateurId;
    }

    public String getNomOfficiel() { return nomOfficiel; }
    public String getNomOfficielEn() { return nomOfficielEn; }
    public String getSigle() { return sigle; }
    public StatutJuridique getStatutJuridique() { return statutJuridique; }
    public String getNumeroAgrement() { return numeroAgrement; }
    public String getCodeEtablissement() { return codeEtablissement; }
    public String getDevisePropre() { return devisePropre; }
    public UUID getRegionId() { return regionId; }
    public UUID getDepartementId() { return departementId; }
    public UUID getArrondissementId() { return arrondissementId; }
    public String getVille() { return ville; }
    public String getBoitePostale() { return boitePostale; }
    public String getTelephones() { return telephones; }
    public String getEmail() { return email; }
    public String getSiteWeb() { return siteWeb; }
    public UUID getUtilisateurId() { return utilisateurId; }
}