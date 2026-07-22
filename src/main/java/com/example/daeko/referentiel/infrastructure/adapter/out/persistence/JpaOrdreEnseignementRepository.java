package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.domain.port.OrdreEnseignementRepository;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.OrdreEnseignementJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.OrdreEnseignementMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaOrdreEnseignementRepository implements OrdreEnseignementRepository {

    private final SpringDataOrdreEnseignementRepository springRepo;
    private final OrdreEnseignementMapper mapper;

    public JpaOrdreEnseignementRepository(SpringDataOrdreEnseignementRepository springRepo,
                                          OrdreEnseignementMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public OrdreEnseignement sauvegarder(OrdreEnseignement ordreEnseignement) {
        OrdreEnseignementJpaEntity entity = mapper.toEntity(ordreEnseignement);
        return mapper.toDomain(springRepo.save(entity));
    }

    @Override
    public Optional<OrdreEnseignement> trouverParId(UUID id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<OrdreEnseignement> rechercher(EtatReferentiel etat) {
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

interface SpringDataOrdreEnseignementRepository extends JpaRepository<OrdreEnseignementJpaEntity, UUID> {
    List<OrdreEnseignementJpaEntity> findByEtat(EtatReferentielJpa etat);
    boolean existsByCode(String code);
}