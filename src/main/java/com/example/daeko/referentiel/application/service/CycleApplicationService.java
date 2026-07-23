package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerCycleCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierCycleCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.CycleUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.domain.model.SousSysteme;
import com.example.daeko.referentiel.domain.port.CycleRepository;
import com.example.daeko.referentiel.domain.port.OrdreEnseignementRepository;
import com.example.daeko.referentiel.domain.port.SousSystemeRepository;
import com.example.daeko.referentiel.domain.service.ReferentielValidationService;
import com.example.daeko.referentiel.infrastructure.config.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Vérifie REF-006 (parents actifs : le SousSysteme ET l'OrdreEnseignement doivent
 * être ACTIVE) avant toute création, puis REF-001 (unicité du code, scopée par
 * sous-système + ordre d'enseignement, cf. contrainte uq_cycle_ss_oe_code).
 */
@Service
public class CycleApplicationService
        extends AbstractReferentielApplicationService<Cycle>
        implements CycleUseCase {

    private static final String TYPE_ENTITE = "Cycle";

    private final CycleRepository repository;
    private final SousSystemeRepository sousSystemeRepository;
    private final OrdreEnseignementRepository ordreEnseignementRepository;
    private final ReferentielValidationService validationService;

    public CycleApplicationService(CycleRepository repository,
                                   SousSystemeRepository sousSystemeRepository,
                                   OrdreEnseignementRepository ordreEnseignementRepository,
                                   ReferentielValidationService validationService,
                                   AuditPort auditPort,
                                   EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
        this.sousSystemeRepository = sousSystemeRepository;
        this.ordreEnseignementRepository = ordreEnseignementRepository;
        this.validationService = validationService;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_CYCLES, allEntries = true)
    public Cycle creer(CreerCycleCommand command) {
        SousSysteme sousSysteme = sousSystemeRepository.trouverParId(command.getSousSystemeId())
                .orElseThrow(EntiteIntrouvableException::new);
        OrdreEnseignement ordreEnseignement = ordreEnseignementRepository
                .trouverParId(command.getOrdreEnseignementId())
                .orElseThrow(EntiteIntrouvableException::new);

        // REF-006 : les deux parents doivent être actifs
        validationService.verifierParentActif(sousSysteme);
        validationService.verifierParentActif(ordreEnseignement);

        // REF-001 : unicité du code, scopée par sous-système + ordre d'enseignement
        if (repository.existeParCode(command.getSousSystemeId(), command.getOrdreEnseignementId(), command.getCode())) {
            throw new ViolationUniciteException(
                    "Un cycle avec le code '" + command.getCode() + "' existe déjà pour ce sous-système et cet ordre d'enseignement.");
        }

        Cycle entite = new Cycle(
                command.getSousSystemeId(),
                command.getOrdreEnseignementId(),
                command.getCode(),
                command.getLibelle(),
                command.getLibelleEn(),
                command.getRang(),
                command.getDureeTheoriqueAnnees(),
                command.getDescription(),
                command.getDateEntreeVigueur(),
                command.getUtilisateurId());

        Cycle sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_CYCLES, allEntries = true)
    public Cycle modifier(ModifierCycleCommand command) {
        Cycle entite = repository.trouverParId(command.getId())
                .orElseThrow(EntiteIntrouvableException::new);
        Cycle avant = copier(entite);

        entite.setLibelle(command.getLibelle());
        entite.setLibelleEn(command.getLibelleEn());
        entite.setDescription(command.getDescription());
        entite.setModifiePar(command.getUtilisateurId());

        Cycle sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "MODIFICATION",
                command.getUtilisateurId(), null, avant, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_CYCLES, allEntries = true)
    public Cycle deprecier(DeprecierCommand command) {
        Cycle entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        Cycle modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_CYCLES, allEntries = true)
    public Cycle reactiver(ReactiverCommand command) {
        Cycle entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        Cycle modifiee = executerReactivation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_CYCLES, key = "#id")
    public Cycle consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_CYCLES, key = "#etat")
    public List<Cycle> rechercher(EtatReferentiel etat) {
        return repository.rechercher(etat);
    }

    @Override
    protected Cycle copier(Cycle source) {
        Cycle copie = new Cycle(
                source.getSousSystemeId(),
                source.getOrdreEnseignementId(),
                source.getCode(),
                source.getLibelle(),
                source.getLibelleEn(),
                source.getRang(),
                source.getDureeTheoriqueAnnees(),
                source.getDescription(),
                source.getDateEntreeVigueur(),
                source.getCreePar());
        copie.setId(source.getId());
        copie.setModifiePar(source.getModifiePar());
        return copie;
    }
}
