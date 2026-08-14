package com.example.daeko.structure_pedagogique.domain.model;

import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.model.EtatConfiguration;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * OUVERTE tant que l'annee n'est pas cloturee ; SCELLEE de facon irreversible
 * a la cloture (toute ecriture ulterieure -> SP-014).
 */
public class ConfigurationBrancheAnnee {

    private UUID id;
    private final UUID brancheId;
    private final UUID anneeAcademiqueId;
    private EtatConfiguration etat;
    private final UUID dupliqueeDepuisId; // nullable
    private Instant dateScellement;
    private Instant dateCreation;
    private Instant dateModification;
    private UUID creePar;
    private UUID modifiePar;

    private ConfigurationBrancheAnnee(UUID brancheId, UUID anneeAcademiqueId, UUID dupliqueeDepuisId, UUID creePar) {
        this.brancheId = Objects.requireNonNull(brancheId, "brancheId requis");
        this.anneeAcademiqueId = Objects.requireNonNull(anneeAcademiqueId, "anneeAcademiqueId requis");
        this.dupliqueeDepuisId = dupliqueeDepuisId;
        this.creePar = Objects.requireNonNull(creePar, "creePar requis");
        this.modifiePar = creePar;
        this.etat = EtatConfiguration.OUVERTE;
    }

    public static ConfigurationBrancheAnnee creer(UUID brancheId, UUID anneeAcademiqueId, UUID creePar) {
        return new ConfigurationBrancheAnnee(brancheId, anneeAcademiqueId, null, creePar);
    }

    public static ConfigurationBrancheAnnee creerParDuplication(UUID brancheId, UUID anneeAcademiqueId,
                                                                  UUID sourceId, UUID creePar) {
        return new ConfigurationBrancheAnnee(brancheId, anneeAcademiqueId, sourceId, creePar);
    }

    public static ConfigurationBrancheAnnee reconstituer(UUID id, UUID brancheId, UUID anneeAcademiqueId,
                                                           EtatConfiguration etat, UUID dupliqueeDepuisId,
                                                           Instant dateScellement, Instant dateCreation,
                                                           Instant dateModification, UUID creePar, UUID modifiePar) {
        ConfigurationBrancheAnnee c = new ConfigurationBrancheAnnee(brancheId, anneeAcademiqueId, dupliqueeDepuisId, creePar);
        c.id = id;
        c.etat = etat;
        c.dateScellement = dateScellement;
        c.dateCreation = dateCreation;
        c.dateModification = dateModification;
        c.modifiePar = modifiePar;
        return c;
    }

    /** Verification en tete de tout use case d'ecriture touchant cette configuration (SP-014). */
    public void exigerOuverte() {
        if (etat != EtatConfiguration.OUVERTE) {
            throw new StructureMetierException(CodeErreurStructure.SP_014,
                "La configuration " + id + " est SCELLEE : aucune ecriture n'est autorisee.");
        }
    }

    public void sceller(UUID acteur) {
        if (etat == EtatConfiguration.SCELLEE) {
            return;
        }
        this.etat = EtatConfiguration.SCELLEE;
        this.dateScellement = Instant.now();
        this.modifiePar = acteur;
        this.dateModification = Instant.now();
    }

    public boolean estOuverte() { return etat == EtatConfiguration.OUVERTE; }

    public Optional<UUID> getDupliqueeDepuisId() { return Optional.ofNullable(dupliqueeDepuisId); }

    public UUID getId() { return id; }
    public UUID getBrancheId() { return brancheId; }
    public UUID getAnneeAcademiqueId() { return anneeAcademiqueId; }
    public EtatConfiguration getEtat() { return etat; }
    public Instant getDateScellement() { return dateScellement; }
    public Instant getDateCreation() { return dateCreation; }
    public Instant getDateModification() { return dateModification; }
    public UUID getCreePar() { return creePar; }
    public UUID getModifiePar() { return modifiePar; }
}
