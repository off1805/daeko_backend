package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request;

import java.util.Set;
import java.util.UUID;

public class DefinirNiveauxActifsRequest {

    private Set<UUID> niveauIds;

    public Set<UUID> getNiveauIds() { return niveauIds; }
    public void setNiveauIds(Set<UUID> niveauIds) { this.niveauIds = niveauIds; }
}
