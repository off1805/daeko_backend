package com.example.daeko.referentiel.application.service;

import com.example.daeko.referentiel.application.dto.CreerMatiereNiveauCommand;
import com.example.daeko.referentiel.application.dto.DeprecierCommand;
import com.example.daeko.referentiel.application.dto.ReactiverCommand;
import com.example.daeko.referentiel.application.port.in.MatiereNiveauUseCase;
import com.example.daeko.referentiel.application.port.out.AuditPort;
import com.example.daeko.referentiel.application.port.out.EvenementPublisherPort;
import com.example.daeko.referentiel.domain.exception.EntiteIntrouvableException;
import com.example.daeko.referentiel.domain.exception.ViolationUniciteException;
import com.example.daeko.referentiel.domain.model.MatiereReferentiel;
import com.example.daeko.referentiel.domain.model.MatiereReferentielNiveau;
import com.example.daeko.referentiel.domain.model.Niveau;
import com.example.daeko.referentiel.domain.model.Serie;
import com.example.daeko.referentiel.domain.port.MatiereReferentielNiveauRepository;
import com.example.daeko.referentiel.domain.port.MatiereReferentielRepository;
import com.example.daeko.referentiel.domain.port.NiveauRepository;
import com.example.daeko.referentiel.domain.port.SerieRepository;
import com.example.daeko.referentiel.domain.service.ReferentielValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MatiereNiveauApplicationService
        extends AbstractReferentielApplicationService<MatiereReferentielNiveau>
        implements MatiereNiveauUseCase {

    private static final String TYPE_ENTITE = "MatiereReferentielNiveau";

    private final MatiereReferentielNiveauRepository repository;
    private final MatiereReferentielRepository matiereRepository;
    private final NiveauRepository niveauRepository;
    private final SerieRepository serieRepository;
    private final ReferentielValidationService validationService;

    public MatiereNiveauApplicationService(MatiereReferentielNiveauRepository repository,
                                           MatiereReferentielRepository matiereRepository,
                                           NiveauRepository niveauRepository,
                                           SerieRepository serieRepository,
                                           ReferentielValidationService validationService,
                                           AuditPort auditPort,
                                           EvenementPublisherPort evenementPublisher) {
        super(auditPort, evenementPublisher);
        this.repository = repository;
        this.matiereRepository = matiereRepository;
        this.niveauRepository = niveauRepository;
        this.serieRepository = serieRepository;
        this.validationService = validationService;
    }

    /**
     * Crée une association matière-niveau, avec ou sans série.
     * Applique dans l'ordre : REF-006 (parents actifs), REF-002 (cohérence sous-système),
     * REF-003 + REF-004 (cohérence série si présente), unicité.
     */
    @Override
    @Transactional
    public MatiereReferentielNiveau creer(CreerMatiereNiveauCommand command) {
        MatiereReferentiel matiere = matiereRepository.trouverParId(command.getMatiereReferentielId())
                .orElseThrow(EntiteIntrouvableException::new);
        Niveau niveau = niveauRepository.trouverParId(command.getNiveauId())
                .orElseThrow(EntiteIntrouvableException::new);

        // REF-006 : parents actifs
        validationService.verifierParentActif(matiere);
        validationService.verifierParentActif(niveau);

        // REF-002 : cohérence sous-système
        validationService.verifierCoherenceSousSysteme(matiere, niveau);

        UUID serieId = command.getSerieId();

        // REF-003 + REF-004 : cohérence série si présente
        if (serieId != null) {
            Serie serie = serieRepository.trouverParId(serieId)
                    .orElseThrow(EntiteIntrouvableException::new);
            validationService.verifierParentActif(serie);
            validationService.verifierCoherenceAvecSerie(serie, niveau);

            if (repository.existeAvecSerie(command.getMatiereReferentielId(), command.getNiveauId(), serieId)) {
                throw new ViolationUniciteException(
                        "Cette association matière/niveau/série existe déjà.");
            }
        } else {
            if (repository.existeSansSerie(command.getMatiereReferentielId(), command.getNiveauId())) {
                throw new ViolationUniciteException(
                        "Cette association matière/niveau (sans série) existe déjà.");
            }
        }

        MatiereReferentielNiveau entite = new MatiereReferentielNiveau(
                command.getMatiereReferentielId(), command.getNiveauId(), serieId,
                command.isEstObligatoire(), command.getCoefficientSuggere(),
                command.getSourceCoefficient(), command.getBaremeSpecifique(),
                command.getDescription(), command.getDateEntreeVigueur(), command.getUtilisateurId());
        MatiereReferentielNiveau sauvegarde = repository.sauvegarder(entite);

        auditPort.enregistrer(TYPE_ENTITE, sauvegarde.getId(), "CREATION",
                command.getUtilisateurId(), null, null, sauvegarde);
        return sauvegarde;
    }

    @Override
    @Transactional
    public MatiereReferentielNiveau deprecier(DeprecierCommand command) {
        MatiereReferentielNiveau entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        MatiereReferentielNiveau modifiee = executerDepreciation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    @Transactional
    public MatiereReferentielNiveau reactiver(ReactiverCommand command) {
        MatiereReferentielNiveau entite = repository.trouverParId(command.getEntiteId())
                .orElseThrow(EntiteIntrouvableException::new);
        MatiereReferentielNiveau modifiee = executerReactivation(entite, TYPE_ENTITE, command);
        return repository.sauvegarder(modifiee);
    }

    @Override
    public MatiereReferentielNiveau consulterParId(UUID id) {
        return repository.trouverParId(id).orElseThrow(EntiteIntrouvableException::new);
    }

    @Override
    public List<MatiereReferentielNiveau> rechercher(UUID niveauId, UUID serieId) {
        return repository.rechercher(niveauId, serieId,
                com.example.daeko.referentiel.domain.model.EtatReferentiel.ACTIVE);
    }

    @Override
    protected MatiereReferentielNiveau copier(MatiereReferentielNiveau source) {
        MatiereReferentielNiveau copie = new MatiereReferentielNiveau(
                source.getMatiereReferentielId(), source.getNiveauId(), source.getSerieId(),
                source.isEstObligatoire(), source.getCoefficientSuggere(),
                source.getSourceCoefficient(), source.getBaremeSpecifique(),
                source.getDescription(), source.getDateEntreeVigueur(), source.getCreePar());
        copie.setId(source.getId());
        copie.setModifiePar(source.getModifiePar());
        return copie;
    }
}