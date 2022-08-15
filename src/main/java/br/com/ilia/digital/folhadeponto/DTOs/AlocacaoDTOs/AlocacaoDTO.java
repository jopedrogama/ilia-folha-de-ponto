package br.com.ilia.digital.folhadeponto.DTOs.AlocacaoDTOs;

import java.time.Duration;
import java.time.LocalDate;

import lombok.Data;

@Data
public class AlocacaoDTO {

    private LocalDate dia;
    private Duration tempo;
    private String nomeProjeto;
}
