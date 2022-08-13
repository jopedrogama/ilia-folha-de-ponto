package br.com.ilia.digital.folhadeponto.Models;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatidaModel {

    private LocalDate dia;
    private LocalTime horario;
}
