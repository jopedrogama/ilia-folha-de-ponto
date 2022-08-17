package br.com.ilia.digital.folhadeponto.Controllers;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ilia.digital.folhadeponto.DTOs.FolhaDePontoDTO.RelatorioMensalDTO;
import br.com.ilia.digital.folhadeponto.Services.FolhaDePontoService;

@RestController
@RequestMapping("/v1/folhas-de-ponto")
public class FolhaDePontoController {

    private FolhaDePontoService folhaDePontoService;

    @Autowired
    public FolhaDePontoController(FolhaDePontoService folhaDePontoService) {
        this.folhaDePontoService = folhaDePontoService;
    }

    @GetMapping("/{mes}")
    public ResponseEntity<RelatorioMensalDTO> acessarRelatorioMensal(@PathVariable("mes") YearMonth mesAno) {
        return new ResponseEntity<RelatorioMensalDTO>(folhaDePontoService.gerarFolhaDePonto(mesAno), HttpStatus.OK);
    }
}
