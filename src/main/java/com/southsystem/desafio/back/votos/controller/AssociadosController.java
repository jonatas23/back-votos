package com.southsystem.desafio.back.votos.controller;

import com.southsystem.desafio.back.votos.dto.AssociadoDTO;
import com.southsystem.desafio.back.votos.entities.Associado;
import com.southsystem.desafio.back.votos.exception.ResponseException;
import com.southsystem.desafio.back.votos.service.AssociadoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/associados")
public class AssociadosController {

    @Autowired
    AssociadoService associadoService;

    @ApiOperation(value = "Está operação salva um novo Associado.")
    @PostMapping(value = "/")
    public ResponseEntity<?> salvar(@RequestBody AssociadoDTO associadoDTO){
        try {
            Associado associado = associadoService.salvar(associadoDTO.transformaParaObjeto());
            return ResponseEntity.status(HttpStatus.OK).body("Associado cadastrado com sucesso! " + associado);
        } catch (Exception e) {
            return ResponseException.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
