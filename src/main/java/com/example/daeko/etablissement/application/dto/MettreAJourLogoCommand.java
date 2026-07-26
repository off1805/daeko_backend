package com.example.daeko.etablissement.application.dto;

import java.util.UUID;

public class MettreAJourLogoCommand {

    private final UUID etablissementId;
    private final String logoUrl;
    private final UUID utilisateurId;

    public MettreAJourLogoCommand(UUID etablissementId, String logoUrl, UUID utilisateurId) {
        this.etablissementId = etablissementId;
        this.logoUrl = logoUrl;
        this.utilisateurId = utilisateurId;
    }

    public UUID getEtablissementId() { return etablissementId; }
    public String getLogoUrl() { return logoUrl; }
    public UUID getUtilisateurId() { return utilisateurId; }
}