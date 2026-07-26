package com.example.daeko.etablissement.application.dto;

import com.example.daeko.etablissement.domain.model.StatutEtablissement;

import java.util.UUID;

public class ChangerStatutCommand {

    private final UUID id;
    private final StatutEtablissement nouveauStatut;
    private final String motif;
    private final UUID utilisateurId;

    public ChangerStatutCommand(UUID id, StatutEtablissement nouveauStatut, String motif, UUID utilisateurId) {
        this.id = id;
        this.nouveauStatut = nouveauStatut;
        this.motif = motif;
        this.utilisateurId = utilisateurId;
    }

    public UUID getId() { return id; }
    public StatutEtablissement getNouveauStatut() { return nouveauStatut; }
    public String getMotif() { return motif; }
    public UUID getUtilisateurId() { return utilisateurId; }
}