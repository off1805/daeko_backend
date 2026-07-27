package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.persistence;

import com.example.daeko.structure_pedagogique.domain.model.Classe;
import com.example.daeko.structure_pedagogique.domain.port.ClasseRepositoryPort;
import com.example.daeko.structure_pedagogique.infrastructure.entity.ClasseJpaEntity;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.ClasseMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ClasseRepositoryAdapter implements ClasseRepositoryPort {

    private final SpringClasseJpaRepository springRepository;
    private final ClasseMapper mapper;

    public ClasseRepositoryAdapter(SpringClasseJpaRepository springRepository, ClasseMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Classe save(Classe classe) {
        return mapper.toDomain(springRepository.save(mapper.toJpa(classe)));
    }

    @Override
    public Optional<Classe> findById(UUID id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Classe> findByConfiguration(UUID configurationId, boolean actifSeulement) {
        List<ClasseJpaEntity> resultats = actifSeulement
            ? springRepository.findByConfigurationIdAndActifTrue(configurationId)
            : springRepository.findByConfigurationId(configurationId);
        return resultats.stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existeSuffixe(UUID configurationId, UUID niveauActiveId, UUID serieActiveId, String suffixe) {
        return serieActiveId != null
            ? springRepository.existsByConfigurationIdAndNiveauActiveIdAndSerieActiveIdAndSuffixe(configurationId, niveauActiveId, serieActiveId, suffixe)
            : springRepository.existsByConfigurationIdAndNiveauActiveIdAndSuffixeAndSerieActiveIdIsNull(configurationId, niveauActiveId, suffixe);
    }
}

interface SpringClasseJpaRepository extends JpaRepository<ClasseJpaEntity, UUID> {

    List<ClasseJpaEntity> findByConfigurationId(UUID configurationId);

    List<ClasseJpaEntity> findByConfigurationIdAndActifTrue(UUID configurationId);

    boolean existsByConfigurationIdAndNiveauActiveIdAndSerieActiveIdAndSuffixe(
        UUID configurationId, UUID niveauActiveId, UUID serieActiveId, String suffixe);

    boolean existsByConfigurationIdAndNiveauActiveIdAndSuffixeAndSerieActiveIdIsNull(
        UUID configurationId, UUID niveauActiveId, String suffixe);
}
