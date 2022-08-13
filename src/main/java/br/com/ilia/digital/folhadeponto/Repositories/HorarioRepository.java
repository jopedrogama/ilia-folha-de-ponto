package br.com.ilia.digital.folhadeponto.Repositories;

import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<HorarioModel, Long> {

    List<HorarioModel> findBydiaModel(@NotNull DiaModel diaModel);
}
