package com.example.daeko.structure_pedagogique.infrastructure.adapter.out.persistence;

import com.example.daeko.structure_pedagogique.domain.model.ConfigurationBrancheAnnee;
import com.example.daeko.structure_pedagogique.domain.port.ConfigurationBrancheAnneeRepositoryPort;
import com.example.daeko.structure_pedagogique.infrastructure.entity.ConfigurationBrancheAnneeJpaEntity;
import com.example.daeko.structure_pedagogique.infrastructure.mapper.ConfigurationBrancheAnneeMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ConfigurationBrancheAnneeRepositoryAdapter implements ConfigurationBrancheAnneeRepositoryPort {

    private final SpringConfigurationBrancheAnneeJpaRepository springRepository;
    private final ConfigurationBrancheAnneeMapper mapper;

    public ConfigurationBrancheAnneeRepositoryAdapter(SpringConfigurationBrancheAnneeJpaRepository springRepository,
                                                       ConfigurationBrancheAnneeMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public ConfigurationBrancheAnnee save(ConfigurationBrancheAnnee configuration) {
        return mapper.toDomain(springRepository.save(mapper.toJpa(configuration)));
    }

    @Override
    public Optional<ConfigurationBrancheAnnee> findById(UUID id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<ConfigurationBrancheAnnee> findByBrancheEtAnnee(UUID brancheId, UUID anneeAcademiqueId) {
        return springRepository.findByBrancheIdAndAnneeAcademiqueId(brancheId, anneeAcademiqueId).map(mapper::toDomain);
    }

    @Override
    public List<ConfigurationBrancheAnnee> findByAnneeAcademique(UUID anneeAcademiqueId) {
        return springRepository.findByAnneeAcademiqueId(anneeAcademiqueId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ConfigurationBrancheAnnee> findDerniereConfigurationAvant(UUID brancheId, UUID anneeAcademiqueCibleId) {
        // NOTE jour 4/7 : necessite de comparer les dates de l'annee academique cible avec celles des
        // autres annees du meme etablissement -- a affiner avec une requete dediee (jointure sur
        // annee_academique.date_debut) plutot que ce filtrage cote application, garde simple ici pour le jour 2.
        return springRepository.findAll().stream()
            .filter(e -> e.getBrancheId().equals(brancheId) && !e.getAnneeAcademiqueId().equals(anneeAcademiqueCibleId))
            .max(Comparator.comparing(ConfigurationBrancheAnneeJpaEntity::getDateCreation))
            .map(mapper::toDomain);
    }
}

interface SpringConfigurationBrancheAnneeJpaRepository extends JpaRepository<ConfigurationBrancheAnneeJpaEntity, UUID> {

    Optional<ConfigurationBrancheAnneeJpaEntity> findByBrancheIdAndAnneeAcademiqueId(UUID brancheId, UUID anneeAcademiqueId);

    List<ConfigurationBrancheAnneeJpaEntity> findByAnneeAcademiqueId(UUID anneeAcademiqueId);
}
