package com.example.daeko.structure_pedagogique.infrastructure.entity;

import com.example.daeko.structure_pedagogique.domain.model.EtatElementLocal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "matiere_locale", schema = "structure_pedagogique",
    uniqueConstraints = @UniqueConstraint(name = "uq_matiere_locale_code", columnNames = {"branche_id", "code"}))
public class MatiereLocaleJpaEntity extends AuditableJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "branche_id", nullable = false)
    private UUID brancheId;

    @Column(name = "code", nullable = false, length = 30)
    private String code;

    @Column(name = "libelle", nullable = false, length = 200)
    private String libelle;

    @Column(name = "libelle_court", length = 30)
    private String libelleCourt;

    @Column(name = "libelle_en", length = 200)
    private String libelleEn;

    /** Reutilise les enums referentiel.domaine_matiere / type_matiere -- mappes en String cote infra. */
    @Column(name = "domaine", nullable = false, columnDefinition = "referentiel.domaine_matiere")
    private String domaine;

    @Column(name = "type_matiere", nullable = false, columnDefinition = "referentiel.type_matiere")
    private String typeMatiere;

    @Column(name = "bareme_par_defaut", nullable = false)
    private short baremeParDefaut;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false, columnDefinition = "structure_pedagogique.etat_element_local")
    private EtatElementLocal etat;

    @Column(name = "motif_depreciation")
    private String motifDepreciation;
}
