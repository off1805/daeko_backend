package com.example.daeko.structure_pedagogique.application.dto;

import java.util.List;

public final class ResultatActivation<T> {

    private final List<T> ajoutees;
    private final List<T> modifiees;
    private final List<T> inchangees;
    private final List<T> retirees;

    public ResultatActivation(List<T> ajoutees, List<T> modifiees, List<T> inchangees, List<T> retirees) {
        this.ajoutees = ajoutees;
        this.modifiees = modifiees;
        this.inchangees = inchangees;
        this.retirees = retirees;
    }

    public List<T> getAjoutees() { return ajoutees; }
    public List<T> getModifiees() { return modifiees; }
    public List<T> getInchangees() { return inchangees; }
    public List<T> getRetirees() { return retirees; }
}
