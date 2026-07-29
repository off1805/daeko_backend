package com.example.daeko.structure_pedagogique.application.service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public final class DifferentielCalculator {

    private DifferentielCalculator() {
    }

    public static Resultat calculer(Set<UUID> actuels, Set<UUID> cibles) {
        Set<UUID> aAjouter = new LinkedHashSet<>(cibles);
        aAjouter.removeAll(actuels);

        Set<UUID> aRetirer = new LinkedHashSet<>(actuels);
        aRetirer.removeAll(cibles);

        Set<UUID> inchanges = new LinkedHashSet<>(actuels);
        inchanges.retainAll(cibles);

        return new Resultat(aAjouter, aRetirer, inchanges);
    }

    public static final class Resultat {
        private final Set<UUID> aAjouter;
        private final Set<UUID> aRetirer;
        private final Set<UUID> inchanges;

        private Resultat(Set<UUID> aAjouter, Set<UUID> aRetirer, Set<UUID> inchanges) {
            this.aAjouter = aAjouter;
            this.aRetirer = aRetirer;
            this.inchanges = inchanges;
        }

        public Set<UUID> getAAjouter() { return aAjouter; }
        public Set<UUID> getARetirer() { return aRetirer; }
        public Set<UUID> getInchanges() { return inchanges; }
    }
}
