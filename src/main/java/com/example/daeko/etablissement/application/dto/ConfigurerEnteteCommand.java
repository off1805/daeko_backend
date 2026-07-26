package com.example.daeko.etablissement.application.dto;

import java.util.List;
import java.util.UUID;

public class ConfigurerEnteteCommand {

    private final UUID etablissementId;
    private final List<LigneEnteteDTO> lignes;
    private final UUID utilisateurId;

    public ConfigurerEnteteCommand(UUID etablissementId, List<LigneEnteteDTO> lignes, UUID utilisateurId) {
        this.etablissementId = etablissementId;
        this.lignes = lignes;
        this.utilisateurId = utilisateurId;
    }

    public UUID getEtablissementId() { return etablissementId; }
    public List<LigneEnteteDTO> getLignes() { return lignes; }
    public UUID getUtilisateurId() { return utilisateurId; }

    public static class LigneEnteteDTO {
        private final Integer ordre;
        private final String texteFr;
        private final String texteEn;

        public LigneEnteteDTO(Integer ordre, String texteFr, String texteEn) {
            this.ordre = ordre;
            this.texteFr = texteFr;
            this.texteEn = texteEn;
        }

        public Integer getOrdre() { return ordre; }
        public String getTexteFr() { return texteFr; }
        public String getTexteEn() { return texteEn; }
    }
}