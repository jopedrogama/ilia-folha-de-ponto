package br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BatidaRequestDTO {

    @NotNull(message = "Campo obrigatório não informado")
    private LocalDateTime dataHora;
}
