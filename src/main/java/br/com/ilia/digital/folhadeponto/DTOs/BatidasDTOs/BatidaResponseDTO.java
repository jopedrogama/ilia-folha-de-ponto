package br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BatidaResponseDTO {

    private LocalDate dia;
    private HorariosResponseListDTO horarios;
}
