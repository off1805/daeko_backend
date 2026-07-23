package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerSerieCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierSerieCommand;
import com.example.daeko.referentiel.application.port.in.SerieUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Filiere;
import com.example.daeko.referentiel.domain.model.Niveau;
import com.example.daeko.referentiel.domain.model.Serie;
import com.example.daeko.referentiel.domain.port.FiliereRepository;
import com.example.daeko.referentiel.domain.port.NiveauRepository;
import com.example.daeko.referentiel.domain.port.SerieRepository;
import com.example.daeko.referentiel.domain.service.ReferentielValidationService;
import com.example.daeko.referentiel.infrastructure.config.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Vérifie REF-006 (parent actif : la Filière doit être ACTIVE) puis REF-011
 * (le niveau d'apparition doit appartenir à un cycle du même ordre d'enseignement
 * que la filière) avant toute création, puis REF-001 (unicité du code dans la filière).
 */
@Service
public class SerieApplicationService
        extends AbstractReferentielApplicationService<Serie>
        implements SerieUseCase {

    private static final String TYPE_ENTITE = "Serie";

    private final SerieRepository repository;
    private final FiliereRepository filiereRepository;
    private final NiveauRepository niveauRepository;
    private final ReferentielValidationService validationService;

    public SerieApplicationService(SerieRepository repository,
                                   FiliereRepository filiereRepository,
                                   NiveauRepository niveauRepository,
                                   ReferentielValidationService validationService,
                                   AuditPort auditPort,
                                   EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
        this.filiereRepository = filiereRepository;
        this.niveauRepository = niveauRepository;
        this.validationService = validationService;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_SERIES, allEntries = true)
    public Serie creer(CreerSerieCommand command) {
        Filiere filiere = filiereRepository.trouverParId(command.getFiliereId())
                .orElseThrow(EntiteIntrouvableException::new);
        Niveau niveauApparition = niveauRepository.trouverParId(command.getNiveauApparitionId())
                .orElseThrow(EntiteIntrouvableException::new);

        // REF-006 : la filière parente doit être active
        validationService.verifierParentActif(filiere);

        // REF-011 : le niveau d'apparition doit appartenir à un cycle du même ordre
        // d'enseignement que la filière
        validationService.verifierCoherenceCreationSerie(filiere, niveauApparition);

        // REF-001 : unicité du code dans la filière
        if (repository.existeParCode(command.getFiliereId(), command.getCode())) {
            throw new ViolationUniciteException("Une série avec le code '"
                    + command.getCode() + "' existe déjà pour cette filière.");
        }

        Serie entite = new Serie(
                command.getFiliereId(),
                command.getNiveauApparitionId(),
                command.getCode(),
                command.getLibelle(),
                command.getLibelleCourt(),
                command.getLibelleEn(),
                command.getDescription(),
                command.getDateEntreeVigueur(),
                command.getUtilisateurId()
        );

        Serie sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_SERIES, allEntries = true)
    public Serie modifier(ModifierSerieCommand command) {
        Serie entite = repository.trouverParId(command.getId())
                .orElseThrow(EntiteIntrouvableException::new);
        Serie avant = copier(entite);

        entite.setLibelle(command.getLibelle());
        entite.setLibelleEn(command.getLibelleEn());
        entite.setDescription(command.getDescription());
        entite.setModifiePar(command.getUtilisateurId());

        Serie sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "MODIFICATION",
                command.getUtilisateurId(), null, avant, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_SERIES, allEntries = true)
    public Serie deprecier(DeprecierCommand command) {
        Serie entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        Serie modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_SERIES, key = "#id")
    public Serie consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_SERIES, key = "#etat")
    public List<Serie> rechercher(EtatReferentiel etat) {
        return repository.rechercher(etat);
    }

    @Override
    protected Serie copier(Serie source) {
        Serie copie = new Serie(
                source.getFiliereId(),
                source.getNiveauApparitionId(),
                source.getCode(),
                source.getLibelle(),
                source.getLibelleCourt(),
                source.getLibelleEn(),
                source.getDescription(),
                source.getDateEntreeVigueur(),
                source.getCreePar());
        copie.setId(source.getId());
        copie.setModifiePar(source.getModifiePar());
        return copie;
    }
}