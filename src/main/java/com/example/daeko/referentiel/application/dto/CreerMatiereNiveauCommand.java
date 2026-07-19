package com.example.daeko.referentiel.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreerMatiereNiveauCommand {

    private final UUID matiereReferentielId;
    private final UUID niveauId;
    private final UUID serieId; // nullable : absence de série
    private final boolean estObligatoire;
    private final BigDecimal coefficientSuggere;
    private final String sourceCoefficient;
    private final Integer baremeSpecifique;
    private final String description;
    private final LocalDate dateEntreeVigueur;
    private final UUID utilisateurId;

    public CreerMatiereNiveauCommand(UUID matiereReferentielId, UUID niveauId, UUID serieId,
                                     boolean estObligatoire, BigDecimal coefficientSuggere,
                                     String sourceCoefficient, Integer baremeSpecifique,
                                     String description, LocalDate dateEntreeVigueur,
                                     UUID utilisateurId) {
        this.matiereReferentielId = matiereReferentielId;
        this.niveauId = niveauId;
        this.serieId = serieId;
        this.estObligatoire = estObligatoire;
        this.coefficientSuggere = coefficientSuggere;
        this.sourceCoefficient = sourceCoefficient;
        this.baremeSpecifique = baremeSpecifique;
        this.description = description;
        this.dateEntreeVigueur = dateEntreeVigueur;
        this.utilisateurId = utilisateurId;
    }

    public UUID getMatiereReferentielId() { return matiereReferentielId; }
    public UUID getNiveauId() { return niveauId; }
    public UUID getSerieId() { return serieId; }
    public boolean isEstObligatoire() { return estObligatoire; }
    public BigDecimal getCoefficientSuggere() { return coefficientSuggere; }
    public String getSourceCoefficient() { return sourceCoefficient; }
    public Integer getBaremeSpecifique() { return baremeSpecifique; }
    public String getDescription() { return description; }
    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public UUID getUtilisateurId() { return utilisateurId; }
}