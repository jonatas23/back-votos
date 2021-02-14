package com.southsystem.desafio.back.votos.controller;

import com.southsystem.desafio.back.votos.api.ApiError;
import com.southsystem.desafio.back.votos.dto.SessaoDTO;
import com.southsystem.desafio.back.votos.dto.SessaoVotosDTO;
import com.southsystem.desafio.back.votos.entities.Sessao;
import com.southsystem.desafio.back.votos.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessao")
public class SessaoController {

    @Autowired
    SessaoService sessaoService;

    @GetMapping(value = "/todasSessoes", produces = "application/json")
    private ResponseEntity<List<Sessao>> todasSessoes(){
        return ResponseEntity.status(HttpStatus.OK).body(sessaoService.buscarTodos());
    }

    @GetMapping(value = "/total/{idSessao}", produces = "application/json")
    private ResponseEntity<SessaoVotosDTO> total(@PathVariable Long idSessao) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(sessaoService.totalVotos(idSessao));
        } catch (Exception e) {
            return ApiError.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/novaSessao")
    public ResponseEntity<?> nova(@RequestBody SessaoDTO sessaoDTO) {
        try {
            Sessao sessao = sessaoService.salvar(sessaoDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Sessão Aberta! " + sessao);
        } catch (Exception e) {
            return ApiError.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
