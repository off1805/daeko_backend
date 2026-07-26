package com.example.daeko.etablissement.domain.model;

import com.example.daeko.etablissement.domain.exception.IncompletudeActifException;
import com.example.daeko.etablissement.domain.exception.MotifObligatoireException;
import com.example.daeko.etablissement.domain.exception.TransitionEtatInterditeException;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe abstraite portant les champs et les règles de cycle de vie
 * communs à toutes les entités du module Établissement.
 */
public abstract class EntiteEtablissement {

    private UUID id;
    private StatutEtablissement statut;
    private LocalDate dateDebutEssai;
    private LocalDate dateFinEssai;
    private OffsetDateTime dateActivation;
    private OffsetDateTime dateSuspension;
    private String motifSuspension;
    private OffsetDateTime dateArchivage;
    private String motifArchivage;

    protected EntiteEtablissement() {
        this.statut = StatutEtablissement.EN_CREATION;
    }

    public void passerEnEssai(LocalDate dateDebut, LocalDate dateFin) {
        if (this.statut != StatutEtablissement.EN_CREATION) {
            throw new TransitionEtatInterditeException("Passage en essai autorisé uniquement depuis l'état EN_CREATION");
        }
        if (dateDebut == null || dateFin == null || dateFin.isBefore(dateDebut)) {
            throw new TransitionEtatInterditeException("Les dates d'essai sont invalides");
        }
        this.statut = StatutEtablissement.EN_ESSAI;
        this.dateDebutEssai = dateDebut;
        this.dateFinEssai = dateFin;
    }

    public void activer(boolean aLogo, boolean aSignatairePrincipal, boolean aEnteteValide, StatutJuridique statutJuridique) {
        if (this.statut != StatutEtablissement.EN_CREATION && this.statut != StatutEtablissement.EN_ESSAI) {
            throw new TransitionEtatInterditeException("L'activation est autorisée uniquement depuis EN_CREATION ou EN_ESSAI");
        }
        if (!aLogo || !aSignatairePrincipal || !aEnteteValide || statutJuridique == null) {
            throw new IncompletudeActifException("L'établissement est incomplet pour passer à l'état ACTIF");
        }
        this.statut = StatutEtablissement.ACTIF;
        this.dateActivation = OffsetDateTime.now();
    }

    public void suspendre(String motif) {
        if (this.statut != StatutEtablissement.ACTIF && this.statut != StatutEtablissement.EN_ESSAI) {
            throw new TransitionEtatInterditeException("La suspension est autorisée uniquement sur un établissement ACTIF ou EN_ESSAI");
        }
        if (motif == null || motif.isBlank()) {
            throw new MotifObligatoireException("Un motif est obligatoire pour la suspension");
        }
        this.statut = StatutEtablissement.SUSPENDU;
        this.dateSuspension = OffsetDateTime.now();
        this.motifSuspension = motif;
    }

    public void reactiver() {
        if (this.statut != StatutEtablissement.SUSPENDU) {
            throw new TransitionEtatInterditeException("La réactivation est autorisée uniquement depuis l'état SUSPENDU");
        }
        this.statut = StatutEtablissement.ACTIF;
    }

    public void archiver(String motif) {
        if (this.statut == StatutEtablissement.ARCHIVE) {
            throw new TransitionEtatInterditeException("L'établissement est déjà archivé");
        }
        if (motif == null || motif.isBlank()) {
            throw new MotifObligatoireException("Un motif est obligatoire pour l'archivage");
        }
        this.statut = StatutEtablissement.ARCHIVE;
        this.dateArchivage = OffsetDateTime.now();
        this.motifArchivage = motif;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public StatutEtablissement getStatut() { return statut; }
    public void setStatut(StatutEtablissement statut) { this.statut = statut; }

    public LocalDate getDateDebutEssai() { return dateDebutEssai; }
    public LocalDate getDateFinEssai() { return dateFinEssai; }
    public OffsetDateTime getDateActivation() { return dateActivation; }
    public OffsetDateTime getDateSuspension() { return dateSuspension; }
    public String getMotifSuspension() { return motifSuspension; }
    public OffsetDateTime getDateArchivage() { return dateArchivage; }
    public String getMotifArchivage() { return motifArchivage; }
}