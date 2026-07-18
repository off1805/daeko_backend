package com.example.daeko.referentiel.infrastructure.adapter.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErreurResponse {

    private String code;
    private String message;
    private Map<String, Object> details;

    public ErreurResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
