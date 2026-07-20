package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AuditResponse {
    private UUID id;
    private String typeEntite;
    private UUID entiteId;
    private String operation;
    private UUID utilisateurId;
    private Instant horodatage;
    private String motif;
    private List<ChampModifieResponse> champsModifies;
}