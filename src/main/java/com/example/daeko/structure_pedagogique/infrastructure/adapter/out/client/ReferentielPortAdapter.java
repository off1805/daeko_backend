package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.client;

import com.example.daeko.structure_pedagogique.domain.exception.RessourceIntrouvableException;
import com.example.daeko.structure_pedagogique.application.port.out.ReferentielPort;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReferentielPortAdapter implements ReferentielPort {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public EntiteReferentielVue chargerSousSysteme(UUID id) {
        return chargerEntiteSimple("referentiel.sous_systeme", id, "sous-systeme");
    }

    @Override
    public EntiteReferentielVue chargerOrdreEnseignement(UUID id) {
        return chargerEntiteSimple("referentiel.ordre_enseignement", id, "ordre d'enseignement");
    }

    @Override
    public EntiteReferentielVue chargerTypeEnseignement(UUID id) {
        return chargerEntiteSimple("referentiel.type_enseignement", id, "type d'enseignement");
    }

    @Override
    public NiveauVue chargerNiveau(UUID id) {
        String sql = "SELECT id, libelle, etat, cycle_id FROM referentiel.niveau WHERE id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                throw new RessourceIntrouvableException("niveau", id);
            }
            return new NiveauVueImpl(rs.getObject("id", UUID.class), rs.getString("libelle"),
                "ACTIVE".equals(rs.getString("etat")), rs.getObject("cycle_id", UUID.class));
        }, id);
    }

    @Override
    public CycleVue chargerCycle(UUID id) {
        String sql = "SELECT id, sous_systeme_id, ordre_enseignement_id FROM referentiel.cycle WHERE id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                throw new RessourceIntrouvableException("cycle", id);
            }
            return new CycleVueImpl(rs.getObject("id", UUID.class), rs.getObject("sous_systeme_id", UUID.class),
                rs.getObject("ordre_enseignement_id", UUID.class));
        }, id);
    }

    @Override
    public FiliereVue chargerFiliere(UUID id) {
        String sql = "SELECT id, libelle, etat, ordre_enseignement_id, type_enseignement_id "
            + "FROM referentiel.filiere WHERE id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                throw new RessourceIntrouvableException("filiere", id);
            }
            return new FiliereVueImpl(rs.getObject("id", UUID.class), rs.getString("libelle"),
                "ACTIVE".equals(rs.getString("etat")), rs.getObject("ordre_enseignement_id", UUID.class),
                rs.getObject("type_enseignement_id", UUID.class));
        }, id);
    }

    @Override
    public SerieVue chargerSerie(UUID id) {
        String sql = "SELECT id, libelle, etat, filiere_id, niveau_apparition_id FROM referentiel.serie WHERE id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                throw new RessourceIntrouvableException("serie", id);
            }
            return new SerieVueImpl(rs.getObject("id", UUID.class), rs.getString("libelle"),
                "ACTIVE".equals(rs.getString("etat")), rs.getObject("filiere_id", UUID.class),
                rs.getObject("niveau_apparition_id", UUID.class));
        }, id);
    }

    @Override
    public MatiereVue chargerMatiere(UUID id) {
        String sql = "SELECT id, libelle, etat, sous_systeme_id FROM referentiel.matiere_referentiel WHERE id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                throw new RessourceIntrouvableException("matiere referentiel", id);
            }
            return new MatiereVueImpl(rs.getObject("id", UUID.class), rs.getString("libelle"),
                "ACTIVE".equals(rs.getString("etat")), rs.getObject("sous_systeme_id", UUID.class));
        }, id);
    }

    @Override
    public boolean estActive(UUID entiteId) {
        // Les entites du referentiel partagent toutes une colonne "etat" avec une valeur ACTIVE / DEPRECATED
        // (cf. dossier Referentiel). Recherche par UNION sur les tables concernees par le module Structure.
        String sql = """
            SELECT etat FROM referentiel.sous_systeme WHERE id = ?
            UNION ALL SELECT etat FROM referentiel.ordre_enseignement WHERE id = ?
            UNION ALL SELECT etat FROM referentiel.type_enseignement WHERE id = ?
            UNION ALL SELECT etat FROM referentiel.niveau WHERE id = ?
            UNION ALL SELECT etat FROM referentiel.filiere WHERE id = ?
            UNION ALL SELECT etat FROM referentiel.serie WHERE id = ?
            UNION ALL SELECT etat FROM referentiel.matiere_referentiel WHERE id = ?
            """;
        java.util.List<String> etats = jdbcTemplate.queryForList(sql, String.class,
            entiteId, entiteId, entiteId, entiteId, entiteId, entiteId, entiteId);
        if (etats.isEmpty()) {
            throw new RessourceIntrouvableException("entite referentiel", entiteId);
        }
        return "ACTIVE".equals(etats.get(0));
    }

    @Override
    public int positionGlobale(UUID niveauId) {
        // Fonction PARTAGEE avec le module Referentiel (section 4.2 du dossier) : reutilise ici la
        // meme colonne "position_globale" que celle exposee par le referentiel plutot que de
        // recalculer une logique d'ordre divergente.
        String sql = "SELECT position_globale FROM referentiel.niveau WHERE id = ?";
        Integer position = jdbcTemplate.queryForObject(sql, Integer.class, niveauId);
        if (position == null) {
            throw new RessourceIntrouvableException("niveau", niveauId);
        }
        return position;
    }

    private EntiteReferentielVue chargerEntiteSimple(String table, UUID id, String libelleType) {
        String sql = "SELECT id, libelle, etat FROM " + table + " WHERE id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                throw new RessourceIntrouvableException(libelleType, id);
            }
            return new EntiteVueImpl(rs.getObject("id", UUID.class), rs.getString("libelle"),
                "ACTIVE".equals(rs.getString("etat")));
        }, id);
    }

    // --- Implementations privees des vues du port (domain/port/out/ReferentielPort.java) ---

    private static final class EntiteVueImpl implements EntiteReferentielVue {
        private final UUID id;
        private final String libelle;
        private final boolean active;

        EntiteVueImpl(UUID id, String libelle, boolean active) {
            this.id = id;
            this.libelle = libelle;
            this.active = active;
        }

        @Override public UUID getId() { return id; }
        @Override public String getLibelle() { return libelle; }
        @Override public boolean isActive() { return active; }
    }

    private static final class NiveauVueImpl implements NiveauVue {
        private final UUID id;
        private final String libelle;
        private final boolean active;
        private final UUID cycleId;

        NiveauVueImpl(UUID id, String libelle, boolean active, UUID cycleId) {
            this.id = id;
            this.libelle = libelle;
            this.active = active;
            this.cycleId = cycleId;
        }

        @Override public UUID getId() { return id; }
        @Override public String getLibelle() { return libelle; }
        @Override public boolean isActive() { return active; }
        @Override public UUID getCycleId() { return cycleId; }
    }

    private static final class CycleVueImpl implements CycleVue {
        private final UUID id;
        private final UUID sousSystemeId;
        private final UUID ordreEnseignementId;

        CycleVueImpl(UUID id, UUID sousSystemeId, UUID ordreEnseignementId) {
            this.id = id;
            this.sousSystemeId = sousSystemeId;
            this.ordreEnseignementId = ordreEnseignementId;
        }

        @Override public UUID getId() { return id; }
        @Override public UUID getSousSystemeId() { return sousSystemeId; }
        @Override public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
    }

    private static final class FiliereVueImpl implements FiliereVue {
        private final UUID id;
        private final String libelle;
        private final boolean active;
        private final UUID ordreEnseignementId;
        private final UUID typeEnseignementId;

        FiliereVueImpl(UUID id, String libelle, boolean active, UUID ordreEnseignementId, UUID typeEnseignementId) {
            this.id = id;
            this.libelle = libelle;
            this.active = active;
            this.ordreEnseignementId = ordreEnseignementId;
            this.typeEnseignementId = typeEnseignementId;
        }

        @Override public UUID getId() { return id; }
        @Override public String getLibelle() { return libelle; }
        @Override public boolean isActive() { return active; }
        @Override public UUID getOrdreEnseignementId() { return ordreEnseignementId; }
        @Override public UUID getTypeEnseignementId() { return typeEnseignementId; }
    }

    private static final class SerieVueImpl implements SerieVue {
        private final UUID id;
        private final String libelle;
        private final boolean active;
        private final UUID filiereId;
        private final UUID niveauApparitionId;

        SerieVueImpl(UUID id, String libelle, boolean active, UUID filiereId, UUID niveauApparitionId) {
            this.id = id;
            this.libelle = libelle;
            this.active = active;
            this.filiereId = filiereId;
            this.niveauApparitionId = niveauApparitionId;
        }

        @Override public UUID getId() { return id; }
        @Override public String getLibelle() { return libelle; }
        @Override public boolean isActive() { return active; }
        @Override public UUID getFiliereId() { return filiereId; }
        @Override public UUID getNiveauApparitionId() { return niveauApparitionId; }
    }

    private static final class MatiereVueImpl implements MatiereVue {
        private final UUID id;
        private final String libelle;
        private final boolean active;
        private final UUID sousSystemeId;

        MatiereVueImpl(UUID id, String libelle, boolean active, UUID sousSystemeId) {
            this.id = id;
            this.libelle = libelle;
            this.active = active;
            this.sousSystemeId = sousSystemeId;
        }

        @Override public UUID getId() { return id; }
        @Override public String getLibelle() { return libelle; }
        @Override public boolean isActive() { return active; }
        @Override public UUID getSousSystemeId() { return sousSystemeId; }
    }
}
