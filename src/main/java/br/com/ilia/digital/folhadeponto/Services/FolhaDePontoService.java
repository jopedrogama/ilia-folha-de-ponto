package br.com.ilia.digital.folhadeponto.Services;

import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.ilia.digital.folhadeponto.DTOs.FolhaDePontoDTO.RelatorioMensalDTO;
import br.com.ilia.digital.folhadeponto.Exception.DomainException;
import br.com.ilia.digital.folhadeponto.Models.AlocacaoModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;
import br.com.ilia.digital.folhadeponto.Repositories.AlocacoesRepository;
import br.com.ilia.digital.folhadeponto.Repositories.DiaRepository;
import br.com.ilia.digital.folhadeponto.Repositories.HorarioRepository;
import br.com.ilia.digital.folhadeponto.Utils.FolhaDePontoBuilder;

@Service
public class FolhaDePontoService {

    private AlocacoesRepository alocacoesRepository;
    private DiaRepository diaRepository;
    private HorarioRepository horarioRepository;
    private FolhaDePontoBuilder folhaDePontoBuilder;

    @Autowired
    public FolhaDePontoService(AlocacoesRepository alocacoesRepository, DiaRepository diaRepository,
            HorarioRepository horarioRepository, AlocacoesService alocacoesService,
            FolhaDePontoBuilder folhaDePontoBuilder) {
        this.alocacoesRepository = alocacoesRepository;
        this.diaRepository = diaRepository;
        this.horarioRepository = horarioRepository;
        this.folhaDePontoBuilder = folhaDePontoBuilder;
    }

    public RelatorioMensalDTO gerarFolhaDePonto(YearMonth mesAno) {
        try {
            List<DiaModel> diaModelList = diaRepository.findAllByYearMonth(mesAno.getYear(),
                    mesAno.getMonthValue());

            List<HorarioModel> horarioList = horarioRepository.findAllnoDiaMes(diaModelList);

            List<AlocacaoModel> alocacaoList = alocacoesRepository.findAllnoDiaMes(diaModelList);

            return folhaDePontoBuilder.emitirRelatorio(mesAno, horarioList, diaModelList, alocacaoList);

        } catch (IndexOutOfBoundsException ex) {
            throw new DomainException("Não há registros para esse mês, não foi possível gerar um relatório",
                    HttpStatus.BAD_REQUEST);
        }

    }
}
