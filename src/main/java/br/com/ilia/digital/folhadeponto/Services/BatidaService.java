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

        // A decisão de projeto que levou a deixar os repositorios de dia, e de alocação
        // separados,
        // foi o fato de pegar o dia do horario (id) e não precisar usar query em banco
        // passando string, datatime,
        // ou outros formatos que iriam honerar mais o banco.

        // Outro fator, é que na hora de regitrar o projeto em que se está trabalhando,
        // ajuda a indexar
        // a carga horaria ao dia, e nao ao periodo. Pois dentro de um periodo de 5h,
        // uma pessoa pode ter
        // dedicado 3h ao projeto XPTO e 2h ao projeto ABC

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
            horarioList = horarioRepository.findBydiaModel(diaCriado);

            if (horarioList.size() >= 4) {
                throw new DomainException("Apenas 4 horários podem ser registrados por dia", HttpStatus.FORBIDDEN);
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
}
