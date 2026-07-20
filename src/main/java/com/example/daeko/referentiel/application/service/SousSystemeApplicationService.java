package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerSousSystemeCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierSousSystemeCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.SousSystemeUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.SousSysteme;
import com.example.daeko.referentiel.domain.port.SousSystemeRepository;
import com.example.daeko.referentiel.infrastructure.config.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SousSystemeApplicationService
        extends AbstractReferentielApplicationService<SousSysteme>
        implements SousSystemeUseCase {

    private static final String TYPE_ENTITE = "SousSysteme";

    private final SousSystemeRepository repository;

    public SousSystemeApplicationService(SousSystemeRepository repository,
                                         AuditPort auditPort,
                                         EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_SOUS_SYSTEMES, allEntries = true)
    public SousSysteme creer(CreerSousSystemeCommand command) {
        if (repository.existeParCode(command.getCode())) {
            throw new ViolationUniciteException("Un sous-système avec le code '"
                    + command.getCode() + "' existe déjà.");
        }
        SousSysteme entite = new SousSysteme(
                command.getCode(), command.getLibelle(), command.getLibelleCourt(),
                command.getDescription(), command.getLanguePrincipale(), null, command.getUtilisateurId());
        SousSysteme sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_SOUS_SYSTEMES, allEntries = true)
    public SousSysteme modifier(ModifierSousSystemeCommand command) {
        SousSysteme entite = repository.trouverParId(command.getId())
                .orElseThrow(EntiteIntrouvableException::new);
        SousSysteme avant = copier(entite);
        entite.setLibelle(command.getLibelle());
        entite.setLibelleCourt(command.getLibelleCourt());
        entite.setDescription(command.getDescription());
        entite.setModifiePar(command.getUtilisateurId());
        SousSysteme sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "MODIFICATION",
                command.getUtilisateurId(), null, avant, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_SOUS_SYSTEMES, allEntries = true)
    public SousSysteme deprecier(DeprecierCommand command) {
        SousSysteme entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        SousSysteme modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_SOUS_SYSTEMES, allEntries = true)
    public SousSysteme reactiver(ReactiverCommand command) {
        SousSysteme entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        SousSysteme modifiee = executerReactivation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_SOUS_SYSTEMES, key = "#id")
    public SousSysteme consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_SOUS_SYSTEMES, key = "#etat")
    public List<SousSysteme> rechercher(EtatReferentiel etat) {
        return repository.rechercher(etat);
    }

    @Override
    protected SousSysteme copier(SousSysteme source) {
        SousSysteme copie = new SousSysteme(
                source.getCode(), source.getLibelle(), source.getLibelleCourt(),
                source.getDescription(), source.getLanguePrincipale(),
                source.getDateEntreeVigueur(), source.getCreePar());
        copie.setId(source.getId());
        copie.setModifiePar(source.getModifiePar());
        return copie;
    }
}