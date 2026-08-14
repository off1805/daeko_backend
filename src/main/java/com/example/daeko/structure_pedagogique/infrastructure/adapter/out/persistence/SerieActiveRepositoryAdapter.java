package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.persistence;

import com.example.daeko.structure_pedagogique.domain.model.SerieActive;
import com.example.daeko.structure_pedagogique.domain.port.SerieActiveRepositoryPort;
import com.example.daeko.structure_pedagogique.infrastructure.entity.SerieActiveJpaEntity;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.SerieActiveMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SerieActiveRepositoryAdapter implements SerieActiveRepositoryPort {

    private final SpringSerieActiveJpaRepository springRepository;
    private final SerieActiveMapper mapper;

    public SerieActiveRepositoryAdapter(SpringSerieActiveJpaRepository springRepository, SerieActiveMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public SerieActive save(SerieActive serieActive) {
        return mapper.toDomain(springRepository.save(mapper.toJpa(serieActive)));
    }

    @Override
    public Optional<SerieActive> findById(UUID id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<SerieActive> findByNiveauActive(UUID niveauActiveId) {
        return springRepository.findByNiveauActiveId(niveauActiveId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void supprimer(UUID id) {
        springRepository.deleteById(id);
    }
}

interface SpringSerieActiveJpaRepository extends JpaRepository<SerieActiveJpaEntity, UUID> {

    List<SerieActiveJpaEntity> findByNiveauActiveId(UUID niveauActiveId);
}
