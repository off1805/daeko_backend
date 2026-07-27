package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.client;

import com.example.daeko.structure_pedagogique.application.port.out.EtablissementPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EtablissementPortAdapter implements EtablissementPort {

    private static final String SQL = "SELECT etat FROM etablissement.etablissement WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public EtablissementPortAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean estActif(UUID etablissementId) {
        String etat = jdbcTemplate.queryForObject(SQL, String.class, etablissementId);
        // Les etats SUSPENDU et ARCHIVE bloquent toute ecriture (SP-021) ; tout le reste est considere actif.
        return etat != null && !"SUSPENDU".equals(etat) && !"ARCHIVE".equals(etat);
    }
}
