package br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BatidaRequestDTO {

    // TODO validação do Not Null não está sendo aplicada. Verificar configurações
    // padrões
    @NotEmpty(message = "Campo obrigatório não informado")
    private LocalDateTime dataHora;
}
