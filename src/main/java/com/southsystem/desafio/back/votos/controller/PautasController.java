package com.southsystem.desafio.back.votos.controller;

import com.southsystem.desafio.back.votos.dto.PautaDTO;
import com.southsystem.desafio.back.votos.dto.PautaRespostaDTO;
import com.southsystem.desafio.back.votos.dto.SessaoDTO;
import com.southsystem.desafio.back.votos.entities.Pauta;
import com.southsystem.desafio.back.votos.exception.ResponseException;
import com.southsystem.desafio.back.votos.service.PautaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pautas")
public class PautasController {

    @Autowired
    PautaService pautaService;

    @ApiOperation(value = "Está operação lista todas as Pautas Cadastradas.")
    @GetMapping(value = "/", produces = "application/json")
    private ResponseEntity<List<Pauta>> pautas(){
        return ResponseEntity.status(HttpStatus.OK).body(pautaService.buscarTodos());
    }

    @ApiOperation(value = "Está operação salva uma nova Pauta.")
    @PostMapping(value = "/")
    public ResponseEntity<?> salvar(@RequestBody PautaDTO pauta){
        try {
            PautaRespostaDTO respostaDTO = pautaService.salvar(pauta.transformaParaObjeto());
            return ResponseEntity.status(HttpStatus.OK).body("Pauta cadastrada com sucesso! " + respostaDTO);
        } catch (Exception e) {
            return ResponseException.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Está operação criara uma nova Sessão para Pauta.")
    @PostMapping(value = "/abrirSessao")
    public ResponseEntity<?> abrirSessao(@RequestBody SessaoDTO sessaoDTO) {
        try {
            Pauta pauta = pautaService.abrirSessao(sessaoDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Sessão Aberta! " + pauta);
        } catch (Exception e) {
            return ResponseException.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
