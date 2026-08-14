package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.persistence;

import com.example.daeko.structure_pedagogique.domain.model.AnneeAcademique;
import com.example.daeko.structure_pedagogique.domain.model.EtatAnnee;
import com.example.daeko.structure_pedagogique.domain.port.AnneeAcademiqueRepositoryPort;
import com.example.daeko.structure_pedagogique.infrastructure.entity.AnneeAcademiqueJpaEntity;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.AnneeAcademiqueMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AnneeAcademiqueRepositoryAdapter implements AnneeAcademiqueRepositoryPort {

    private final SpringAnneeAcademiqueJpaRepository springRepository;
    private final AnneeAcademiqueMapper mapper;

    public AnneeAcademiqueRepositoryAdapter(SpringAnneeAcademiqueJpaRepository springRepository, AnneeAcademiqueMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public AnneeAcademique save(AnneeAcademique annee) {
        return mapper.toDomain(springRepository.save(mapper.toJpa(annee)));
    }

    @Override
    public Optional<AnneeAcademique> findById(UUID id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<AnneeAcademique> findByIdEtEtablissement(UUID id, UUID etablissementId) {
        return springRepository.findByIdAndEtablissementId(id, etablissementId).map(mapper::toDomain);
    }

    @Override
    public List<AnneeAcademique> findByEtablissement(UUID etablissementId) {
        return springRepository.findByEtablissementIdOrderByDateDebutDesc(etablissementId)
            .stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<AnneeAcademique> findEnCoursByEtablissement(UUID etablissementId) {
        return springRepository.findByEtablissementIdAndEtat(etablissementId, EtatAnnee.EN_COURS).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEtablissementAndLibelle(UUID etablissementId, String libelle) {
        return springRepository.existsByEtablissementIdAndLibelle(etablissementId, libelle);
    }
}

interface SpringAnneeAcademiqueJpaRepository extends JpaRepository<AnneeAcademiqueJpaEntity, UUID> {

    Optional<AnneeAcademiqueJpaEntity> findByIdAndEtablissementId(UUID id, UUID etablissementId);

    List<AnneeAcademiqueJpaEntity> findByEtablissementIdOrderByDateDebutDesc(UUID etablissementId);

    Optional<AnneeAcademiqueJpaEntity> findByEtablissementIdAndEtat(UUID etablissementId, EtatAnnee etat);

    boolean existsByEtablissementIdAndLibelle(UUID etablissementId, String libelle);
}
