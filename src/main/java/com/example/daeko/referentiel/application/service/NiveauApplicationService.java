package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerNiveauCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierNiveauCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.NiveauUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Niveau;
import com.example.daeko.referentiel.domain.port.CycleRepository;
import com.example.daeko.referentiel.domain.port.NiveauRepository;
import com.example.daeko.referentiel.domain.service.ReferentielValidationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daeko.referentiel.infrastructure.config.CacheConfig;

import java.util.List;
import java.util.UUID;

/**
 * Vérifie REF-006 (parent actif : le Cycle doit être ACTIVE) avant toute création,
 * puis REF-001 (unicité du code dans le cycle).
 */
@Service
public class NiveauApplicationService
        extends AbstractReferentielApplicationService<Niveau>
        implements NiveauUseCase {

    private static final String TYPE_ENTITE = "Niveau";

    private final NiveauRepository repository;
    private final CycleRepository cycleRepository;
    private final ReferentielValidationService validationService;

    public NiveauApplicationService(NiveauRepository repository,
                                    CycleRepository cycleRepository,
                                    ReferentielValidationService validationService,
                                    AuditPort auditPort,
                                    EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
        this.cycleRepository = cycleRepository;
        this.validationService = validationService;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_NIVEAUX, allEntries = true)
    public Niveau creer(CreerNiveauCommand command) {
        Cycle cycle = cycleRepository.trouverParId(command.getCycleId())
                .orElseThrow(EntiteIntrouvableException::new);

        // REF-006 : le cycle parent doit être actif
        validationService.verifierParentActif(cycle);

        if (repository.existeParCode(command.getCycleId(), command.getCode())) {
            throw new ViolationUniciteException(
                    "Un niveau avec le code '" + command.getCode() + "' existe déjà dans ce cycle.");
        }

        Niveau entite = new Niveau(
                command.getCycleId(), command.getCode(), command.getLibelle(),
                command.getLibelleCourt(), command.getLibelleEn(), command.getRangDansCycle(),
                command.getAgeTheoriqueDebut(), command.getDescription(),
                command.getDateEntreeVigueur(), command.getUtilisateurId());
        Niveau sauvegarde = repository.sauvegarder(entite);

        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_NIVEAUX, allEntries = true)
    public Niveau modifier(ModifierNiveauCommand command) {
        Niveau entite = repository.trouverParId(command.getId())
                .orElseThrow(EntiteIntrouvableException::new);
        Niveau avant = copier(entite);

        entite.setLibelle(command.getLibelle());
        entite.setLibelleCourt(command.getLibelleCourt());
        entite.setLibelleEn(command.getLibelleEn());
        entite.setAgeTheoriqueDebut(command.getAgeTheoriqueDebut());
        entite.setDescription(command.getDescription());
        entite.setModifiePar(command.getUtilisateurId());

        Niveau sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "MODIFICATION",
                command.getUtilisateurId(), null, avant, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_NIVEAUX, allEntries = true)
    public Niveau deprecier(DeprecierCommand command) {
        Niveau entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        Niveau modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_NIVEAUX, allEntries = true)
    public Niveau reactiver(ReactiverCommand command) {
        Niveau entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        Niveau modifiee = executerReactivation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_NIVEAUX, key = "#id")
    public Niveau consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_NIVEAUX, key = "#cycleId + '-' + #etat")
    public List<Niveau> rechercher(UUID cycleId, EtatReferentiel etat) {
        return cycleId != null
                ? repository.rechercher(cycleId, etat)
                : repository.rechercher(etat);
    }

    @Override
    protected Niveau copier(Niveau source) {
        Niveau copie = new Niveau(
                source.getCycleId(), source.getCode(), source.getLibelle(),
                source.getLibelleCourt(), source.getLibelleEn(), source.getRangDansCycle(),
                source.getAgeTheoriqueDebut(), source.getDescription(),
                source.getDateEntreeVigueur(), source.getCreePar());
        copie.setId(source.getId());
        copie.setModifiePar(source.getModifiePar());
        return copie;
    }
}