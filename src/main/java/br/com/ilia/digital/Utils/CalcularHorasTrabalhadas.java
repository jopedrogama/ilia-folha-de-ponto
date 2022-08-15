package br.com.ilia.digital.Utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import br.com.ilia.digital.folhadeponto.Exception.DomainException;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;

public class CalcularHorasTrabalhadas {

    public static Duration calcularHorasTrabalhadas(List<HorarioModel> listHorarios) {

        // TODO Apesar dessa validação não estar registrada no swagger, foi importante
        // para garantir que não haverá um horario incompleto (sem horario final dessa
        // jornada)
        if (listHorarios.size() % 2 != 0) {
            ArrayList<String> horariosArrayList = new ArrayList<>();

            listHorarios.forEach((horario) -> {
                horariosArrayList.add(horario.getHorario().toString());
            });

            String horariosMensagem = horariosArrayList.toString();

            String mensagemDeExcecao = String.format(
                    "Você não registrou horário de saída. Os horarios registrados para esse dia são: %sa",
                    horariosMensagem);
            throw new DomainException(mensagemDeExcecao, HttpStatus.BAD_REQUEST);
        }

        Duration intervalo1 = Duration.between(listHorarios.get(0).getHorario(), listHorarios.get(1).getHorario());
        Duration intervalo2 = listHorarios.size() > 2
                ? Duration.between(listHorarios.get(0).getHorario(), listHorarios.get(1).getHorario())
                : Duration.ZERO;

        return intervalo1.plus(intervalo2);
    }

    public static Duration calcularHorasTrabalhadasMes(List<List<HorarioModel>> listaDeHorarios) {

        return Duration.ZERO;
    }

    public static Duration calcularHorasExcedentes() {
        return Duration.ZERO;
    }

    public static Duration calcularHorasDevidas() {
        return Duration.ZERO;
    }
}
