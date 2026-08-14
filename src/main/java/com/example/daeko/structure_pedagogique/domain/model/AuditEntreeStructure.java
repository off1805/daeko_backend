package com.example.daeko.structure_pedagogique.domain.model;


import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class AuditEntreeStructure {

    private final UUID id;
    private final UUID etablissementId;
    private final OperationAuditSp operation;
    private final String cibleType;
    private final UUID cibleId;
    private final UUID utilisateurId;
    private final Map<String, Object> valeursAvant; // nullable
    private final Map<String, Object> valeursApres;  // nullable
    private final String motif;                      // nullable
    private final Instant horodatage;

    private AuditEntreeStructure(UUID id, UUID etablissementId, OperationAuditSp operation, String cibleType,
                                  UUID cibleId, UUID utilisateurId, Map<String, Object> valeursAvant,
                                  Map<String, Object> valeursApres, String motif, Instant horodatage) {
        this.id = id;
        this.etablissementId = Objects.requireNonNull(etablissementId, "etablissementId requis");
        this.operation = Objects.requireNonNull(operation, "operation requise");
        this.cibleType = Objects.requireNonNull(cibleType, "cibleType requis");
        this.cibleId = Objects.requireNonNull(cibleId, "cibleId requis");
        this.utilisateurId = Objects.requireNonNull(utilisateurId, "utilisateurId requis");
        this.valeursAvant = valeursAvant;
        this.valeursApres = valeursApres;
        this.motif = motif;
        this.horodatage = horodatage != null ? horodatage : Instant.now();
    }

    public static AuditEntreeStructure nouvelle(UUID etablissementId, OperationAuditSp operation, String cibleType,
                                                 UUID cibleId, UUID utilisateurId, Map<String, Object> valeursAvant,
                                                 Map<String, Object> valeursApres, String motif) {
        return new AuditEntreeStructure(null, etablissementId, operation, cibleType, cibleId, utilisateurId,
            valeursAvant, valeursApres, motif, null);
    }

    public static AuditEntreeStructure reconstituer(UUID id, UUID etablissementId, OperationAuditSp operation,
                                                      String cibleType, UUID cibleId, UUID utilisateurId,
                                                      Map<String, Object> valeursAvant, Map<String, Object> valeursApres,
                                                      String motif, Instant horodatage) {
        return new AuditEntreeStructure(id, etablissementId, operation, cibleType, cibleId, utilisateurId,
            valeursAvant, valeursApres, motif, horodatage);
    }

    public UUID getId() { return id; }
    public UUID getEtablissementId() { return etablissementId; }
    public OperationAuditSp getOperation() { return operation; }
    public String getCibleType() { return cibleType; }
    public UUID getCibleId() { return cibleId; }
    public UUID getUtilisateurId() { return utilisateurId; }
    public Optional<Map<String, Object>> getValeursAvant() { return Optional.ofNullable(valeursAvant); }
    public Optional<Map<String, Object>> getValeursApres() { return Optional.ofNullable(valeursApres); }
    public Optional<String> getMotif() { return Optional.ofNullable(motif); }
    public Instant getHorodatage() { return horodatage; }
}
