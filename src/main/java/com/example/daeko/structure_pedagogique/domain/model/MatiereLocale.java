package com.example.daeko.structure_pedagogique.domain.model;

import com.example.daeko.structure_pedagogique.domain.exception.CodeErreurStructure;
import com.example.daeko.structure_pedagogique.domain.exception.StructureMetierException;
import com.example.daeko.structure_pedagogique.domain.model.EtatElementLocal;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class MatiereLocale {

    private UUID id;
    private final UUID brancheId;
    private String code;
    private String libelle;
    private String libelleCourt;
    private String libelleEn;
    private String domaine;
    private String typeMatiere;
    private int baremeParDefaut;
    private EtatElementLocal etat;
    private String motifDepreciation;
    private Instant dateCreation;
    private Instant dateModification;
    private UUID creePar;
    private UUID modifiePar;

    private MatiereLocale(UUID brancheId, String code, String libelle, String libelleCourt, String libelleEn,
                           String domaine, String typeMatiere, int baremeParDefaut, UUID creePar) {
        this.brancheId = Objects.requireNonNull(brancheId, "brancheId requis");
        this.code = Objects.requireNonNull(code, "code requis");
        this.libelle = Objects.requireNonNull(libelle, "libelle requis");
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        this.domaine = Objects.requireNonNull(domaine, "domaine requis");
        this.typeMatiere = Objects.requireNonNull(typeMatiere, "typeMatiere requis");
        if (baremeParDefaut <= 0) {
            throw new StructureMetierException(CodeErreurStructure.SP_010, "Le bareme par defaut doit etre strictement positif.");
        }
        this.baremeParDefaut = baremeParDefaut;
        this.creePar = Objects.requireNonNull(creePar, "creePar requis");
        this.modifiePar = creePar;
        this.etat = EtatElementLocal.ACTIVE;
    }

    public static MatiereLocale creer(UUID brancheId, String code, String libelle, String libelleCourt,
                                       String libelleEn, String domaine, String typeMatiere,
                                       Integer baremeParDefaut, UUID creePar) {
        return new MatiereLocale(brancheId, code, libelle, libelleCourt, libelleEn, domaine, typeMatiere,
            baremeParDefaut == null ? 20 : baremeParDefaut, creePar);
    }

    public static MatiereLocale reconstituer(UUID id, UUID brancheId, String code, String libelle, String libelleCourt,
                                              String libelleEn, String domaine, String typeMatiere, int baremeParDefaut,
                                              EtatElementLocal etat, String motifDepreciation, Instant dateCreation,
                                              Instant dateModification, UUID creePar, UUID modifiePar) {
        MatiereLocale m = new MatiereLocale(brancheId, code, libelle, libelleCourt, libelleEn, domaine, typeMatiere,
            baremeParDefaut, creePar);
        m.id = id;
        m.etat = etat;
        m.motifDepreciation = motifDepreciation;
        m.dateCreation = dateCreation;
        m.dateModification = dateModification;
        m.modifiePar = modifiePar;
        return m;
    }

    public void modifierAttributs(String nouveauCode, String libelle, String libelleCourt, String libelleEn,
                                   String domaine, String typeMatiere, Integer baremeParDefaut, UUID acteur) {
        if (nouveauCode != null) this.code = nouveauCode;
        if (libelle != null) this.libelle = libelle;
        this.libelleCourt = libelleCourt;
        this.libelleEn = libelleEn;
        if (domaine != null) this.domaine = domaine;
        if (typeMatiere != null) this.typeMatiere = typeMatiere;
        if (baremeParDefaut != null) this.baremeParDefaut = baremeParDefaut;
        toucher(acteur);
    }

    public void deprecier(String motif, UUID acteur) {
        if (motif == null || motif.isBlank()) {
            throw new StructureMetierException(CodeErreurStructure.SP_010, "Le motif de depreciation est obligatoire.");
        }
        if (this.etat == EtatElementLocal.DEPRECATED) {
            // idempotent : deja depreciee, on ne re-notifie pas une seconde fois
            return;
        }
        this.etat = EtatElementLocal.DEPRECATED;
        this.motifDepreciation = motif;
        toucher(acteur);
    }

    public void exigerActive() {
        if (etat == EtatElementLocal.DEPRECATED) {
            throw new StructureMetierException(CodeErreurStructure.SP_020,
                "La matiere locale " + id + " est depreciee et ne peut plus etre proposee dans une nouvelle activation.");
        }
    }

    private void toucher(UUID acteur) {
        this.modifiePar = acteur;
        this.dateModification = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getBrancheId() { return brancheId; }
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public String getLibelleCourt() { return libelleCourt; }
    public String getLibelleEn() { return libelleEn; }
    public String getDomaine() { return domaine; }
    public String getTypeMatiere() { return typeMatiere; }
    public int getBaremeParDefaut() { return baremeParDefaut; }
    public EtatElementLocal getEtat() { return etat; }
    public String getMotifDepreciation() { return motifDepreciation; }
    public Instant getDateCreation() { return dateCreation; }
    public Instant getDateModification() { return dateModification; }
    public UUID getCreePar() { return creePar; }
    public UUID getModifiePar() { return modifiePar; }
}
