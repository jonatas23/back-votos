package com.southsystem.desafio.back.votos.controller;

import com.southsystem.desafio.back.votos.api.ApiError;
import com.southsystem.desafio.back.votos.dto.PautaDTO;
import com.southsystem.desafio.back.votos.dto.PautaRespostaDTO;
import com.southsystem.desafio.back.votos.dto.SessaoDTO;
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

    @GetMapping(value = "/listarPautas", produces = "application/json")
    private ResponseEntity<List<Pauta>> pautas(){
        return ResponseEntity.status(HttpStatus.OK).body(pautaService.buscarTodos());
    }

    @PostMapping(value = "/nova")
    public ResponseEntity<?> nova(@RequestBody PautaDTO pauta){
        try {
            PautaRespostaDTO respostaDTO = pautaService.salvar(pauta.transformaParaObjeto());
            return ResponseEntity.status(HttpStatus.OK).body("Pauta cadastrada com sucesso! " + respostaDTO);
        } catch (Exception e) {
            return ApiError.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/abrirSessao")
    public ResponseEntity<?> abrirSessao(@RequestBody SessaoDTO sessaoDTO) {
        try {
            Pauta pauta = pautaService.abrirSessao(sessaoDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Sessão Aberta! " + pauta);
        } catch (Exception e) {
            return ApiError.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
