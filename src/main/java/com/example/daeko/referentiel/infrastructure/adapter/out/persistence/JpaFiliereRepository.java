package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Filiere;
import com.example.daeko.referentiel.domain.port.FiliereRepository;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.FiliereJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.FiliereMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaFiliereRepository implements FiliereRepository {

    private final SpringDataFiliereRepository springRepo;
    private final FiliereMapper mapper;

    public JpaFiliereRepository(SpringDataFiliereRepository springRepo, FiliereMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Filiere sauvegarder(Filiere filiere) {
        FiliereJpaEntity entity = mapper.toEntity(filiere);
        return mapper.toDomain(springRepo.save(entity));
    }

    @Override
    public Optional<Filiere> trouverParId(UUID id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Filiere> rechercher(EtatReferentiel etat) {
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

interface SpringDataFiliereRepository extends JpaRepository<FiliereJpaEntity, UUID> {
    List<FiliereJpaEntity> findByEtat(EtatReferentielJpa etat);
    boolean existsByCode(String code);
}
