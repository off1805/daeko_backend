package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.persistence;

import com.example.daeko.structure_pedagogique.domain.model.MatiereActive;
import com.example.daeko.structure_pedagogique.domain.port.MatiereActiveRepositoryPort;
import com.example.daeko.structure_pedagogique.infrastructure.entity.MatiereActiveJpaEntity;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.MatiereActiveMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MatiereActiveRepositoryAdapter implements MatiereActiveRepositoryPort {

    private final SpringMatiereActiveJpaRepository springRepository;
    private final MatiereActiveMapper mapper;

    public MatiereActiveRepositoryAdapter(SpringMatiereActiveJpaRepository springRepository, MatiereActiveMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public MatiereActive save(MatiereActive matiereActive) {
        return mapper.toDomain(springRepository.save(mapper.toJpa(matiereActive)));
    }

    @Override
    public Optional<MatiereActive> findById(UUID id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MatiereActive> findByNiveauActive(UUID niveauActiveId) {
        return springRepository.findByNiveauActiveId(niveauActiveId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existeDoublon(UUID niveauActiveId, UUID serieActiveId, UUID matiereReferentielId, UUID matiereLocaleId) {
        // Traduit directement les 4 index uniques partiels de la migration V19 (SP-011).
        if (matiereReferentielId != null) {
            return serieActiveId != null
                ? springRepository.existsByNiveauActiveIdAndSerieActiveIdAndMatiereReferentielId(niveauActiveId, serieActiveId, matiereReferentielId)
                : springRepository.existsByNiveauActiveIdAndMatiereReferentielIdAndSerieActiveIdIsNull(niveauActiveId, matiereReferentielId);
        }
        return serieActiveId != null
            ? springRepository.existsByNiveauActiveIdAndSerieActiveIdAndMatiereLocaleId(niveauActiveId, serieActiveId, matiereLocaleId)
            : springRepository.existsByNiveauActiveIdAndMatiereLocaleIdAndSerieActiveIdIsNull(niveauActiveId, matiereLocaleId);
    }

    @Override
    public void supprimer(UUID id) {
        springRepository.deleteById(id);
    }
}

interface SpringMatiereActiveJpaRepository extends JpaRepository<MatiereActiveJpaEntity, UUID> {

    List<MatiereActiveJpaEntity> findByNiveauActiveId(UUID niveauActiveId);

    boolean existsByNiveauActiveIdAndSerieActiveIdAndMatiereReferentielId(
        UUID niveauActiveId, UUID serieActiveId, UUID matiereReferentielId);

    boolean existsByNiveauActiveIdAndMatiereReferentielIdAndSerieActiveIdIsNull(
        UUID niveauActiveId, UUID matiereReferentielId);

    boolean existsByNiveauActiveIdAndSerieActiveIdAndMatiereLocaleId(
        UUID niveauActiveId, UUID serieActiveId, UUID matiereLocaleId);

    boolean existsByNiveauActiveIdAndMatiereLocaleIdAndSerieActiveIdIsNull(
        UUID niveauActiveId, UUID matiereLocaleId);
}
