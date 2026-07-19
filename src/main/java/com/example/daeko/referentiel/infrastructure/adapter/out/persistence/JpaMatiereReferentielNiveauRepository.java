package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentielNiveau;
import com.example.daeko.referentiel.domain.port.MatiereReferentielNiveauRepository;
import com.example.daeko.referentiel.infrastructure.entity.EtatReferentielJpa;
import com.example.daeko.referentiel.infrastructure.entity.MatiereReferentielNiveauJpaEntity;
import com.example.daeko.referentiel.infrastructure.mapper.MatiereReferentielNiveauMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaMatiereReferentielNiveauRepository implements MatiereReferentielNiveauRepository {

    private final SpringDataMatiereReferentielNiveauRepository springRepo;
    private final MatiereReferentielNiveauMapper mapper;

    public JpaMatiereReferentielNiveauRepository(SpringDataMatiereReferentielNiveauRepository springRepo,
                                                 MatiereReferentielNiveauMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public MatiereReferentielNiveau sauvegarder(MatiereReferentielNiveau mrn) {
        MatiereReferentielNiveauJpaEntity entity = mapper.toEntity(mrn);
        return mapper.toDomain(springRepo.save(entity));
    }

    @Override
    public Optional<MatiereReferentielNiveau> trouverParId(UUID id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MatiereReferentielNiveau> rechercher(EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.findByEtat(etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatiereReferentielNiveau> rechercher(UUID niveauId, UUID serieId, EtatReferentiel etat) {
        EtatReferentielJpa etatJpa = EtatReferentielJpa.valueOf(etat.name());
        return springRepo.rechercherParNiveauEtSerie(niveauId, serieId, etatJpa).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeAvecSerie(UUID matiereReferentielId, UUID niveauId, UUID serieId) {
        return springRepo.existsByMatiereReferentielIdAndNiveauIdAndSerieId(matiereReferentielId, niveauId, serieId);
    }

    @Override
    public boolean existeSansSerie(UUID matiereReferentielId, UUID niveauId) {
        return springRepo.existsByMatiereReferentielIdAndNiveauIdAndSerieIsNull(matiereReferentielId, niveauId);
    }
}

interface SpringDataMatiereReferentielNiveauRepository extends JpaRepository<MatiereReferentielNiveauJpaEntity, UUID> {
    List<MatiereReferentielNiveauJpaEntity> findByEtat(EtatReferentielJpa etat);

    @Query("SELECT m FROM MatiereReferentielNiveauJpaEntity m WHERE m.niveau.id = :niveauId AND m.etat = :etat AND (m.serie.id IS NULL OR (:serieId IS NOT NULL AND m.serie.id = :serieId))")
    List<MatiereReferentielNiveauJpaEntity> rechercherParNiveauEtSerie(
            @Param("niveauId") UUID niveauId,
            @Param("serieId") UUID serieId,
            @Param("etat") EtatReferentielJpa etat);

    boolean existsByMatiereReferentielIdAndNiveauIdAndSerieId(UUID matiereReferentielId, UUID niveauId, UUID serieId);

    boolean existsByMatiereReferentielIdAndNiveauIdAndSerieIsNull(UUID matiereReferentielId, UUID niveauId);
}
