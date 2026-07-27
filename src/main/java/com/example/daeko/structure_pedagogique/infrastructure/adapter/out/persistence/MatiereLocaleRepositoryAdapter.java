package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.persistence;

import com.example.daeko.structure_pedagogique.domain.model.MatiereLocale;
import com.example.daeko.structure_pedagogique.domain.port.MatiereLocaleRepositoryPort;
import com.example.daeko.structure_pedagogique.infrastructure.entity.MatiereLocaleJpaEntity;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.MatiereLocaleMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MatiereLocaleRepositoryAdapter implements MatiereLocaleRepositoryPort {

    private final SpringMatiereLocaleJpaRepository springRepository;
    private final MatiereLocaleMapper mapper;

    public MatiereLocaleRepositoryAdapter(SpringMatiereLocaleJpaRepository springRepository, MatiereLocaleMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public MatiereLocale save(MatiereLocale matiereLocale) {
        return mapper.toDomain(springRepository.save(mapper.toJpa(matiereLocale)));
    }

    @Override
    public Optional<MatiereLocale> findById(UUID id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MatiereLocale> findByBranche(UUID brancheId) {
        return springRepository.findByBrancheId(brancheId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByBrancheAndCode(UUID brancheId, String code) {
        return springRepository.existsByBrancheIdAndCode(brancheId, code);
    }
}

interface SpringMatiereLocaleJpaRepository extends JpaRepository<MatiereLocaleJpaEntity, UUID> {

    List<MatiereLocaleJpaEntity> findByBrancheId(UUID brancheId);

    boolean existsByBrancheIdAndCode(UUID brancheId, String code);
}
