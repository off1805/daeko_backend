package com.example.daeko.structure_pedagogique.domain.model;

import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.model.SourceMatiere;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class MatiereActive {

    private UUID id;
    private final UUID niveauActiveId;
    private final UUID serieActiveId;         // null = vaut pour tout le niveau
    private final UUID matiereReferentielId;  // nullable, XOR avec matiereLocaleId
    private final UUID matiereLocaleId;       // nullable, XOR avec matiereReferentielId
    private BigDecimal coefficient;
    private Integer bareme;                   // null = bareme par defaut
    private boolean estObligatoire;
    private Instant dateCreation;
    private Instant dateModification;
    private UUID creePar;
    private UUID modifiePar;

    private MatiereActive(UUID niveauActiveId, UUID serieActiveId, UUID matiereReferentielId, UUID matiereLocaleId,
                           BigDecimal coefficient, Integer bareme, boolean estObligatoire, UUID creePar) {
        this.niveauActiveId = Objects.requireNonNull(niveauActiveId, "niveauActiveId requis");
        this.serieActiveId = serieActiveId;

        int nbSourcesRenseignees = (matiereReferentielId != null ? 1 : 0) + (matiereLocaleId != null ? 1 : 0);
        if (nbSourcesRenseignees != 1) {
            throw new StructureMetierException(CodeErreurStructure.SP_008,
                "Une MatiereActive doit referencer exactement une source (referentiel OU locale), jamais zero ni deux.");
        }
        this.matiereReferentielId = matiereReferentielId;
        this.matiereLocaleId = matiereLocaleId;

        exigerCoefficientValide(coefficient);
        this.coefficient = coefficient;

        if (bareme != null && bareme <= 0) {
            throw new StructureMetierException(CodeErreurStructure.SP_010, "Le bareme, s'il est renseigne, doit etre strictement positif.");
        }
        this.bareme = bareme;
        this.estObligatoire = estObligatoire;
        this.creePar = Objects.requireNonNull(creePar, "creePar requis");
        this.modifiePar = creePar;
    }

    public static MatiereActive activerDepuisReferentiel(UUID niveauActiveId, UUID serieActiveId, UUID matiereReferentielId,
                                                           BigDecimal coefficient, Integer bareme, boolean estObligatoire, UUID creePar) {
        return new MatiereActive(niveauActiveId, serieActiveId, matiereReferentielId, null, coefficient, bareme, estObligatoire, creePar);
    }

    public static MatiereActive activerDepuisLocale(UUID niveauActiveId, UUID serieActiveId, UUID matiereLocaleId,
                                                      BigDecimal coefficient, Integer bareme, boolean estObligatoire, UUID creePar) {
        return new MatiereActive(niveauActiveId, serieActiveId, null, matiereLocaleId, coefficient, bareme, estObligatoire, creePar);
    }

    public static MatiereActive reconstituer(UUID id, UUID niveauActiveId, UUID serieActiveId, UUID matiereReferentielId,
                                              UUID matiereLocaleId, BigDecimal coefficient, Integer bareme,
                                              boolean estObligatoire, Instant dateCreation, Instant dateModification,
                                              UUID creePar, UUID modifiePar) {
        MatiereActive m = new MatiereActive(niveauActiveId, serieActiveId, matiereReferentielId, matiereLocaleId,
            coefficient, bareme, estObligatoire, creePar);
        m.id = id;
        m.dateCreation = dateCreation;
        m.dateModification = dateModification;
        m.modifiePar = modifiePar;
        return m;
    }

    public SourceMatiere source() {
        return matiereReferentielId != null ? SourceMatiere.REFERENTIEL : SourceMatiere.LOCALE;
    }

    /**
     * Modification du coefficient effectif. Le VERROUILLAGE selon l'etat de
     * l'annee (SP-022, matrice 4.6) est du ressort de GardeEtatConfigurationService
     * (couche application, jour 6) -- cette methode ne fait respecter que
     * l'invariant de valeur (SP-010), pas la politique de verrouillage
     * temporel, qui n'est pas la responsabilite de cet agregat.
     */
    public void modifierCoefficient(BigDecimal nouveauCoefficient, UUID acteur) {
        exigerCoefficientValide(nouveauCoefficient);
        this.coefficient = nouveauCoefficient;
        toucher(acteur);
    }

    public void modifierBareme(Integer nouveauBareme, UUID acteur) {
        if (nouveauBareme != null && nouveauBareme <= 0) {
            throw new StructureMetierException(CodeErreurStructure.SP_010, "Le bareme, s'il est renseigne, doit etre strictement positif.");
        }
        this.bareme = nouveauBareme;
        toucher(acteur);
    }

    public void modifierObligatoire(boolean obligatoire, UUID acteur) {
        this.estObligatoire = obligatoire;
        toucher(acteur);
    }

    private static void exigerCoefficientValide(BigDecimal coefficient) {
        if (coefficient == null || coefficient.compareTo(BigDecimal.ZERO) <= 0) {
            throw new StructureMetierException(CodeErreurStructure.SP_010, "Le coefficient doit etre strictement positif.");
        }
    }

    private void toucher(UUID acteur) {
        this.modifiePar = acteur;
        this.dateModification = Instant.now();
    }

    public Optional<UUID> getSerieActiveId() { return Optional.ofNullable(serieActiveId); }
    public Optional<UUID> getMatiereReferentielId() { return Optional.ofNullable(matiereReferentielId); }
    public Optional<UUID> getMatiereLocaleId() { return Optional.ofNullable(matiereLocaleId); }

    public UUID getId() { return id; }
    public UUID getNiveauActiveId() { return niveauActiveId; }
    public BigDecimal getCoefficient() { return coefficient; }
    public Integer getBareme() { return bareme; }
    public boolean isEstObligatoire() { return estObligatoire; }
    public Instant getDateCreation() { return dateCreation; }
    public Instant getDateModification() { return dateModification; }
    public UUID getCreePar() { return creePar; }
    public UUID getModifiePar() { return modifiePar; }
}
