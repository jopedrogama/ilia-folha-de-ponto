package br.com.ilia.digital.folhadeponto.Repositories;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ilia.digital.folhadeponto.Models.AlocacaoModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;

@Repository
public interface AlocacoesRepository extends JpaRepository<AlocacaoModel, Long> {

    List<AlocacaoModel> findBydia(@NotNull DiaModel diaModel);

}
