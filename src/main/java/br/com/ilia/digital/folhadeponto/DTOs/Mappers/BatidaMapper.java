package br.com.ilia.digital.folhadeponto.DTOs.Mappers;

import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaRequestDTO;
import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaResponseDTO;
import br.com.ilia.digital.folhadeponto.Models.BatidaModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatidaMapper {

    @Autowired
    private HorarioMapper horarioMapper;

    public BatidaModel toModel(BatidaRequestDTO batidaRequestDTO) {

        return BatidaModel.builder()
                .dia(batidaRequestDTO.getDataHora().toLocalDate())
                .horario(batidaRequestDTO.getDataHora().toLocalTime())
                .build();
    }

    public BatidaResponseDTO toDTO(DiaModel diaCriado, List<HorarioModel> horarioList) {
        return BatidaResponseDTO.builder()
                .horarios(horarioMapper.toDTOList(horarioList))
                .dia(diaCriado.getData())
                .build();
    }

    public List<BatidaResponseDTO> toListDTO(List<DiaModel> diaList, List<HorarioModel> horarioList) {
        List<BatidaResponseDTO> response = new ArrayList<>();

        diaList.forEach((dia) -> {
            List<HorarioModel> horariosDoDia = horarioList.stream()
                    .filter((horario) -> horario.getDiaModel().getId() == dia.getId()).collect(Collectors.toList());

            response.add(toDTO(dia, horariosDoDia));
        });

        return response;
    }
}
