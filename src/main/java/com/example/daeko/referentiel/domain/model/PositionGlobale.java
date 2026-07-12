package com.example.daeko.referentiel.domain.model;

import java.util.Objects;

/**
 * Value object immuable représentant la position globale d'un niveau
 * dans le référentiel (rang du cycle + rang dans le cycle).
 */
public final class PositionGlobale {

    private final int rangCycle;
    private final int rangDansCycle;

    public PositionGlobale(int rangCycle, int rangDansCycle) {
        this.rangCycle = rangCycle;
        this.rangDansCycle = rangDansCycle;
    }

    public boolean estEgalOuPosterieurA(PositionGlobale autre) {
        if (this.rangCycle != autre.rangCycle) {
            return this.rangCycle >= autre.rangCycle;
        }
        return this.rangDansCycle >= autre.rangDansCycle;
    }

    public int getRangCycle() { return rangCycle; }
    public int getRangDansCycle() { return rangDansCycle; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionGlobale that)) return false;
        return rangCycle == that.rangCycle && rangDansCycle == that.rangDansCycle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rangCycle, rangDansCycle);
    }
}
