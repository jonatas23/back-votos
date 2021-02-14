package com.southsystem.desafio.back.votos.controller;

import com.southsystem.desafio.back.votos.api.ApiError;
import com.southsystem.desafio.back.votos.entities.Pauta;
import com.southsystem.desafio.back.votos.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    @Autowired
    PautaService pautaService;

    @GetMapping(value = "/todasPautas", produces = "application/json")
    private ResponseEntity<List<Pauta>> pautas(){
        return ResponseEntity.status(HttpStatus.OK).body(pautaService.buscarTodos());
    }

    @PostMapping(value = "/nova")
    public ResponseEntity<?> nova(@RequestBody Pauta pauta){
        try {
            pauta = pautaService.salvar(pauta);
            return ResponseEntity.status(HttpStatus.OK).body("Pauta cadastrada com sucesso! " + pauta);
        } catch (Exception e) {
            return ApiError.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
