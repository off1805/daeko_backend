package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChampModifieResponse {
    private String champ;
    private Object valeurAvant;
    private Object valeurApres;
}