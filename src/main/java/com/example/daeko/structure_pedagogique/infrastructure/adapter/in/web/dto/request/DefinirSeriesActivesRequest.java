package com.example.daeko.structure_pedagogique.infrastructure.adapter.in.web.dto.request;

import java.util.Set;
import java.util.UUID;

public class DefinirSeriesActivesRequest {

    private Set<UUID> serieIds;

    public Set<UUID> getSerieIds() { return serieIds; }
    public void setSerieIds(Set<UUID> serieIds) { this.serieIds = serieIds; }
}
