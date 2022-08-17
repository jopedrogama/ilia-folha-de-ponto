package br.com.ilia.digital.folhadeponto.Utils;

import java.time.Duration;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ilia.digital.folhadeponto.DTOs.AlocacaoDTOs.AlocacaoDTO;
import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaResponseDTO;
import br.com.ilia.digital.folhadeponto.DTOs.FolhaDePontoDTO.RelatorioMensalDTO;
import br.com.ilia.digital.folhadeponto.DTOs.Mappers.AlocacaoMapper;
import br.com.ilia.digital.folhadeponto.DTOs.Mappers.BatidaMapper;
import br.com.ilia.digital.folhadeponto.Models.AlocacaoModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;

@Component
public class FolhaDePontoBuilder {

    @Autowired
    private AlocacaoMapper alocacaoMapper;
    @Autowired
    private BatidaMapper batidaMapper;

    public RelatorioMensalDTO emitirRelatorio(YearMonth mesAno, List<HorarioModel> horarioList,
            List<DiaModel> diaModelList,
            List<AlocacaoModel> alocacaoList) {

        Duration horasTrabalhadas = CalcularHoras.calcularHorasTrabalhadas(horarioList);
        Duration horasExcedentes = CalcularHoras.calcularHorasExcedentes(horasTrabalhadas);
        Duration horasDevidas = CalcularHoras.calcularHorasDevidas(horasTrabalhadas);

        List<AlocacaoDTO> alocacao = alocacaoMapper.listToDTo(alocacaoList);
        List<BatidaResponseDTO> registros = batidaMapper.toListDTO(diaModelList, horarioList);

        return RelatorioMensalDTO.builder()
                .mes(mesAno)
                .horasTrabalhadas(horasTrabalhadas)
                .horasDevidas(horasDevidas)
                .horasExcedentes(horasExcedentes)
                .registros(registros)
                .alocacoes(alocacao)
                .build();
    }
}
