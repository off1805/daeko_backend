package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.SousSysteme;
import com.example.daeko.referentiel.domain.port.SousSystemeRepository;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.SousSystemeJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.SousSystemeMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaSousSystemeRepository implements SousSystemeRepository {

    private final SpringDataSousSystemeRepository springRepo;
    private final SousSystemeMapper mapper;

    public JpaSousSystemeRepository(SpringDataSousSystemeRepository springRepo,
                                    SousSystemeMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public SousSysteme sauvegarder(SousSysteme sousSysteme) {
        SousSystemeJpaEntity entite = mapper.toEntity(sousSysteme);
        return mapper.toDomain(springRepo.save(entite));
    }

    @Override
    public Optional<SousSysteme> trouverParId(UUID id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<SousSysteme> rechercher(EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findByEtat(etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeParCode(String code) {
        return springRepo.existsByCode(code);
    }
}

interface SpringDataSousSystemeRepository extends JpaRepository<SousSystemeJpaEntity, UUID> {
    List<SousSystemeJpaEntity> findByEtat(EtatReferentielJpa etat);
    boolean existsByCode(String code);
}
