package com.example.daeko.referentiel.infrastructure.adapter.out.persistence;

import com.example.daeko.referentiel.application.port.out.TypeEnseignementRepository;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class TypeEnseignementPersistenceAdapter implements TypeEnseignementRepository {

    private final SpringDataTypeEnseignementRepository repository;

    public TypeEnseignementPersistenceAdapter(SpringDataTypeEnseignementRepository repository) {
        this.repository = repository;
    }

    @Override
    public TypeEnseignement save(TypeEnseignement typeEnseignement) {
        // Enregistrement réel dans la base de données
        // Si tu as un mapper persistence/domaine, applique-le ici. Sinon :
        return repository.save(typeEnseignement);
    }

    @Override
    public Optional<TypeEnseignement> findById(UUID id) {
        return repository.findById(id);
    }
}