package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerMatiereCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ModifierMatiereCommand;
import com.example.daeko.referentiel.application.port.in.MatiereReferentielUseCase;
import com.example.daeko.referentiel.application.port.in.MatiereReferentielUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.EtatReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentiel;
import com.example.daeko.referentiel.domain.port.MatiereReferentielRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public abstract class MatiereReferentielApplicationService
        extends AbstractReferentielApplicationService<MatiereReferentiel>
        implements MatiereReferentielUseCase {

    private static final String TYPE_ENTITE = "MatiereReferentiel";

    private final MatiereReferentielRepository repository;

    public MatiereReferentielApplicationService(MatiereReferentielRepository repository,
                                                AuditPort auditPort,
                                                EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
    }

    @Override
    @Transactional
    public MatiereReferentiel creer(CreerMatiereCommand command) {
        if (repository.existeParCode(command.getSousSystemeId(), command.getCode())) {
            throw new ViolationUniciteException("Une matière avec le code '"
                    + command.getCode() + "' existe déjà pour ce sous-système.");
        }

        MatiereReferentiel entite = new MatiereReferentiel(
                command.getSousSystemeId(),
                command.getCode(),
                command.getLibelle(),
                command.getLibelleCourt(),
                command.getLibelleEn(),
                command.getDomaine(),
                command.getTypeMatiere(),
                command.getBaremeParDefaut(),
                command.getDescription(),
                command.getDateEntreeVigueur()
        );

        MatiereReferentiel sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    public MatiereReferentiel modifier(ModifierMatiereCommand command) {
        MatiereReferentiel entite = repository.trouverParId(command.getId())
                .orElseThrow(EntiteIntrouvableException::new);
        MatiereReferentiel avant = copier(entite);
        entite.setLibelle(command.getLibelle());
        entite.setLibelleEn(command.getLibelleEn());
        entite.setDescription(command.getDescription());
        MatiereReferentiel sauvegarde = repository.sauvegarder(entite);
        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "MODIFICATION",
                command.getUtilisateurId(), avant, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    public MatiereReferentiel deprecier(DeprecierCommand command) {
        MatiereReferentiel entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        MatiereReferentiel modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    public MatiereReferentiel consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    public List<MatiereReferentiel> rechercher(EtatReferentiel etat) {
        return repository.rechercher(etat);
    }

    @Override
    protected MatiereReferentiel copier(MatiereReferentiel source) {
        MatiereReferentiel copie = new MatiereReferentiel(
                source.getSousSystemeId(),
                source.getCode(),
                source.getLibelle(),
                source.getLibelleCourt(),
                source.getLibelleEn(),
                source.getDomaine(),
                source.getTypeMatiere(),
                source.getBaremeParDefaut(),
                source.getDescription(),
                source.getDateEntreeVigueur()
        );
        copie.setId(source.getId());
        return copie;
    }
}