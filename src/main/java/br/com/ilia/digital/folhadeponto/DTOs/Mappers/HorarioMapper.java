package br.com.ilia.digital.folhadeponto.DTOs.Mappers;

import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.HorarioResponseListDTO;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HorarioMapper {

    public HorarioResponseListDTO toDTO(HorarioModel horarioModel){
        return HorarioResponseListDTO.builder()
                .horario(horarioModel.getHorario())
                .build();
    }

    public List<HorarioResponseListDTO> toDTOList(List<HorarioModel> horarioList){
        return horarioList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
