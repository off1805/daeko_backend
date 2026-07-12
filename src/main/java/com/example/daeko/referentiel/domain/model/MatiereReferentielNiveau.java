package com.example.daeko.referentiel.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Représente l'association entre une matière du référentiel et un niveau
 */
public class MatiereReferentielNiveau extends EntiteReferentiel {

    private UUID matiereReferentielId;
    private UUID niveauId;
    private UUID serieId; // null = toutes séries
    private boolean estObligatoire;
    private BigDecimal coefficientSuggere;
    private String sourceCoefficient;
    private Integer baremeSpecifique;
    private String description;

    public MatiereReferentielNiveau() {
        super();
        this.estObligatoire = true;
    }

    public MatiereReferentielNiveau(UUID matiereReferentielId, UUID niveauId, UUID serieId,
                                     boolean estObligatoire, BigDecimal coefficientSuggere,
                                     String sourceCoefficient, Integer baremeSpecifique,
                                     String description, LocalDate dateEntreeVigueur) {
        super();
        this.matiereReferentielId = matiereReferentielId;
        this.niveauId = niveauId;
        this.serieId = serieId;
        this.estObligatoire = estObligatoire;
        this.coefficientSuggere = coefficientSuggere;
        this.sourceCoefficient = sourceCoefficient;
        this.baremeSpecifique = baremeSpecifique;
        this.description = description;
        if (dateEntreeVigueur != null) setDateEntreeVigueur(dateEntreeVigueur);
    }

    public UUID getMatiereReferentielId() { return matiereReferentielId; }
    public void setMatiereReferentielId(UUID matiereReferentielId) { this.matiereReferentielId = matiereReferentielId; }

    public UUID getNiveauId() { return niveauId; }
    public void setNiveauId(UUID niveauId) { this.niveauId = niveauId; }

    public UUID getSerieId() { return serieId; }
    public void setSerieId(UUID serieId) { this.serieId = serieId; }

    public boolean isEstObligatoire() { return estObligatoire; }
    public void setEstObligatoire(boolean estObligatoire) { this.estObligatoire = estObligatoire; }

    public BigDecimal getCoefficientSuggere() { return coefficientSuggere; }
    public void setCoefficientSuggere(BigDecimal coefficientSuggere) { this.coefficientSuggere = coefficientSuggere; }

    public String getSourceCoefficient() { return sourceCoefficient; }
    public void setSourceCoefficient(String sourceCoefficient) { this.sourceCoefficient = sourceCoefficient; }

    public Integer getBaremeSpecifique() { return baremeSpecifique; }
    public void setBaremeSpecifique(Integer baremeSpecifique) { this.baremeSpecifique = baremeSpecifique; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
