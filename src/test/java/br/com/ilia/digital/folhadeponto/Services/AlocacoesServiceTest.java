package br.com.ilia.digital.folhadeponto.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ilia.digital.folhadeponto.Repositories.DiaRepository;
import br.com.ilia.digital.folhadeponto.Repositories.HorarioRepository;
import br.com.ilia.digital.folhadeponto.Exception.DomainException;
import br.com.ilia.digital.folhadeponto.Models.AlocacaoModel;
import br.com.ilia.digital.folhadeponto.Models.DiaModel;
import br.com.ilia.digital.folhadeponto.Models.HorarioModel;
import br.com.ilia.digital.folhadeponto.Repositories.AlocacoesRepository;

class AlocacoesServiceTest {

	private AlocacoesService alocacoesService;

	@Mock
	private DiaRepository diaRepository;
	@Mock
	private HorarioRepository horarioRepository;
	@Mock
	private AlocacoesRepository alocacoesRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		alocacoesService = new AlocacoesService(alocacoesRepository, diaRepository, horarioRepository);
	}

	@Test
	void registrarAlocacao_AlocarMaisHorasQueTrabalhadas() {
		AlocacaoModel alocacao = AlocacaoModel.builder()
				.duracao(Duration.parse("PT1H1M28S"))
				.dia(definirDia())
				.projeto("Projeto XPTO")
				.build();

		when(horarioRepository.findBydiaModelOrderByHorario(any())).thenReturn(preencherHorarios(4));
		when(alocacoesRepository.findBydia(any())).thenReturn(preencherAlocacoes());
		when(diaRepository.findByData(any())).thenReturn(new DiaModel());

		Exception ex = assertThrows(DomainException.class, () -> {
			alocacoesService.registrarAlocacao(alocacao);
		});

		assertEquals(ex.getMessage(), "Não pode alocar tempo maior que o tempo trabalhado no dia");
	}

	@Test
	void registrarAlocacao_AlocarQuantidadeExataDeHoras() {
		try {
			Duration duracaoCadastro = Duration.parse("PT1H1M27S");
			String nomeProjeto = "Projeto XPTO";

			AlocacaoModel alocacao = AlocacaoModel.builder()
					.duracao(duracaoCadastro)
					.dia(definirDia())
					.projeto(nomeProjeto)
					.build();

			when(horarioRepository.findBydiaModelOrderByHorario(any())).thenReturn(preencherHorarios(4));
			when(alocacoesRepository.findBydia(any())).thenReturn(preencherAlocacoes());
			when(diaRepository.findByData(any())).thenReturn(new DiaModel());

			alocacoesService.registrarAlocacao(alocacao);
		} catch (Exception e) {
			fail("Nao deveria soltar excecoes - Tempo dentro do permitido");
		}
	}

	private DiaModel definirDia() {
		DiaModel diaModelo = new DiaModel();
		diaModelo.setId(1l);
		diaModelo.setData(LocalDate.of(2022, 8, 15));
		return diaModelo;
	}

	private List<HorarioModel> preencherHorarios(int quantidade) {
		List<HorarioModel> listaHorarios = new ArrayList<>();

		HorarioModel horario1 = HorarioModel.builder()
				.diaModel(definirDia())
				.horario(LocalTime.of(8, 01, 10))
				.build();

		HorarioModel horario2 = HorarioModel.builder()
				.diaModel(definirDia())
				.horario(LocalTime.of(12, 32, 16))
				.build();

		HorarioModel horario3 = HorarioModel.builder()
				.diaModel(definirDia())
				.horario(LocalTime.of(14, 35, 25))
				.build();

		HorarioModel horario4 = HorarioModel.builder()
				.diaModel(definirDia())
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

		// Todas as batidas representam 8h 18min e 37s
		return listaHorarios;
	}

	private List<AlocacaoModel> preencherAlocacoes() {
		List<AlocacaoModel> listaAlocacoes = new ArrayList<>();

		AlocacaoModel alocacao1 = AlocacaoModel.builder()
				.dia(definirDia())
				.projeto("Projeto ABC")
				.duracao(Duration.parse("PT5H15M10S"))
				.build();

		AlocacaoModel alocacao2 = AlocacaoModel.builder()
				.dia(definirDia())
				.projeto("Projeto ACME")
				.duracao(Duration.parse("PT2H2M"))
				.build();

		Collections.addAll(listaAlocacoes, alocacao1, alocacao2);
		// Alocados 7h 17min e 10s
		return listaAlocacoes;
	}

}
