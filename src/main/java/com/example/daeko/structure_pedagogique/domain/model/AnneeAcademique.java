package com.example.daeko.structure_pedagogique.domain.model;

import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class AnneeAcademique {

    private UUID id;
    private final UUID etablissementId;
    private String libelle;
    private final LocalDate dateDebut;
    private final LocalDate dateFin;
    private EtatAnnee etat;
    private Instant dateDemarrage;
    private Instant dateCloture;
    private Instant dateCreation;
    private Instant dateModification;
    private UUID creePar;
    private UUID modifiePar;

    private AnneeAcademique(UUID etablissementId, String libelle, LocalDate dateDebut, LocalDate dateFin, UUID creePar) {
        this.etablissementId = Objects.requireNonNull(etablissementId, "etablissementId requis");
        this.libelle = Objects.requireNonNull(libelle, "libelle requis");
        this.dateDebut = Objects.requireNonNull(dateDebut, "dateDebut requise");
        this.dateFin = Objects.requireNonNull(dateFin, "dateFin requise");
        if (!dateFin.isAfter(dateDebut)) {
            throw new StructureMetierException(CodeErreurStructure.SP_012, "La date de fin doit etre posterieure a la date de debut.");
        }
        this.creePar = Objects.requireNonNull(creePar, "creePar requis");
        this.modifiePar = creePar;
        this.etat = EtatAnnee.EN_PREPARATION;
    }

    public static AnneeAcademique creer(UUID etablissementId, String libelle, LocalDate dateDebut, LocalDate dateFin, UUID creePar) {
        return new AnneeAcademique(etablissementId, libelle, dateDebut, dateFin, creePar);
    }

    public static AnneeAcademique reconstituer(UUID id, UUID etablissementId, String libelle, LocalDate dateDebut,
                                                LocalDate dateFin, EtatAnnee etat, Instant dateDemarrage, Instant dateCloture,
                                                Instant dateCreation, Instant dateModification, UUID creePar, UUID modifiePar) {
        AnneeAcademique a = new AnneeAcademique(etablissementId, libelle, dateDebut, dateFin, creePar);
        a.id = id;
        a.etat = etat;
        a.dateDemarrage = dateDemarrage;
        a.dateCloture = dateCloture;
        a.dateCreation = dateCreation;
        a.dateModification = dateModification;
        a.modifiePar = modifiePar;
        return a;
    }

    public boolean chevauche(AnneeAcademique autre) {
        return !this.dateFin.isBefore(autre.dateDebut) && !autre.dateFin.isBefore(this.dateDebut);
    }

    public void demarrer(UUID acteur) {
        if (etat != EtatAnnee.EN_PREPARATION) {
            throw new StructureMetierException(CodeErreurStructure.SP_013,
                "L'annee " + id + " est " + etat + ", impossible de la demarrer.");
        }
        this.etat = EtatAnnee.EN_COURS;
        this.dateDemarrage = Instant.now();
        toucher(acteur);
    }

    public void cloturer(UUID acteur) {
        if (etat == EtatAnnee.CLOTUREE) {
            throw new StructureMetierException(CodeErreurStructure.SP_013, "L'annee " + id + " est deja CLOTUREE (terminal).");
        }
        this.etat = EtatAnnee.CLOTUREE;
        this.dateCloture = Instant.now();
        toucher(acteur);
    }

    public boolean estEnCours() { return etat == EtatAnnee.EN_COURS; }
    public boolean estClotures() { return etat == EtatAnnee.CLOTUREE; }
    public boolean estEnPreparation() { return etat == EtatAnnee.EN_PREPARATION; }

    private void toucher(UUID acteur) {
        this.modifiePar = acteur;
        this.dateModification = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getEtablissementId() { return etablissementId; }
    public String getLibelle() { return libelle; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public EtatAnnee getEtat() { return etat; }
    public Instant getDateDemarrage() { return dateDemarrage; }
    public Instant getDateCloture() { return dateCloture; }
    public Instant getDateCreation() { return dateCreation; }
    public Instant getDateModification() { return dateModification; }
    public UUID getCreePar() { return creePar; }
    public UUID getModifiePar() { return modifiePar; }
}
