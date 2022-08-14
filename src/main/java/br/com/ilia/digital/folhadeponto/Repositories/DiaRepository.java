package br.com.ilia.digital.folhadeponto.Repositories;

import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DiaRepository extends JpaRepository<DiaModel, Integer> {

    @Query("SELECT d FROM DiaModel d WHERE CAST(d.data AS date) = CAST(:diaPesquisado AS date)")
    DiaModel findByData(@Param("diaPesquisado") LocalDate diaPesquisado);
}
