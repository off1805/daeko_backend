package com.example.daeko.structure_pedagogique.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class EtablissementContextFilter extends OncePerRequestFilter {

    public static final String EN_TETE_ETABLISSEMENT = "X-Etablissement-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String valeur = request.getHeader(EN_TETE_ETABLISSEMENT);
        try {
            if (valeur != null && !valeur.isBlank()) {
                EtablissementContextHolder.definir(UUID.fromString(valeur));
            }
            chain.doFilter(request, response);
        } finally {
            EtablissementContextHolder.effacer();
        }
    }
}
