package com.example.daeko.structure_pedagogique.domain.model;

import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.model.EtatBranche;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Le triplet (sousSystemeId, ordreEnseignementId, typeEnseignementId) est
 * IMMUABLE apres creation (SP-019) : aucune methode ne permet de le modifier.
 * Seul le libelle est editable. Changer la nature d'une branche = en creer
 * une nouvelle et archiver l'ancienne.
 */
public class Branche {

    private UUID id;
    private final UUID etablissementId;
    private final UUID sousSystemeId;
    private final UUID ordreEnseignementId;
    private final UUID typeEnseignementId;
    private String libelle;
    private EtatBranche etat;
    private Instant dateCreation;
    private Instant dateModification;
    private UUID creePar;
    private UUID modifiePar;

    private Branche(UUID etablissementId, UUID sousSystemeId, UUID ordreEnseignementId,
                     UUID typeEnseignementId, String libelle, UUID creePar) {
        this.etablissementId = Objects.requireNonNull(etablissementId, "etablissementId requis");
        this.sousSystemeId = Objects.requireNonNull(sousSystemeId, "sousSystemeId requis");
        this.ordreEnseignementId = Objects.requireNonNull(ordreEnseignementId, "ordreEnseignementId requis");
        this.typeEnseignementId = Objects.requireNonNull(typeEnseignementId, "typeEnseignementId requis");
        this.libelle = Objects.requireNonNull(libelle, "libelle requis");
        this.creePar = Objects.requireNonNull(creePar, "creePar requis");
        this.modifiePar = creePar;
        this.etat = EtatBranche.EN_CONFIGURATION;
    }

    /** Etat initial : EN_CONFIGURATION. */
    public static Branche creer(UUID etablissementId, UUID sousSystemeId, UUID ordreEnseignementId,
                                 UUID typeEnseignementId, String libelle, UUID creePar) {
        return new Branche(etablissementId, sousSystemeId, ordreEnseignementId, typeEnseignementId, libelle, creePar);
    }

    public static Branche reconstituer(UUID id, UUID etablissementId, UUID sousSystemeId, UUID ordreEnseignementId,
                                        UUID typeEnseignementId, String libelle, EtatBranche etat,
                                        Instant dateCreation, Instant dateModification, UUID creePar, UUID modifiePar) {
        Branche b = new Branche(etablissementId, sousSystemeId, ordreEnseignementId, typeEnseignementId, libelle, creePar);
        b.id = id;
        b.etat = etat;
        b.dateCreation = dateCreation;
        b.dateModification = dateModification;
        b.modifiePar = modifiePar;
        return b;
    }

    /** Seul champ modifiable en dehors des transitions d'etat (SP-019 pour le reste). */
    public void modifierLibelle(String nouveauLibelle, UUID acteur) {
        this.libelle = Objects.requireNonNull(nouveauLibelle, "libelle requis");
        this.modifiePar = acteur;
        this.dateModification = Instant.now();
    }

    /** Transition 4.5 : EN_CONFIGURATION -> ACTIVE. */
    public void activer(UUID acteur) {
        exigerTransition(etat == EtatBranche.EN_CONFIGURATION);
        this.etat = EtatBranche.ACTIVE;
        toucher(acteur);
    }

    /** Transition 4.5 : ACTIVE -> SUSPENDUE. */
    public void suspendre(UUID acteur) {
        exigerTransition(etat == EtatBranche.ACTIVE);
        this.etat = EtatBranche.SUSPENDUE;
        toucher(acteur);
    }

    /** Transition 4.5 : SUSPENDUE -> ACTIVE. */
    public void reactiver(UUID acteur) {
        exigerTransition(etat == EtatBranche.SUSPENDUE);
        this.etat = EtatBranche.ACTIVE;
        toucher(acteur);
    }

    /** Transition 4.5 : ACTIVE ou SUSPENDUE -> ARCHIVEE (terminal, motif obligatoire cote use case). */
    public void archiver(UUID acteur) {
        exigerTransition(etat == EtatBranche.ACTIVE || etat == EtatBranche.SUSPENDUE);
        this.etat = EtatBranche.ARCHIVEE;
        toucher(acteur);
    }

    /** Une configuration ne peut etre creee que sur une branche ACTIVE (SP-018). */
    public void exigerActive() {
        if (etat != EtatBranche.ACTIVE) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_018,
                "La branche " + id + " est " + etat + ", impossible de creer une configuration."
            );
        }
    }

    private void exigerTransition(boolean autorisee) {
        if (!autorisee) {
            throw new StructureMetierException(
                CodeErreurStructure.SP_013,
                "Transition d'etat interdite pour la branche " + id + " depuis l'etat " + etat
            );
        }
    }

    private void toucher(UUID acteur) {
        this.modifiePar = acteur;
        this.dateModification = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getEtablissementId() { return etablissementId; }
    public UUID getSousSystemeId() { return sousSystemeId; }
    public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
    public UUID getTypeEnseignementId() { return typeEnseignementId; }
    public String getLibelle() { return libelle; }
    public EtatBranche getEtat() { return etat; }
    public Instant getDateCreation() { return dateCreation; }
    public Instant getDateModification() { return dateModification; }
    public UUID getCreePar() { return creePar; }
    public UUID getModifiePar() { return modifiePar; }
}
