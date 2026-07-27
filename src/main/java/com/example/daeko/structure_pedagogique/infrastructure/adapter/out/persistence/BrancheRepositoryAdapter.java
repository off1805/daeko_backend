package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.persistence;

import com.example.daeko.structure_pedagogique.domain.model.Branche;
import com.example.daeko.structure_pedagogique.domain.port.BrancheRepositoryPort;
import com.example.daeko.structure_pedagogique.infrastructure.entity.BrancheJpaEntity;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.BrancheMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class BrancheRepositoryAdapter implements BrancheRepositoryPort {

    private final SpringBrancheJpaRepository springRepository;
    private final BrancheMapper mapper;

    public BrancheRepositoryAdapter(SpringBrancheJpaRepository springRepository, BrancheMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Branche save(Branche branche) {
        BrancheJpaEntity sauvegardee = springRepository.save(mapper.toJpa(branche));
        return mapper.toDomain(sauvegardee);
    }

    @Override
    public Optional<Branche> findById(UUID id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Branche> findByIdEtEtablissement(UUID id, UUID etablissementId) {
        return springRepository.findByIdAndEtablissementId(id, etablissementId).map(mapper::toDomain);
    }

    @Override
    public List<Branche> findByEtablissement(UUID etablissementId) {
        return springRepository.findByEtablissementId(etablissementId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByTriplet(UUID etablissementId, UUID sousSystemeId, UUID ordreEnseignementId, UUID typeEnseignementId) {
        return springRepository.existsByEtablissementIdAndSousSystemeIdAndOrdreEnseignementIdAndTypeEnseignementId(
            etablissementId, sousSystemeId, ordreEnseignementId, typeEnseignementId);
    }
}

interface SpringBrancheJpaRepository extends JpaRepository<BrancheJpaEntity, UUID> {

    Optional<BrancheJpaEntity> findByIdAndEtablissementId(UUID id, UUID etablissementId);

    List<BrancheJpaEntity> findByEtablissementId(UUID etablissementId);

    boolean existsByEtablissementIdAndSousSystemeIdAndOrdreEnseignementIdAndTypeEnseignementId(
        UUID etablissementId, UUID sousSystemeId, UUID ordreEnseignementId, UUID typeEnseignementId);
}
