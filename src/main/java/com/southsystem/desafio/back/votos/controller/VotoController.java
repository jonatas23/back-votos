package com.southsystem.desafio.back.votos.controller;

import com.southsystem.desafio.back.votos.api.ApiError;
import com.southsystem.desafio.back.votos.dto.VotoDTO;
import com.southsystem.desafio.back.votos.entities.Voto;
import com.southsystem.desafio.back.votos.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votacao")
public class VotoController {

    @Autowired
    VotoService votoService;

    @PostMapping(value = "/votar")
    public ResponseEntity<?> votar(@RequestBody VotoDTO votoDTO) {
        try {
            Voto voto = votoService.salvar(votoDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Voto realizado com sucesso! " + voto);
        } catch (Exception e) {
            return ApiError.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
