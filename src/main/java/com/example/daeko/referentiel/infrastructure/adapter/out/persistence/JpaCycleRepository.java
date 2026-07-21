package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.port.CycleRepository;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.CycleJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.CycleMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaCycleRepository implements CycleRepository {

    private final SpringDataCycleRepository springRepo;
    private final CycleMapper mapper;

    public JpaCycleRepository(SpringDataCycleRepository springRepo, CycleMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Cycle sauvegarder(Cycle cycle) {
        CycleJpaEntity entity = mapper.toEntity(cycle);
        return mapper.toDomain(springRepo.save(entity));
    }

    @Override
    public Optional<Cycle> trouverParId(UUID id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Cycle> rechercher(EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findByEtat(etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cycle> rechercher(UUID sousSystemeId, UUID ordreEnseignementId, EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findBySousSystemeIdAndOrdreEnseignementIdAndEtat(sousSystemeId, ordreEnseignementId, etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeParCode(String code) {
        return springRepo.existsBySousSystemeIdAndOrdreEnseignementIdAndCode(sousSystemeId, ordreEnseignementId, code);
    }
}

interface SpringDataCycleRepository extends JpaRepository<CycleJpaEntity, UUID> {
    List<CycleJpaEntity> findByEtat(EtatReferentielJpa etat);
    List<CycleJpaEntity> findBySousSystemeIdAndOrdreEnseignementIdAndEtat(UUID sousSystemeId, UUID ordreEnseignementId, EtatReferentielJpa etat);
    boolean existsBySousSystemeIdAndOrdreEnseignementIdAndCode(UUID sousSystemeId, UUID ordreEnseignementId, String code);
}
