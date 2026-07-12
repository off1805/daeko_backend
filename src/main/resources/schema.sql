-- Contraintes complémentaires que Hibernate ne peut pas générer automatiquement
-- à partir des seules annotations JPA.

-- Unicité partielle sur matiere_referentiel_niveau (§3.6 du dossier d'implémentation)
-- Nécessaire car serie_id est nullable : deux valeurs NULL ne sont jamais égales
-- en SQL, donc une contrainte UNIQUE classique laisserait passer plusieurs lignes
-- sans série pour la même matière/niveau.

CREATE UNIQUE INDEX IF NOT EXISTS uq_mrn_avec_serie
    ON matiere_referentiel_niveau (matiere_referentiel_id, niveau_id, serie_id)
    WHERE serie_id IS NOT NULL;

CREATE UNIQUE INDEX IF NOT EXISTS uq_mrn_sans_serie
    ON matiere_referentiel_niveau (matiere_referentiel_id, niveau_id)
    WHERE serie_id IS NULL;