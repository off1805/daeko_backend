package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierTypeEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.TypeEnseignementUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import com.example.daeko.referentiel.domain.port.TypeEnseignementRepository;
import com.example.daeko.referentiel.infrastructure.config.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Table racine, aucun parent, donc pas de vérification REF-006 à la création.
 * Vérifie uniquement REF-001 (unicité du code).
 */
@Service
public class TypeEnseignementApplicationService
        extends AbstractReferentielApplicationService<TypeEnseignement>
        implements TypeEnseignementUseCase {

    private static final String TYPE_ENTITE = "TypeEnseignement";

    private final TypeEnseignementRepository repository;

    public TypeEnseignementApplicationService(TypeEnseignementRepository repository,
                                              AuditPort auditPort,
                                              EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_TYPES_ENSEIGNEMENT, allEntries = true)
    public TypeEnseignement creer(CreerTypeEnseignementCommand command) {
        if (repository.existeParCode(command.getCode())) {
            throw new ViolationUniciteException(
                    "Un type d'enseignement avec le code '" + command.getCode() + "' existe déjà.");
        }

        TypeEnseignement entite = new TypeEnseignement(
                command.getCode(), command.getLibelle(), command.getDescription(),
                command.getDateEntreeVigueur());
        entite.setCreePar(command.getUtilisateurId());
        entite.setModifiePar(command.getUtilisateurId());

        TypeEnseignement sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_TYPES_ENSEIGNEMENT, allEntries = true)
    public TypeEnseignement modifier(ModifierTypeEnseignementCommand command) {
        TypeEnseignement entite = repository.trouverParId(command.getId())
                .orElseThrow(EntiteIntrouvableException::new);
        TypeEnseignement avant = copier(entite);

        entite.setLibelle(command.getLibelle());
        entite.setDescription(command.getDescription());
        entite.setModifiePar(command.getUtilisateurId());

        TypeEnseignement sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "MODIFICATION",
                command.getUtilisateurId(), null, avant, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_TYPES_ENSEIGNEMENT, allEntries = true)
    public TypeEnseignement deprecier(DeprecierCommand command) {
        TypeEnseignement entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        TypeEnseignement modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_TYPES_ENSEIGNEMENT, allEntries = true)
    public TypeEnseignement reactiver(ReactiverCommand command) {
        TypeEnseignement entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        TypeEnseignement modifiee = executerReactivation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_TYPES_ENSEIGNEMENT, key = "#id")
    public TypeEnseignement consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_TYPES_ENSEIGNEMENT, key = "#etat")
    public List<TypeEnseignement> rechercher(EtatReferentiel etat) {
        return repository.rechercher(etat);
    }

    @Override
    protected TypeEnseignement copier(TypeEnseignement source) {
        TypeEnseignement copie = new TypeEnseignement(
                source.getCode(), source.getLibelle(), source.getDescription(),
                source.getDateEntreeVigueur());
        copie.setId(source.getId());
        copie.setCreePar(source.getCreePar());
        copie.setModifiePar(source.getModifiePar());
        return copie;
    }
}
