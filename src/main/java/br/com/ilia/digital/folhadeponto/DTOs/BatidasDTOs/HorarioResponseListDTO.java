package br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HorarioResponseListDTO {

    private LocalTime horario;
}
