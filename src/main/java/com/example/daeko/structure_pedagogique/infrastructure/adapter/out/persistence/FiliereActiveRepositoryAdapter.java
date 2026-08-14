package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.persistence;

import com.example.daeko.structure_pedagogique.domain.model.FiliereActive;
import com.example.daeko.structure_pedagogique.domain.port.FiliereActiveRepositoryPort;
import com.example.daeko.structure_pedagogique.infrastructure.entity.FiliereActiveJpaEntity;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.FiliereActiveMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FiliereActiveRepositoryAdapter implements FiliereActiveRepositoryPort {

    private final SpringFiliereActiveJpaRepository springRepository;
    private final FiliereActiveMapper mapper;

    public FiliereActiveRepositoryAdapter(SpringFiliereActiveJpaRepository springRepository, FiliereActiveMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public FiliereActive save(FiliereActive filiereActive) {
        return mapper.toDomain(springRepository.save(mapper.toJpa(filiereActive)));
    }

    @Override
    public List<FiliereActive> findByConfiguration(UUID configurationId) {
        return springRepository.findByConfigurationId(configurationId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void supprimer(UUID id) {
        springRepository.deleteById(id);
    }
}

interface SpringFiliereActiveJpaRepository extends JpaRepository<FiliereActiveJpaEntity, UUID> {

    List<FiliereActiveJpaEntity> findByConfigurationId(UUID configurationId);
}
