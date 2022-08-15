package br.com.ilia.digital.folhadeponto.Controllers;

import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaRequestDTO;
import br.com.ilia.digital.folhadeponto.DTOs.BatidasDTOs.BatidaResponseDTO;

import br.com.ilia.digital.folhadeponto.DTOs.Mappers.BatidaMapper;
import br.com.ilia.digital.folhadeponto.Models.BatidaModel;
import br.com.ilia.digital.folhadeponto.Services.BatidaService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/batidas")
public class BatidasController {

    private BatidaService batidaService;
    private BatidaMapper batidaMapper;

    @Autowired
    public BatidasController(BatidaService batidaService, BatidaMapper batidaMapper) {
        this.batidaService = batidaService;
        this.batidaMapper = batidaMapper;
    }

    @PostMapping
    public ResponseEntity<BatidaResponseDTO> registrarBatida(@Valid @RequestBody BatidaRequestDTO batidaRequestDTO) {
        BatidaModel batidaModel = batidaMapper.toModel(batidaRequestDTO);
        return ResponseEntity.ok(batidaService.baterPonto(batidaModel));
    }
}
