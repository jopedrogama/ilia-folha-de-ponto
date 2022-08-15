package br.com.ilia.digital.folhadeponto.Services;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.ilia.digital.Utils.CalcularHorasTrabalhadas;
import br.com.ilia.digital.folhadeponto.Exception.DomainException;
import br.com.ilia.digital.folhadeponto.Models.AlocacaoModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;
import br.com.ilia.digital.folhadeponto.Repositories.AlocacoesRepository;
import br.com.ilia.digital.folhadeponto.Repositories.DiaRepository;
import br.com.ilia.digital.folhadeponto.Repositories.HorarioRepository;

@Service
public class AlocacoesService {

    private AlocacoesRepository alocacoesRepository;
    private DiaRepository diaRepository;
    private HorarioRepository horarioRepository;

    @Autowired
    public AlocacoesService(AlocacoesRepository alocacoesRepository, DiaRepository diaRepository,
            HorarioRepository horarioRepository) {
        this.alocacoesRepository = alocacoesRepository;
        this.diaRepository = diaRepository;
        this.horarioRepository = horarioRepository;
    }

    public AlocacaoModel registrarAlocacao(AlocacaoModel alocacaoModel) {
        LocalDate queryDate = alocacaoModel.getDia().getData();
        DiaModel diaCriado = this.diaRepository.findByData(queryDate);

        if (diaCriado == null) {
            // TODO essa mensagem poderia ser mais intuiva, avisando que não há nenhum
            // horario registrado, porém iria fugir do escopo do projeto
            throw new DomainException("Não pode alocar tempo maior que o tempo trabalhado no dia",
                    HttpStatus.BAD_REQUEST);
        }

        alocacaoModel.setDia(diaCriado);

        List<HorarioModel> horarioList = horarioRepository.findBydiaModelOrderByHorario(alocacaoModel.getDia());

        Duration horasJaRegistradas = calcularHorasJaRegistradas(diaCriado);
        Duration totalDeHorasRestantes = CalcularHorasTrabalhadas.calcularHorasTrabalhadas(horarioList)
                .minus(horasJaRegistradas);

        if (totalDeHorasRestantes.compareTo(alocacaoModel.getDuracao()) < 0) {
            throw new DomainException("Não pode alocar tempo maior que o tempo trabalhado no dia",
                    HttpStatus.BAD_REQUEST);
        }

        return alocacoesRepository.save(alocacaoModel);
    }

    private Duration calcularHorasJaRegistradas(DiaModel diaModel) {
        List<AlocacaoModel> alocacoesList = alocacoesRepository.findBydia(diaModel);

        return alocacoesList.stream().map(AlocacaoModel::getDuracao).reduce(Duration.ZERO, Duration::plus);
    }
}
