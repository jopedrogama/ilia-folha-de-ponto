package br.com.ilia.digital.folhadeponto.Services;

import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ilia.digital.Utils.CalcularHorasTrabalhadas;
import br.com.ilia.digital.folhadeponto.DTOs.FolhaDePontoDTO.RelatorioMensalDTO;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Repositories.AlocacoesRepository;
import br.com.ilia.digital.folhadeponto.Repositories.DiaRepository;
import br.com.ilia.digital.folhadeponto.Repositories.HorarioRepository;

@Service
public class FolhaDePontoService {

    private AlocacoesRepository alocacoesRepository;
    private DiaRepository diaRepository;
    private HorarioRepository horarioRepository;

    @Autowired
    public FolhaDePontoService(AlocacoesRepository alocacoesRepository, DiaRepository diaRepository,
            HorarioRepository horarioRepository, AlocacoesService alocacoesService) {
        this.alocacoesRepository = alocacoesRepository;
        this.diaRepository = diaRepository;
        this.horarioRepository = horarioRepository;
    }

    public RelatorioMensalDTO gerarFolhaDePonto(YearMonth mesAno) {
        List<DiaModel> diasComRegistro = diaRepository.findAllByYearMonth(mesAno.getYear(), mesAno.getMonthValue());
        // CalcularHorasTrabalhadas.calcularHorasTrabalhadas(listHorarios);
        return null;
    }
}
