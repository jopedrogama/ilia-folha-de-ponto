package br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BatidaResponseDTO {

    private LocalDate dia;
    private List<HorarioResponseListDTO> horarios;
}
