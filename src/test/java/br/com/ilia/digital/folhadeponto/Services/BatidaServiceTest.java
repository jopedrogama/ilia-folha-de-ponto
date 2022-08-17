package br.com.ilia.digital.folhadeponto.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ilia.digital.folhadeponto.Exception.DomainException;
import br.com.ilia.digital.folhadeponto.Models.BatidaModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;
import br.com.ilia.digital.folhadeponto.Repositories.DiaRepository;
import br.com.ilia.digital.folhadeponto.Repositories.HorarioRepository;

class BatidaServiceTest {

	private BatidaService batidaService;

	@Mock
	private DiaRepository diaRepository;
	@Mock
	private HorarioRepository horarioRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		batidaService = new BatidaService(diaRepository, horarioRepository);
	}

	@Test
	void baterPonto_NaoPodeFinalDeSemana_Exception() {

		BatidaModel diaHorario = BatidaModel.builder()
				.dia(LocalDate.of(2022, 8, 14))
				.horario(LocalTime.of(12, 32, 15))
				.build();

		Exception ex = assertThrows(DomainException.class, () -> {
			batidaService.baterPonto(diaHorario);
		});

		assertEquals(ex.getMessage(), "Sábado e domingo não são permitidos como dia de trabalho");
	}

	@Test
	void baterPonto_MenosQueUmaHoraDeAlmoco() {
		BatidaModel diaHorario = BatidaModel.builder()
				.dia(LocalDate.of(2022, 8, 15))
				.horario(LocalTime.of(13, 32, 15))
				.build();

		when(horarioRepository.findBydiaModelOrderByHorario(any())).thenReturn(preencherHorarios(2));
		when(diaRepository.findByData(any())).thenReturn(new DiaModel());

		Exception ex = assertThrows(DomainException.class, () -> {
			batidaService.baterPonto(diaHorario);
		});

		assertEquals(ex.getMessage(), "Deve haver no mínimo 1 hora de almoço");

	}

	@Test
	void baterPonto_MaisDeQuatroRegistros() {
		BatidaModel diaHorario = BatidaModel.builder()
				.dia(LocalDate.of(2022, 8, 15))
				.horario(LocalTime.of(13, 32, 15))
				.build();

		when(horarioRepository.findBydiaModelOrderByHorario(any())).thenReturn(preencherHorarios(4));
		when(diaRepository.findByData(any())).thenReturn(new DiaModel());

		Exception ex = assertThrows(DomainException.class, () -> {
			batidaService.baterPonto(diaHorario);
		});

		assertEquals(ex.getMessage(), "Apenas 4 horários podem ser registrados por dia");

	}

	@Test
	void baterPonto_HorarioJaRegistrado() {
		BatidaModel diaHorario = BatidaModel.builder()
				.dia(LocalDate.of(2022, 8, 15))
				.horario(LocalTime.of(8, 01, 10))
				.build();

		when(horarioRepository.findBydiaModelOrderByHorario(any())).thenReturn(preencherHorarios(1));
		when(diaRepository.findByData(any())).thenReturn(new DiaModel());

		Exception ex = assertThrows(DomainException.class, () -> {
			batidaService.baterPonto(diaHorario);
		});

		assertEquals(ex.getMessage(), "Horários já registrado");

	}

	private List<HorarioModel> preencherHorarios(int quantidade) {
		List<HorarioModel> listaHorarios = new ArrayList<>();
		DiaModel diaModelo = new DiaModel();
		diaModelo.setId(1l);
		diaModelo.setData(LocalDate.of(2022, 8, 15));

		HorarioModel horario1 = HorarioModel.builder()
				.diaModel(diaModelo)
				.horario(LocalTime.of(8, 01, 10))
				.build();

		HorarioModel horario2 = HorarioModel.builder()
				.diaModel(diaModelo)
				.horario(LocalTime.of(12, 32, 16))
				.build();

		HorarioModel horario3 = HorarioModel.builder()
				.diaModel(diaModelo)
				.horario(LocalTime.of(14, 35, 25))
				.build();

		HorarioModel horario4 = HorarioModel.builder()
				.diaModel(diaModelo)
				.horario(LocalTime.of(18, 22, 56))
				.build();

		if (quantidade == 1) {
			Collections.addAll(listaHorarios, horario1);
		}
		if (quantidade == 2) {
			Collections.addAll(listaHorarios, horario1, horario2);
		}
		if (quantidade == 4) {
			Collections.addAll(listaHorarios, horario1, horario2, horario3, horario4);
		}

		return listaHorarios;
	}
}
