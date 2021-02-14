package com.southsystem.desafio.back.votos.service;

import com.southsystem.desafio.back.votos.dto.PautaRespostaDTO;
import com.southsystem.desafio.back.votos.dto.SessaoDTO;
import com.southsystem.desafio.back.votos.entities.Pauta;
import com.southsystem.desafio.back.votos.entities.Voto;
import com.southsystem.desafio.back.votos.exception.MensagemException;
import com.southsystem.desafio.back.votos.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class PautaService {

    PautaRepository pautaRepository;
    VotoService votoService;

    @Autowired
    public PautaService(PautaRepository pautaRepository, VotoService votoService) {
        this.pautaRepository = pautaRepository;
        this.votoService = votoService;
    }

    public PautaRespostaDTO salvar(Pauta pauta) throws MensagemException {
        if (pauta.getNome() == null || pauta.getNome().equals("")) {
            throw new MensagemException("Pauta vazia");
        }
        return new PautaRespostaDTO().transformaParaObjeto(pautaRepository.save(pauta));
    }

    public List<Pauta> buscarTodos(){
        return pautaRepository.findAll();
    }

    public Pauta abrirSessao(SessaoDTO sessaoDTO) throws MensagemException {
        Pauta pauta = pautaRepository.findById(sessaoDTO.getIdPauta()).get();

        if (pauta == null) {
            throw new MensagemException("Necessario informar uma Pauta existente!");
        } else if (pauta.getSessaoAberta() != null) {
            throw new MensagemException("Sessão já foi aberta para esta Pauta!");
        }

        pauta.setSessaoAberta(sessaoDTO.getAbertura());
        pauta.setTempoMinutos(sessaoDTO.getTempoMinutos() == 0 ? 1 : sessaoDTO.getTempoMinutos());

        pauta = this.pautaRepository.save(pauta);

        this.encerrarSessao(pauta);

        return pauta;
    }

    private void encerrarSessao (Pauta pauta)    {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            List<Voto> votos = votoService.buscarSessao(pauta.getId());

            pauta.setSessaoAberta(!pauta.getSessaoAberta());
            pauta.setTotalVotosSim(votos.stream().filter(v -> v.getVoto().equalsIgnoreCase("SIM")).count());
            pauta.setTotalVotosNao(votos.stream().filter(v -> v.getVoto().equalsIgnoreCase("NÃO") || v.getVoto().equalsIgnoreCase("NAO")).count());

            pautaRepository.save(pauta);
        };

        ses.schedule(task, pauta.getTempoMinutos(), TimeUnit.MINUTES);
        ses.shutdown();
    }

    public Pauta validarSessao(Long idPauta) throws MensagemException {
        Pauta pauta = pautaRepository.findById(idPauta).get();

        if (!pauta.getSessaoAberta()) {
            throw new MensagemException("Sessão fechada!");
        }

        return pauta;
    }

}
