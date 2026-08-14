package com.example.daeko.structure_pedagogique.application.service;

import com.example.daeko.structure_pedagogique.application.port.out.ReferentielPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LibelleBrancheGenerator {

    private static final String SEPARATEUR = " — ";

    private final ReferentielPort referentielPort;

    public String genererLibelle(UUID sousSystemeId, UUID ordreEnseignementId, UUID typeEnseignementId) {
        String sousSysteme = referentielPort.chargerSousSysteme(sousSystemeId).getLibelle();
        String ordre = referentielPort.chargerOrdreEnseignement(ordreEnseignementId).getLibelle();
        String type = referentielPort.chargerTypeEnseignement(typeEnseignementId).getLibelle();
        return sousSysteme + SEPARATEUR + ordre + SEPARATEUR + type;
    }
}
