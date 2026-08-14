package com.example.daeko.structure_pedagogique.application.port.out;

import com.example.daeko.structure_pedagogique.domain.model.EvenementSortant;

public interface  EvenementPublisherPort {

    void publier(EvenementSortant evenement);
}
