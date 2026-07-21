package com.example.daeko.referentiel.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Cache de lecture, un cache nommé par ressource (6.2 du dossier). TTL de sécurité
 * de 24h en filet, si une invalidation venait à échouer silencieusement.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CACHE_SOUS_SYSTEMES = "sousSystemes";
    public static final String CACHE_MATIERES_NIVEAUX = "matieresNiveaux";
    public static final String CACHE_NIVEAUX = "niveaux";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                CACHE_SOUS_SYSTEMES, CACHE_MATIERES_NIVEAUX, CACHE_NIVEAUX);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofHours(24))
                .maximumSize(500));
        return cacheManager;
    }
}