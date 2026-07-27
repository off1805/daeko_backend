package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.persistence;

import com.example.daeko.structure_pedagogique.domain.model.NiveauActive;
import com.example.daeko.structure_pedagogique.domain.port.NiveauActiveRepositoryPort;
import com.example.daeko.structure_pedagogique.infrastructure.entity.NiveauActiveJpaEntity;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.NiveauActiveMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class NiveauActiveRepositoryAdapter implements NiveauActiveRepositoryPort {

    private final SpringNiveauActiveJpaRepository springRepository;
    private final NiveauActiveMapper mapper;

    public NiveauActiveRepositoryAdapter(SpringNiveauActiveJpaRepository springRepository, NiveauActiveMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public NiveauActive save(NiveauActive niveauActive) {
        return mapper.toDomain(springRepository.save(mapper.toJpa(niveauActive)));
    }

    @Override
    public Optional<NiveauActive> findById(UUID id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<NiveauActive> findByConfiguration(UUID configurationId) {
        return springRepository.findByConfigurationId(configurationId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void supprimer(UUID id) {
        springRepository.deleteById(id);
    }
}

interface SpringNiveauActiveJpaRepository extends JpaRepository<NiveauActiveJpaEntity, UUID> {

    List<NiveauActiveJpaEntity> findByConfigurationId(UUID configurationId);
}
