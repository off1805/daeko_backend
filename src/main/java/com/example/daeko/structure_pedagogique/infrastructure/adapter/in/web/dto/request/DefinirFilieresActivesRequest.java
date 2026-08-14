package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request;

import java.util.Set;
import java.util.UUID;

public class DefinirFilieresActivesRequest {

    private Set<UUID> filiereIds;

    public Set<UUID> getFiliereIds() { return filiereIds; }
    public void setFiliereIds(Set<UUID> filiereIds) { this.filiereIds = filiereIds; }
}
