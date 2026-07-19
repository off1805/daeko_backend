package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Niveau;
import com.example.daeko.referentiel.domain.port.NiveauRepository;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.NiveauJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.NiveauMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaNiveauRepository implements NiveauRepository {

    private final SpringDataNiveauRepository springRepo;
    private final NiveauMapper mapper;

    public JpaNiveauRepository(SpringDataNiveauRepository springRepo, NiveauMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Niveau sauvegarder(Niveau niveau) {
        NiveauJpaEntity entity = mapper.toEntity(niveau);
        return mapper.toDomain(springRepo.save(entity));
    }

    @Override
    public Optional<Niveau> trouverParId(UUID id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Niveau> rechercher(EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findByEtat(etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Niveau> rechercher(UUID cycleId, EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findByCycleIdAndEtat(cycleId, etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeParCode(UUID cycleId, String code) {
        return springRepo.existsByCycleIdAndCode(cycleId, code);
    }
}

interface SpringDataNiveauRepository extends JpaRepository<NiveauJpaEntity, UUID> {
    List<NiveauJpaEntity> findByEtat(EtatReferentielJpa etat);
    List<NiveauJpaEntity> findByCycleIdAndEtat(UUID cycleId, EtatReferentielJpa etat);
    boolean existsByCycleIdAndCode(UUID cycleId, String code);
}
