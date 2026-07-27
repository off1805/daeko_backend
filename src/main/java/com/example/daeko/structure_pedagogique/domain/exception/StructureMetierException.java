package com.example.daeko.structure_pedagogique.domain.exception;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Exception metier du module Structure Pedagogique,
 * parametree par CodeErreurStructure (SP-001 a SP-024)
 * { "code": "SP-XXX", "message": "...", "details": {...} }
 */
public class StructureMetierException extends RuntimeException {

    private final CodeErreurStructure code;
    private final Map<String, Object> details;

    public StructureMetierException(CodeErreurStructure code) {
        this(code, code.getDescriptionDefaut(), Collections.emptyMap());
    }

    public StructureMetierException(CodeErreurStructure code, String message) {
        this(code, message, Collections.emptyMap());
    }

    public StructureMetierException(CodeErreurStructure code, Map<String, Object> details) {
        this(code, code.getDescriptionDefaut(), details);
    }

    public StructureMetierException(CodeErreurStructure code, String message, Map<String, Object> details) {
        super(message);
        this.code = code;
        this.details = Collections.unmodifiableMap(new LinkedHashMap<>(details));
    }

    public CodeErreurStructure getCode() {
        return code;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    /** Petit constructeur utilitaire pour un unique couple cle/valeur de detail. */
    public static StructureMetierException avecDetail(CodeErreurStructure code, String message, String cle, Object valeur) {
        Map<String, Object> d = new LinkedHashMap<>();
        d.put(cle, valeur);
        return new StructureMetierException(code, message, d);
    }
}
