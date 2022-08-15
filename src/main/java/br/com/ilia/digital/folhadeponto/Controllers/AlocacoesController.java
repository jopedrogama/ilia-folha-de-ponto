package br.com.ilia.digital.folhadeponto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ilia.digital.folhadeponto.DTOs.AlocacaoDTOs.AlocacaoDTO;
import br.com.ilia.digital.folhadeponto.DTOs.Mappers.AlocacaoMapper;
import br.com.ilia.digital.folhadeponto.Models.AlocacaoModel;
import br.com.ilia.digital.folhadeponto.Services.AlocacoesService;

@RestController
@RequestMapping("/v1/alocacoes")
public class AlocacoesController {

    private AlocacoesService alocacoesService;
    private AlocacaoMapper alocacaoMapper;

    @Autowired
    public AlocacoesController(AlocacoesService alocacoesService, AlocacaoMapper alocacaoMapper) {
        this.alocacoesService = alocacoesService;
        this.alocacaoMapper = alocacaoMapper;
    }

    @PostMapping
    public ResponseEntity<Object> alocarHoras(@RequestBody AlocacaoDTO alocacaoRequestDTO) {
        AlocacaoModel alocacaoModel = alocacaoMapper.toModel(alocacaoRequestDTO);
        alocacoesService.registrarAlocacao(alocacaoModel);

        return new ResponseEntity<Object>(alocacaoRequestDTO, HttpStatus.CREATED);
    }
}
