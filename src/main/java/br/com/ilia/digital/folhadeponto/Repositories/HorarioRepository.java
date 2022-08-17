package br.com.ilia.digital.folhadeponto.Repositories;

import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<HorarioModel, Long> {

    List<HorarioModel> findBydiaModelOrderByHorario(DiaModel diaModel);

    @Query("SELECT h FROM HorarioModel h WHERE h.diaModel IN :diaModel")
    List<HorarioModel> findAllnoDiaMes(@Param("diaModel") List<DiaModel> diaModel);
}
