package com.example.daeko.etablissement.domain.model;

import java.util.UUID;

public class EnteteLigne extends EntiteEtablissement {

    public static final String SCHEMA = "etablissement";

    private UUID etablissementId;
    private Integer ordre;
    private String texteFr;
    private String texteEn;

    public EnteteLigne() {
        super();
    }

    public EnteteLigne(UUID etablissementId, Integer ordre, String texteFr, String texteEn) {
        super();
        this.etablissementId = etablissementId;
        this.ordre = ordre;
        this.texteFr = texteFr;
        this.texteEn = texteEn;
    }

    public UUID getEtablissementId() { return etablissementId; }
    public void setEtablissementId(UUID etablissementId) { this.etablissementId = etablissementId; }

    public Integer getOrdre() { return ordre; }
    public void setOrdre(Integer ordre) { this.ordre = ordre; }

    public String getTexteFr() { return texteFr; }
    public void setTexteFr(String texteFr) { this.texteFr = texteFr; }

    public String getTexteEn() { return texteEn; }
    public void setTexteEn(String texteEn) { this.texteEn = texteEn; }
}