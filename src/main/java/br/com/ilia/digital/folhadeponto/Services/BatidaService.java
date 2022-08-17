package br.com.ilia.digital.folhadeponto.Services;

import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaResponseDTO;
import br.com.ilia.digital.folhadeponto.DTOs.Mappers.HorarioMapper;
import br.com.ilia.digital.folhadeponto.Exception.DomainException;
import br.com.ilia.digital.folhadeponto.Models.BatidaModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;
import br.com.ilia.digital.folhadeponto.Repositories.DiaRepository;
import br.com.ilia.digital.folhadeponto.Repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatidaService {

    private DiaRepository diaRepository;
    private HorarioRepository horarioRepository;

    @Autowired
    private HorarioMapper horarioMapper;

    @Autowired
    public BatidaService(DiaRepository diaRepository, HorarioRepository horarioRepository) {
        this.diaRepository = diaRepository;
        this.horarioRepository = horarioRepository;
    }

    @Transactional
    public BatidaResponseDTO baterPonto(BatidaModel batida) {

        if (isFinalDeSemana(batida.getDia())) {
            throw new DomainException("Sábado e domingo não são permitidos como dia de trabalho",
                    HttpStatus.FORBIDDEN);
        }

        DiaModel diaCriado = this.diaRepository.findByData(batida.getDia());

        HorarioModel horario = HorarioModel.builder()
                .horario(batida.getHorario())
                .diaModel(diaCriado)
                .build();

        List<HorarioModel> horarioList = new ArrayList<>();

        if (diaCriado == null) {
            DiaModel diaModel = new DiaModel();
            diaModel.setData(batida.getDia());
            diaCriado = diaRepository.save(diaModel);

            horario.setDiaModel(diaModel);

        } else {
            horarioList = horarioRepository.findBydiaModelOrderByHorario(diaCriado);

            if (horarioList.size() >= 4) {
                throw new DomainException("Apenas 4 horários podem ser registrados por dia", HttpStatus.FORBIDDEN);
            }

            if (horarioList.size() == 2) {
                if (isMenosQue1hora(horarioList.get(1).getHorario(), batida.getHorario())) {
                    throw new DomainException("Deve haver no mínimo 1 hora de almoço", HttpStatus.FORBIDDEN);
                }
            }

            List<HorarioModel> existeMesmoHorario = horarioList.stream()
                    .filter(element -> element.getHorario().equals(batida.getHorario()))
                    .collect(Collectors.toList());

            if (!existeMesmoHorario.isEmpty()) {
                throw new DomainException("Horários já registrado", HttpStatus.CONFLICT);
            }
        }

        horarioRepository.save(horario);
        horarioList.add(horario);

        return BatidaResponseDTO.builder()
                .horarios(horarioMapper.toDTOList(horarioList))
                .dia(diaCriado.getData())
                .build();
    }

    private boolean isMenosQue1hora(LocalTime horarioInicial, LocalTime horarioFinal) {
        Duration tempo = Duration.between(horarioInicial, horarioFinal);
        return tempo.toHours() < 1 ? true : false;
    }

    private boolean isFinalDeSemana(LocalDate dia) {
        DayOfWeek diaDaSemana = dia.getDayOfWeek();
        return (diaDaSemana == DayOfWeek.SATURDAY || diaDaSemana == DayOfWeek.SUNDAY) ? true : false;
    }
}
