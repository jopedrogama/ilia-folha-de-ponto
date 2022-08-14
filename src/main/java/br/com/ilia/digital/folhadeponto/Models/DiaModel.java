package br.com.ilia.digital.folhadeponto.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class DiaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    @NotNull
    private LocalDate data;

    public DiaModel(LocalDate dia) {
        this.data = dia;
    }

    public DiaModel() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DiaModel diaModel = (DiaModel) o;
        return id != null && Objects.equals(id, diaModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
