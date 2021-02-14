package com.southsystem.desafio.back.votos.service;

import com.southsystem.desafio.back.votos.entities.Pauta;
import com.southsystem.desafio.back.votos.exception.MensagemException;
import com.southsystem.desafio.back.votos.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {

    PautaRepository pautaRepository;

    @Autowired
    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta salvar(Pauta pauta) throws MensagemException {
        if (pauta.getNome() == null || pauta.getNome().equals("")) {
            throw new MensagemException("Pauta vazia");
        }
        return pautaRepository.save(pauta);
    }

    public Pauta buscarId(Long idPauta) throws MensagemException {
        if (idPauta == null || idPauta == 0) {
            throw new MensagemException("Necessario informar o ID da Pauta!");
        }
        return pautaRepository.findById(idPauta).get();
    }

    public List<Pauta> buscarTodos(){
        return pautaRepository.findAll();
    }
}
