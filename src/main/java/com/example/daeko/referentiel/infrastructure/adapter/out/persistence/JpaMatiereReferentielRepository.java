package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentiel;
import com.example.daeko.referentiel.domain.port.MatiereReferentielRepository;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.MatiereReferentielJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.MatiereReferentielMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaMatiereReferentielRepository implements MatiereReferentielRepository {

    private final SpringDataMatiereReferentielRepository springRepo;
    private final MatiereReferentielMapper mapper;

    public JpaMatiereReferentielRepository(SpringDataMatiereReferentielRepository springRepo,
                                           MatiereReferentielMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public MatiereReferentiel sauvegarder(MatiereReferentiel matiere) {
        MatiereReferentielJpaEntity entity = mapper.toEntity(matiere);
        return mapper.toDomain(springRepo.save(entity));
    }

    @Override
    public Optional<MatiereReferentiel> trouverParId(UUID id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MatiereReferentiel> rechercher(EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findByEtat(etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatiereReferentiel> rechercher(UUID sousSystemeId, EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findBySousSystemeIdAndEtat(sousSystemeId, etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeParCode(UUID sousSystemeId, String code) {
        return springRepo.existsBySousSystemeIdAndCode(sousSystemeId, code);
    }
}

interface SpringDataMatiereReferentielRepository extends JpaRepository<MatiereReferentielJpaEntity, UUID> {
    List<MatiereReferentielJpaEntity> findByEtat(EtatReferentielJpa etat);
    List<MatiereReferentielJpaEntity> findBySousSystemeIdAndEtat(UUID sousSystemeId, EtatReferentielJpa etat);
    boolean existsBySousSystemeIdAndCode(UUID sousSystemeId, String code);
}
