package com.example.daeko.structure_pedagogique.domain.exception;

/**
 * Catalogue des codes d'erreur metier du module Structure Pedagogique
 * (section 4.1 du dossier d'implementation STP-IMPL-1.0).
 */
public enum CodeErreurStructure {

    SP_001("Branche dupliquee : meme triplet (sous-systeme, ordre, type) deja declare par cet etablissement.", 409),
    SP_002("Sous-systeme incoherent : le niveau (via son cycle) ou la matiere du referentiel n'appartient pas au sous-systeme de la branche.", 422),
    SP_003("Ordre incoherent : le cycle du niveau, ou la filiere (de la serie), ne porte pas l'ordre d'enseignement de la branche.", 422),
    SP_004("Type incoherent : la filiere (activee ou portee par une serie) ne correspond pas au type d'enseignement de la branche.", 422),
    SP_005("Serie activee sur un niveau anterieur a son niveau d'apparition officiel.", 422),
    SP_006("Serie activee alors que sa filiere n'est pas dans les filieres actives de la configuration.", 422),
    SP_007("Element rattache a un niveau non active dans cette configuration.", 422),
    SP_008("Polymorphisme viole : MatiereActive sans source ou avec deux sources.", 422),
    SP_009("Matiere locale d'une autre branche referencee dans cette configuration.", 422),
    SP_010("Coefficient invalide (nul, negatif, ou hors format).", 422),
    SP_011("Doublon d'activation (niveau, serie ou matiere deja active dans le meme perimetre).", 409),
    SP_012("Cycle de vie d'annee invalide : deuxieme annee EN_COURS, ou chevauchement de dates avec une annee existante du meme etablissement.", 422),
    SP_013("Transition d'etat interdite (branche ou annee).", 422),
    SP_014("Configuration SCELLEE : toute ecriture est refusee.", 422),
    SP_015("Duplication impossible : aucune configuration source pour cette branche sur l'annee precedente demandee.", 422),
    SP_016("Classe dupliquee : suffixe deja utilise pour ce niveau/serie dans cette configuration.", 409),
    SP_017("Classe incoherente : serie non activee pour ce niveau, ou serie exigee par le niveau (second cycle) absente.", 422),
    SP_018("Branche non ACTIVE : impossible de creer une configuration sur une branche EN_CONFIGURATION, SUSPENDUE ou ARCHIVEE.", 422),
    SP_019("Modification d'une reference structurelle immuable.", 422),
    SP_020("Activation d'une entree DEPRECATED du referentiel dans une nouvelle configuration.", 422),
    SP_021("Etablissement SUSPENDU ou ARCHIVE : toute ecriture du module est refusee.", 422),
    SP_022("Coefficient verrouille : modification refusee quand l'annee est EN_COURS (hors flux de deverrouillage) ou CLOTUREE.", 422),
    SP_023("Permission insuffisante (structure.configurer requise ; scoping tenant verifie).", 403),
    SP_024("Ressource introuvable dans le perimetre de l'etablissement.", 404);

    private final String descriptionDefaut;
    private final int statutHttp;

    CodeErreurStructure(String descriptionDefaut, int statutHttp) {
        this.descriptionDefaut = descriptionDefaut;
        this.statutHttp = statutHttp;
    }

    public String getDescriptionDefaut() {
        return descriptionDefaut;
    }

    public int getStatutHttp() {
        return statutHttp;
    }
}
