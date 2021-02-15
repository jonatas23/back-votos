package com.southsystem.desafio.back.votos.controller;

import com.southsystem.desafio.back.votos.dto.VotoDTO;
import com.southsystem.desafio.back.votos.dto.VotoRespostaDTO;
import com.southsystem.desafio.back.votos.exception.ResponseException;
import com.southsystem.desafio.back.votos.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aip/votacao")
public class VotosController {

    @Autowired
    VotoService votoService;

    @PostMapping(value = "/votar")
    public ResponseEntity<?> votar(@RequestBody VotoDTO votoDTO) {
        try {
            VotoRespostaDTO voto = votoService.salvar(votoDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Voto realizado com sucesso! " + voto);
        } catch (Exception e) {
            return ResponseException.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
