package br.com.ilia.digital.folhadeponto.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ilia.digital.folhadeponto.Models.AlocacaoModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;

@Repository
public interface AlocacoesRepository extends JpaRepository<AlocacaoModel, Long> {

    List<AlocacaoModel> findBydia(DiaModel diaModel);

    @Query("SELECT a FROM AlocacaoModel a WHERE a.dia IN :diaModel")
    List<AlocacaoModel> findAllnoDiaMes(@Param("diaModel") List<DiaModel> diaModel);

}
