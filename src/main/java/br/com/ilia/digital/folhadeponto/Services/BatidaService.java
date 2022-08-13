package br.com.ilia.digital.folhadeponto.Services;

import br.com.ilia.digital.folhadeponto.Models.BatidaModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;
import br.com.ilia.digital.folhadeponto.Repositories.DiaRepository;
import br.com.ilia.digital.folhadeponto.Repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatidaService {

    private DiaRepository diaRepository;
    private HorarioRepository horarioRepository;

    @Autowired
    public BatidaService(DiaRepository diaRepository, HorarioRepository horarioRepository) {
        this.diaRepository = diaRepository;
        this.horarioRepository = horarioRepository;
    }

    // TODO trocar o DTO para o model e criar os mappers para fazer a alteração
    // TODO No Mapper da conversão, verificar se o formato de data e horario está
    // errado
    public void baterPonto(BatidaModel batida) {

        // A decisão de projeto que levou a deixar os repositorios de dia, e de alocação
        // separados,
        // foi o fato de pegar o dia do horario (id) e não precisar usar query em banco
        // passando string, datatime,
        // ou outros formatos que iriam honerar mais o banco.

        // Outro fator, é que na hora de regitrar o projeto em que se está trabalhando,
        // ajuda a indexar
        // a carga horaria ao dia, e nao ao periodo. Pois dentro de um periodo de 5h,
        // uma pessoa pode ter
        // dedicado 3h ao projeto XPTO e 2h ao projeto ABC

        // TODO verifica se já tem 4 horarios para aquele dia

        DiaModel diaCriado = this.diaRepository.findByDay(batida.getDia());

        if (diaCriado == null){
            DiaModel diaModel = new DiaModel();
            diaModel.setData(batida.getDia());
            diaCriado = diaRepository.save(diaModel);
        }else{
            List<HorarioModel> horarioList = horarioRepository.findBydiaModel(diaCriado);

            if(horarioList.size() >= 4){
                throw new RuntimeException("Apenas 4 horários podem ser registrados por dia");
            }
        }



        // TODO verificar se o horario informado, já foi cadastrado anteriormente

        //TODO save horario
        HorarioModel horario = new HorarioModel();
        horario.setDiaModel(diaCriado);
        horario.setHorario(batida.getHorario());

        horarioRepository.save(horario);

        // TODO Converter para o DTO de response

    }
}
