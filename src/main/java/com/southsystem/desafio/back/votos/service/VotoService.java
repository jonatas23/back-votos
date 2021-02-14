package com.southsystem.desafio.back.votos.service;

import com.southsystem.desafio.back.votos.dto.VotoDTO;
import com.southsystem.desafio.back.votos.entities.Associado;
import com.southsystem.desafio.back.votos.entities.Sessao;
import com.southsystem.desafio.back.votos.entities.Voto;
import com.southsystem.desafio.back.votos.exception.MensagemException;
import com.southsystem.desafio.back.votos.repository.VotoRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class VotoService {

    VotoRepository votoRepository;
    SessaoService sessaoService;
    AssociadoService associadoService;

    @Autowired
    public VotoService(VotoRepository votoRepository, @Lazy SessaoService sessaoService, AssociadoService associadoService) {
        this.votoRepository = votoRepository;
        this.sessaoService = sessaoService;
        this.associadoService = associadoService;
    }

    public Voto salvar(VotoDTO votoDTO) throws MensagemException {
        try {
            Associado associado = this.validarAssociado(votoDTO.getCpfAssociado());
            Sessao sessao = sessaoService.validarSessao(votoDTO.getIdSessao());

            if (this.buscarSessao(votoDTO.getIdSessao()).stream().anyMatch(v -> v.getAssociado().equals(associado))){
                throw new MensagemException("Associado já votou nesta sessão!");
            }

            if (!(votoDTO.getVoto().equalsIgnoreCase("SIM") || votoDTO.getVoto().equalsIgnoreCase("NÃO") || votoDTO.getVoto().equalsIgnoreCase("NAO"))) {
                throw new MensagemException("Os votos são apenas 'Sim'/'Não'!");
            }

            Voto voto = new Voto(votoDTO.getVoto(), sessao, associado);
            return votoRepository.save(voto);
        } catch (IOException | JSONException e) {
            throw new MensagemException(e.getMessage());
        }
    }

    public List<Voto> buscarSessao(Long idSessao) {
        return votoRepository.findSessao(idSessao);
    }

    private Associado validarAssociado(String cpf) throws MensagemException, IOException, JSONException {
        Associado associado = associadoService.buscarCpf(cpf);

        if (associado == null) {
            throw new MensagemException("CPF não cadastrado!");
        }

        if (!associadoService.validarCpf(cpf)) {
            throw new MensagemException("Associado não esta autorizado a executar tal ação!");
        }

        return associado;
    }

}
