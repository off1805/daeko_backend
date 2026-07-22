package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerOrdreEnseignementCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierOrdreEnseignementCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.OrdreEnseignementUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.domain.port.OrdreEnseignementRepository;
import com.example.daeko.referentiel.infrastructure.config.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Table racine, aucun parent,
 * donc pas de vérification REF-006 à la création. Vérifie uniquement REF-001
 * (unicité du code).
 */
@Service
public class OrdreEnseignementApplicationService
        extends AbstractReferentielApplicationService<OrdreEnseignement>
        implements OrdreEnseignementUseCase {

    private static final String TYPE_ENTITE = "OrdreEnseignement";

    private final OrdreEnseignementRepository repository;

    public OrdreEnseignementApplicationService(OrdreEnseignementRepository repository,
                                               AuditPort auditPort,
                                               EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_ORDRES_ENSEIGNEMENT, allEntries = true)
    public OrdreEnseignement creer(CreerOrdreEnseignementCommand command) {
        if (repository.existeParCode(command.getCode())) {
            throw new ViolationUniciteException(
                    "Un ordre d'enseignement avec le code '" + command.getCode() + "' existe déjà.");
        }

        OrdreEnseignement entite = new OrdreEnseignement(
                command.getCode(), command.getLibelle(), command.getTutelleMinisterielle(),
                command.getTutelleMinisterielleEn(), command.getRang(), command.getDescription(),
                command.getDateEntreeVigueur());
        entite.setCreePar(command.getUtilisateurId());
        entite.setModifiePar(command.getUtilisateurId());

        OrdreEnseignement sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_ORDRES_ENSEIGNEMENT, allEntries = true)
    public OrdreEnseignement modifier(ModifierOrdreEnseignementCommand command) {
        OrdreEnseignement entite = repository.trouverParId(command.getId())
                .orElseThrow(EntiteIntrouvableException::new);
        OrdreEnseignement avant = copier(entite);

        entite.setLibelle(command.getLibelle());
        entite.setTutelleMinisterielle(command.getTutelleMinisterielle());
        entite.setTutelleMinisterielleEn(command.getTutelleMinisterielleEn());
        entite.setDescription(command.getDescription());
        entite.setModifiePar(command.getUtilisateurId());

        OrdreEnseignement sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "MODIFICATION",
                command.getUtilisateurId(), null, avant, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_ORDRES_ENSEIGNEMENT, allEntries = true)
    public OrdreEnseignement deprecier(DeprecierCommand command) {
        OrdreEnseignement entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        OrdreEnseignement modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_ORDRES_ENSEIGNEMENT, allEntries = true)
    public OrdreEnseignement reactiver(ReactiverCommand command) {
        OrdreEnseignement entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        OrdreEnseignement modifiee = executerReactivation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_ORDRES_ENSEIGNEMENT, key = "#id")
    public OrdreEnseignement consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_ORDRES_ENSEIGNEMENT, key = "#etat")
    public List<OrdreEnseignement> rechercher(EtatReferentiel etat) {
        return repository.rechercher(etat);
    }

    @Override
    protected OrdreEnseignement copier(OrdreEnseignement source) {
        OrdreEnseignement copie = new OrdreEnseignement(
                source.getCode(), source.getLibelle(), source.getTutelleMinisterielle(),
                source.getTutelleMinisterielleEn(), source.getRang(), source.getDescription(),
                source.getDateEntreeVigueur());
        copie.setId(source.getId());
        copie.setCreePar(source.getCreePar());
        copie.setModifiePar(source.getModifiePar());
        return copie;
    }
}