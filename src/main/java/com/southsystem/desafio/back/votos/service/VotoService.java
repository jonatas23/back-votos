package com.southsystem.desafio.back.votos.service;

import com.southsystem.desafio.back.votos.dto.VotoDTO;
import com.southsystem.desafio.back.votos.dto.VotoRespostaDTO;
import com.southsystem.desafio.back.votos.entities.Associado;
import com.southsystem.desafio.back.votos.entities.Pauta;
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
    AssociadoService associadoService;
    PautaService pautaService;

    @Autowired
    public VotoService(VotoRepository votoRepository, @Lazy PautaService pautaService, AssociadoService associadoService) {
        this.votoRepository = votoRepository;
        this.pautaService = pautaService;
        this.associadoService = associadoService;
    }

    public VotoRespostaDTO salvar(VotoDTO votoDTO) throws MensagemException {
        try {
            Associado associado = this.validarAssociado(votoDTO.getCpfAssociado());
            Pauta pauta = pautaService.validarSessao(votoDTO.getIdPauta());

            if (this.buscarSessao(votoDTO.getIdPauta()).stream().anyMatch(v -> v.getAssociado().equals(associado))){
                throw new MensagemException("Associado já votou nesta sessão!");
            }

            if (!(votoDTO.getVoto().equalsIgnoreCase("SIM") || votoDTO.getVoto().equalsIgnoreCase("NÃO") || votoDTO.getVoto().equalsIgnoreCase("NAO"))) {
                throw new MensagemException("Os votos são apenas 'Sim'/'Não'!");
            }

            Voto voto = new Voto(votoDTO.getVoto(), pauta, associado);
            return new VotoRespostaDTO().transformaParaObjeto(votoRepository.save(voto));
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
