package com.example.daeko.referentiel.domain.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Classe abstraite portant les champs de cycle de vie communs à toutes les entités
 * du référentiel éducatif, ainsi que la logique de dépréciation.
 */
public abstract class EntiteReferentiel {

    private UUID id;
    private EtatReferentiel etat;
    private LocalDate dateEntreeVigueur;
    private LocalDate dateDepreciation;
    private String motifDepreciation;
    private UUID creePar;
    private UUID modifiePar;

    protected EntiteReferentiel() {
        this.etat = EtatReferentiel.ACTIVE;
        this.dateEntreeVigueur = LocalDate.now();
    }

    /**
     * Déprécie l'entité en appliquant les règles du cycle de vie.
     * Lève une exception si l'entité est déjà dépréciée ou si les paramètres sont invalides.
     */
    public void deprecier(String motif, LocalDate dateEffet) {
        if (this.etat == EtatReferentiel.DEPRECATED) {
            throw new IllegalStateException("L'entité est déjà dépréciée.");
        }
        if (motif == null || motif.isBlank()) {
            throw new IllegalArgumentException("Le motif de dépréciation est obligatoire.");
        }
        if (dateEffet == null) {
            throw new IllegalArgumentException("La date d'effet est obligatoire.");
        }
        this.etat = EtatReferentiel.DEPRECATED;
        this.dateDepreciation = dateEffet;
        this.motifDepreciation = motif;
    }

    /**
     * Réactive une entité précédemment dépréciée.
     * Lève une exception si l'entité est déjà active.
     */
    public void reactiver() {
        if (this.etat == EtatReferentiel.ACTIVE) {
            throw new IllegalStateException("L'entité est déjà active.");
        }
        this.etat = EtatReferentiel.ACTIVE;
        this.dateDepreciation = null;
        this.motifDepreciation = null;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public EtatReferentiel getEtat() { return etat; }

    public LocalDate getDateEntreeVigueur() { return dateEntreeVigueur; }
    public void setDateEntreeVigueur(LocalDate dateEntreeVigueur) { this.dateEntreeVigueur = dateEntreeVigueur; }

    public LocalDate getDateDepreciation() { return dateDepreciation; }
    public String getMotifDepreciation() { return motifDepreciation; }

    public UUID getCreePar() { return creePar; }
    public void setCreePar(UUID creePar) { this.creePar = creePar; }

    public UUID getModifiePar() { return modifiePar; }
    public void setModifiePar(UUID modifiePar) { this.modifiePar = modifiePar; }
}