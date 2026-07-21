package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerFiliereCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierFiliereCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.FiliereUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.Filiere;
import com.example.daeko.referentiel.domain.model.OrdreEnseignement;
import com.example.daeko.referentiel.domain.model.TypeEnseignement;
import com.example.daeko.referentiel.domain.port.FiliereRepository;
import com.example.daeko.referentiel.domain.port.OrdreEnseignementRepository;
import com.example.daeko.referentiel.domain.port.TypeEnseignementRepository;
import com.example.daeko.referentiel.domain.service.ReferentielValidationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.daeko.referentiel.infrastructure.config.CacheConfig;

import java.util.List;
import java.util.UUID;

/**
 * Vérifie REF-006 (parents actifs : l'OrdreEnseignement ET le TypeEnseignement doivent
 * être ACTIVE) avant toute création, puis REF-001 (unicité du code).
 */
@Service
public class FiliereApplicationService
        extends AbstractReferentielApplicationService<Filiere>
        implements FiliereUseCase {

    private static final String TYPE_ENTITE = "Filiere";

    private final FiliereRepository repository;
    private final OrdreEnseignementRepository ordreEnseignementRepository;
    private final TypeEnseignementRepository typeEnseignementRepository;
    private final ReferentielValidationService validationService;

    public FiliereApplicationService(FiliereRepository repository,
                                     OrdreEnseignementRepository ordreEnseignementRepository,
                                     TypeEnseignementRepository typeEnseignementRepository,
                                     ReferentielValidationService validationService,
                                     AuditPort auditPort,
                                     EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
        this.ordreEnseignementRepository = ordreEnseignementRepository;
        this.typeEnseignementRepository = typeEnseignementRepository;
        this.validationService = validationService;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_FILIERES, allEntries = true)
    public Filiere creer(CreerFiliereCommand command) {
        OrdreEnseignement ordreEnseignement = ordreEnseignementRepository
                .trouverParId(command.getOrdreEnseignementId())
                .orElseThrow(EntiteIntrouvableException::new);
        TypeEnseignement typeEnseignement = typeEnseignementRepository
                .trouverParId(command.getTypeEnseignementId())
                .orElseThrow(EntiteIntrouvableException::new);

        // REF-006 : les deux parents doivent être actifs
        validationService.verifierParentActif(ordreEnseignement);
        validationService.verifierParentActif(typeEnseignement);

        // REF-001 : unicité du code
        if (repository.existeParCode(command.getCode())) {
            throw new ViolationUniciteException(
                    "Une filière avec le code '" + command.getCode() + "' existe déjà.");
        }

        Filiere entite = new Filiere(
                command.getOrdreEnseignementId(), command.getTypeEnseignementId(), command.getCode(),
                command.getLibelle(), command.getLibelleEn(), command.getDescription(),
                command.getDateEntreeVigueur(), command.getUtilisateurId());
        Filiere sauvegarde = repository.sauvegarder(entite);

        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_FILIERES, allEntries = true)
    public Filiere modifier(ModifierFiliereCommand command) {
        Filiere entite = repository.trouverParId(command.getId())
                .orElseThrow(EntiteIntrouvableException::new);
        Filiere avant = copier(entite);

        entite.setLibelle(command.getLibelle());
        entite.setLibelleEn(command.getLibelleEn());
        entite.setDescription(command.getDescription());
        entite.setModifiePar(command.getUtilisateurId());

        Filiere sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "MODIFICATION",
                command.getUtilisateurId(), null, avant, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_FILIERES, allEntries = true)
    public Filiere deprecier(DeprecierCommand command) {
        Filiere entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        Filiere modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConfig.CACHE_FILIERES, allEntries = true)
    public Filiere reactiver(ReactiverCommand command) {
        Filiere entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        Filiere modifiee = executerReactivation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_FILIERES, key = "#id")
    public Filiere consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    @Cacheable(value = CacheConfig.CACHE_FILIERES, key = "#etat")
    public List<Filiere> rechercher(EtatReferentiel etat) {
        return repository.rechercher(etat);
    }

    @Override
    protected Filiere copier(Filiere source) {
        Filiere copie = new Filiere(
                source.getOrdreEnseignementId(), source.getTypeEnseignementId(), source.getCode(),
                source.getLibelle(), source.getLibelleEn(), source.getDescription(),
                source.getDateEntreeVigueur(), source.getCreePar());
        copie.setId(source.getId());
        copie.setModifiePar(source.getModifiePar());
        return copie;
    }
}
