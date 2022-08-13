package br.com.ilia.digital.folhadeponto.DTOs.Mappers;

import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaRequestDTO;
import br.com.ilia.digital.folhadeponto.Models.BatidaModel;
import org.springframework.stereotype.Component;

@Component
public class BatidaMapper {

    public BatidaModel toModel(BatidaRequestDTO batidaRequestDTO) {

        return BatidaModel.builder()
                .dia(batidaRequestDTO.getDataHora().toLocalDate())
                .horario(batidaRequestDTO.getDataHora().toLocalTime())
                .build();
    }
}
