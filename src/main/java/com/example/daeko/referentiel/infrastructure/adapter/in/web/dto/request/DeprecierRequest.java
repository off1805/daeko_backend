package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeprecierRequest {

    @NotBlank
    private String motif;

    private LocalDate dateEffet;
}
