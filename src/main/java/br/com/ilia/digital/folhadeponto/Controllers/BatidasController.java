package br.com.ilia.digital.folhadeponto.Controllers;

import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaRequestDTO;
import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaResponseDTO;

import br.com.ilia.digital.folhadeponto.DTOs.Mappers.BatidaMapper;
import br.com.ilia.digital.folhadeponto.Models.BatidaModel;
import br.com.ilia.digital.folhadeponto.Services.BatidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/batidas")
public class BatidasController {

    @Autowired
    private BatidaService batidaService;

    @Autowired
    private BatidaMapper batidaMapper;

    @PostMapping
    public BatidaResponseDTO registrarBatida(@RequestBody BatidaRequestDTO batidaRequestDTO) {
        BatidaModel batidaModel = batidaMapper.toModel(batidaRequestDTO);
        batidaService.baterPonto(batidaModel);
        System.out.println(batidaModel);
        return null;
    }
}
