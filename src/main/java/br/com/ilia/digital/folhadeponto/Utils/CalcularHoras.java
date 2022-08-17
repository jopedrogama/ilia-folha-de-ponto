package br.com.ilia.digital.folhadeponto.Utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import br.com.ilia.digital.folhadeponto.Exception.DomainException;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;

public class CalcularHoras {

    final static Duration CARGA_MAXIMA = Duration.ofMinutes(10560l); // 44h por 4 semanas

    public static Duration calcularHorasTrabalhadas(List<HorarioModel> listHorarios) {

        // Apesar dessa validação não estar registrada no swagger, foi importante
        // para garantir que não haverá um horario incompleto (sem horario final dessa
        // jornada)
        if (listHorarios.size() % 2 != 0) {
            ArrayList<String> horariosArrayList = new ArrayList<>();

            listHorarios.forEach((horario) -> {
                horariosArrayList.add(horario.getHorario().toString());
            });

            String horariosMensagem = horariosArrayList.toString();

            String mensagemDeExcecao = String.format(
                    "Você não registrou horário de saída. Os horarios registrados para esse dia são: %s",
                    horariosMensagem);
            throw new DomainException(mensagemDeExcecao, HttpStatus.BAD_REQUEST);
        }

        Duration intervalo1 = Duration.between(listHorarios.get(0).getHorario(), listHorarios.get(1).getHorario());
        Duration intervalo2 = listHorarios.size() > 2
                ? Duration.between(listHorarios.get(2).getHorario(), listHorarios.get(3).getHorario())
                : Duration.ZERO;

        return intervalo1.plus(intervalo2);
    }

    public static Duration calcularBancoDeHorasTrabalhadas(List<List<HorarioModel>> horarios) {
        Duration totalTrabalhado = Duration.ZERO;

        horarios.stream().forEach((listaDeHorarios) -> {
            totalTrabalhado.plus(calcularHorasTrabalhadas(listaDeHorarios));
        });

        return totalTrabalhado;

    }

    public static Duration calcularHorasExcedentes(Duration horasTrabalhadas) {
        Duration value = horasTrabalhadas.minus(CARGA_MAXIMA);
        return value.isNegative() ? Duration.ZERO : value;
    }

    public static Duration calcularHorasDevidas(Duration horasTrabalhadas) {
        Duration value = CARGA_MAXIMA.minus(horasTrabalhadas);
        return value.isNegative() ? Duration.ZERO : value;
    }
}
