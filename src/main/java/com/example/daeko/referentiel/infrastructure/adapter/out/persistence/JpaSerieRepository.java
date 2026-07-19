package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Serie;
import com.example.daeko.referentiel.domain.port.SerieRepository;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.SerieJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.SerieMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaSerieRepository implements SerieRepository {

    private final SpringDataSerieRepository springRepo;
    private final SerieMapper mapper;

    public JpaSerieRepository(SpringDataSerieRepository springRepo, SerieMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Serie sauvegarder(Serie serie) {
        SerieJpaEntity entity = mapper.toEntity(serie);
        return mapper.toDomain(springRepo.save(entity));
    }

    @Override
    public Optional<Serie> trouverParId(UUID id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Serie> rechercher(EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findByEtat(etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Serie> rechercher(UUID filiereId, UUID niveauId, EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findByFiliereIdAndNiveauApparitionIdAndEtat(filiereId, niveauId, etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeParCode(UUID filiereId, String code) {
        return springRepo.existsByFiliereIdAndCode(filiereId, code);
    }
}

interface SpringDataSerieRepository extends JpaRepository<SerieJpaEntity, UUID> {
    List<SerieJpaEntity> findByEtat(EtatReferentielJpa etat);
    List<SerieJpaEntity> findByFiliereIdAndNiveauApparitionIdAndEtat(UUID filiereId, UUID niveauId, EtatReferentielJpa etat);
    boolean existsByFiliereIdAndCode(UUID filiereId, String code);
}
