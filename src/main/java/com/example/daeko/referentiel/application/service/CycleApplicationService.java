package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerCycleCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierCycleCommand;
import com.example.daeko.referentiel.application.port.in.CycleUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.Cycle;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.port.CycleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CycleApplicationService
        extends AbstractReferentielApplicationService<Cycle>
        implements CycleUseCase {

    private static final String TYPE_ENTITE = "Cycle";

    private final CycleRepository repository;

    public CycleApplicationService(CycleRepository repository,
                                   AuditPort auditPort,
                                   EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
    }

    @Override
    @Transactional
    public Cycle creer(CreerCycleCommand command) {
        if (repository.existeParCode(command.getCode())) {
            throw new ViolationUniciteException("Un cycle avec le code '"
                    + command.getCode() + "' existe déjà.");
        }
        Cycle entite = new Cycle(
                command.getOrdreEnseignementId(),
                command.getCode(),
                command.getLibelle(),
                command.getLibelleEn(),
                command.getDescription(),
                command.getDateEntreeVigueur(),
                command.getUtilisateurId());

        Cycle sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    public Cycle modifier(ModifierCycleCommand command) {
        Cycle entite = repository.trouverParId(command.getId())
                .orElseThrow(EntiteIntrouvableException::new);
        Cycle avant = copier(entite);
        entite.setLibelle(command.getLibelle());
        entite.setLibelleEn(command.getLibelleEn());
        entite.setDescription(command.getDescription());

        Cycle sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "MODIFICATION",
                command.getUtilisateurId(), avant, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    public Cycle deprecier(DeprecierCommand command) {
        Cycle entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        Cycle modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    public Cycle consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    public List<Cycle> rechercher(EtatReferentiel etat) {
        return repository.rechercher(etat);
    }

    @Override
    protected Cycle copier(Cycle source) {
        Cycle copie = new Cycle(
                source.getOrdreEnseignementId(),
                source.getCode(),
                source.getLibelle(),
                source.getLibelleEn(),
                source.getDescription(),
                source.getDateEntreeVigueur(),
                null);
        copie.setId(source.getId());
        return copie;
    }
}