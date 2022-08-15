package br.com.ilia.digital.folhadeponto.DTOs.Mappers;

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
}
