package com.example.daeko.etablissement.application.port.in;

import com.example.daeko.etablissement.domain.model.JournalAuditEtablissement;

import java.util.List;
import java.util.UUID;

public interface JournalAuditEtablissementUseCase {

    List<JournalAuditEtablissement> listerParEtablissement(UUID etablissementId);
}