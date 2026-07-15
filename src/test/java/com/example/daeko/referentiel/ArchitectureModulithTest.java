package com.example.daeko.referentiel;

import com.example.daeko.DaekoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ArchitectureModulithTest {

    private static final ApplicationModules modules =
            ApplicationModules.of(DaekoApplication.class);

    @Test
    void modulesSontStructurellementValides() {
        modules.verify();
    }

    @Test
    void afficherDescriptionDesModules() {
        modules.forEach(System.out::println);
    }
}
