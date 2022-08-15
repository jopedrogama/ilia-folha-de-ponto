package br.com.ilia.digital.folhadeponto.Controllers;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ilia.digital.folhadeponto.Services.FolhaDePontoService;

@RestController
@RequestMapping("/v1/folhas-de-ponto")
public class FolhaDePontoController {

    private FolhaDePontoService folhaDePontoService;

    @Autowired
    public FolhaDePontoController(FolhaDePontoService folhaDePontoService) {
        this.folhaDePontoService = folhaDePontoService;
    }

    @GetMapping("{mes}")
    public ResponseEntity<Object> acessarRelatorioMensal(@PathVariable YearMonth mesAno) {
        folhaDePontoService.gerarFolhaDePonto(mesAno);
        return new ResponseEntity<Object>("ok", HttpStatus.OK);
    }
}
