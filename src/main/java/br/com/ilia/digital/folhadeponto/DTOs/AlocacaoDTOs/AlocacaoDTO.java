package br.com.ilia.digital.folhadeponto.DTOs.AlocacaoDTOs;

import java.time.Duration;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlocacaoDTO {

    @JsonInclude(Include.NON_NULL)
    private LocalDate dia;

    private String nomeProjeto;
    private Duration tempo;
}
