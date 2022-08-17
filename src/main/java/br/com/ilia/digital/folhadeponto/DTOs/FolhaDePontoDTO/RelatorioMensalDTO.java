package br.com.ilia.digital.folhadeponto.DTOs.FolhaDePontoDTO;

import java.time.Duration;
import java.time.YearMonth;
import java.util.List;

import br.com.ilia.digital.folhadeponto.DTOs.AlocacaoDTOs.AlocacaoDTO;
import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelatorioMensalDTO {

    private YearMonth mes;
    private Duration horasTrabalhadas;
    private Duration horasExcedentes;
    private Duration horasDevidas;
    private List<BatidaResponseDTO> registros;
    private List<AlocacaoDTO> alocacoes;

}
