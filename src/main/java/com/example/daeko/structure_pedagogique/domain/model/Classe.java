package com.example.daeko.structure_pedagogique.domain.model;

import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Le rattachement (niveauActiveId, serieActiveId) est IMMUABLE une fois la
 * classe creee (SP-019) : une classe mal rattachee se desactive et se
 * recree, elle ne se corrige jamais en place.
 */
public class Classe {

    private UUID id;
    private final UUID configurationId;
    private final UUID niveauActiveId;
    private final UUID serieActiveId; // nullable
    private String suffixe;
    private String libelleComplet;
    private Integer effectifPrevu;
    private String salle;
    private UUID enseignantPrincipalId; // reference logique, module Personnes
    private boolean actif;
    private Instant dateCreation;
    private Instant dateModification;
    private UUID creePar;
    private UUID modifiePar;

    private Classe(UUID configurationId, UUID niveauActiveId, UUID serieActiveId, String suffixe,
                    String libelleComplet, UUID creePar) {
        this.configurationId = Objects.requireNonNull(configurationId, "configurationId requis");
        this.niveauActiveId = Objects.requireNonNull(niveauActiveId, "niveauActiveId requis");
        this.serieActiveId = serieActiveId;
        this.suffixe = Objects.requireNonNull(suffixe, "suffixe requis");
        this.libelleComplet = Objects.requireNonNull(libelleComplet, "libelleComplet requis");
        this.creePar = Objects.requireNonNull(creePar, "creePar requis");
        this.modifiePar = creePar;
        this.actif = true;
    }

    public static Classe creer(UUID configurationId, UUID niveauActiveId, UUID serieActiveId, String suffixe,
                                String libelleComplet, Integer effectifPrevu, String salle, UUID creePar) {
        Classe c = new Classe(configurationId, niveauActiveId, serieActiveId, suffixe, libelleComplet, creePar);
        c.effectifPrevu = effectifPrevu;
        c.salle = salle;
        return c;
    }

    public static Classe reconstituer(UUID id, UUID configurationId, UUID niveauActiveId, UUID serieActiveId,
                                       String suffixe, String libelleComplet, Integer effectifPrevu, String salle,
                                       UUID enseignantPrincipalId, boolean actif, Instant dateCreation,
                                       Instant dateModification, UUID creePar, UUID modifiePar) {
        Classe c = new Classe(configurationId, niveauActiveId, serieActiveId, suffixe, libelleComplet, creePar);
        c.id = id;
        c.effectifPrevu = effectifPrevu;
        c.salle = salle;
        c.enseignantPrincipalId = enseignantPrincipalId;
        c.actif = actif;
        c.dateCreation = dateCreation;
        c.dateModification = dateModification;
        c.modifiePar = modifiePar;
        return c;
    }

    public void modifierAttributs(String suffixe, String libelleComplet, Integer effectifPrevu, String salle,
                                   UUID enseignantPrincipalId, UUID acteur) {
        if (suffixe != null) this.suffixe = suffixe;
        if (libelleComplet != null) this.libelleComplet = libelleComplet;
        this.effectifPrevu = effectifPrevu;
        this.salle = salle;
        this.enseignantPrincipalId = enseignantPrincipalId;
        toucher(acteur);
    }

    /** Motif obligatoire; en EN_COURS, verification synchrone aupres du module Personnes en amont. */
    public void desactiver(UUID acteur) {
        if (!actif) {
            throw new StructureMetierException(CodeErreurStructure.SP_013, "La classe " + id + " est deja inactive.");
        }
        this.actif = false;
        toucher(acteur);
    }

    /** Reactivation possible uniquement si l'annee associee n'est pas cloturee */
    public void reactiver(UUID acteur) {
        if (actif) {
            return; // idempotent
        }
        this.actif = true;
        toucher(acteur);
    }

    private void toucher(UUID acteur) {
        this.modifiePar = acteur;
        this.dateModification = Instant.now();
    }

    public Optional<UUID> getSerieActiveId() { return Optional.ofNullable(serieActiveId); }
    public Optional<UUID> getEnseignantPrincipalId() { return Optional.ofNullable(enseignantPrincipalId); }

    public UUID getId() { return id; }
    public UUID getConfigurationId() { return configurationId; }
    public UUID getNiveauActiveId() { return niveauActiveId; }
    public String getSuffixe() { return suffixe; }
    public String getLibelleComplet() { return libelleComplet; }
    public Integer getEffectifPrevu() { return effectifPrevu; }
    public String getSalle() { return salle; }
    public boolean isActif() { return actif; }
    public Instant getDateCreation() { return dateCreation; }
    public Instant getDateModification() { return dateModification; }
    public UUID getCreePar() { return creePar; }
    public UUID getModifiePar() { return modifiePar; }
}
