package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import com.example.daeko.referentiel.domain.port.TypeEnseignementRepository;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.TypeEnseignementJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.TypeEnseignementMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaTypeEnseignementRepository implements TypeEnseignementRepository {

    private final SpringDataTypeEnseignementRepository springRepo;
    private final TypeEnseignementMapper mapper;

    public JpaTypeEnseignementRepository(SpringDataTypeEnseignementRepository springRepo,
                                         TypeEnseignementMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public TypeEnseignement sauvegarder(TypeEnseignement typeEnseignement) {
        TypeEnseignementJpaEntity entity = mapper.toEntity(typeEnseignement);
        return mapper.toDomain(springRepo.save(entity));
    }

    @Override
    public Optional<TypeEnseignement> trouverParId(UUID id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<TypeEnseignement> rechercher(EtatReferentiel etat) {
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

interface SpringDataTypeEnseignementRepository extends JpaRepository<TypeEnseignementJpaEntity, UUID> {
    List<TypeEnseignementJpaEntity> findByEtat(EtatReferentielJpa etat);
    boolean existsByCode(String code);
}
