package br.com.ilia.digital.folhadeponto.DTOs.Mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.ilia.digital.folhadeponto.DTOs.AlocacaoDTOs.AlocacaoDTO;
import br.com.ilia.digital.folhadeponto.Models.AlocacaoModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;

@Component
public class AlocacaoMapper {

    public AlocacaoModel toModel(AlocacaoDTO alocacaoDTO) {
        return AlocacaoModel.builder()
                .dia(new DiaModel(alocacaoDTO.getDia()))
                .projeto(alocacaoDTO.getNomeProjeto())
                .duracao(alocacaoDTO.getTempo())
                .build();
    }

    public AlocacaoDTO toDTO(AlocacaoModel alocacaoModel) {
        return AlocacaoDTO.builder()
                .nomeProjeto(alocacaoModel.getProjeto())
                .tempo(alocacaoModel.getDuracao())
                .build();

    }

    public List<AlocacaoDTO> listToDTo(List<AlocacaoModel> alocacaoList) {
        return alocacaoList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
