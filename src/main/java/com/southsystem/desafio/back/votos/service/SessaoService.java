package com.southsystem.desafio.back.votos.service;

import com.southsystem.desafio.back.votos.dto.SessaoDTO;
import com.southsystem.desafio.back.votos.dto.SessaoVotosDTO;
import com.southsystem.desafio.back.votos.entities.Pauta;
import com.southsystem.desafio.back.votos.entities.Sessao;
import com.southsystem.desafio.back.votos.entities.Voto;
import com.southsystem.desafio.back.votos.exception.MensagemException;
import com.southsystem.desafio.back.votos.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class SessaoService {

    SessaoRepository sessaoRepository;
    VotoService votoService;
    PautaService pautaService;

    @Autowired
    public SessaoService(SessaoRepository sessaoRepository, VotoService votoService, PautaService pautaService) {
        this.sessaoRepository = sessaoRepository;
        this.votoService = votoService;
        this.pautaService = pautaService;
    }

    public Sessao salvar(SessaoDTO sessaoDTO) throws MensagemException {
        Pauta pauta = pautaService.buscarId(sessaoDTO.getIdPauta());
        Sessao sessao = sessaoDTO.transformaParaObjeto(pauta);

        if (sessao.getPauta() == null) {
            throw new MensagemException("Necessario informar uma Pauta existente!");
        }

        sessao = sessaoRepository.save(sessao);

        this.encerrarSessao(sessao);

        return sessao;
    }

    public List<Sessao> buscarTodos(){
        return sessaoRepository.findAll();
    }

    public Sessao validarSessao(Long idSessao) throws MensagemException {
        Sessao sessao = sessaoRepository.findById(idSessao).get();

        if (!sessao.getAbertura()) {
            throw new MensagemException("Sessão fechada!");
        }

        return sessao;
    }

    public SessaoVotosDTO totalVotos(Long idSessao) {
        List<Voto> votos = votoService.buscarSessao(idSessao);

        Long votosSim = votos.stream().filter(s -> s.getVoto().equals("SIM")).count();
        Long votosNao = votos.stream().filter(s -> s.getVoto().equals("NAO")).count();

        return new SessaoVotosDTO(votos.size(), votosSim, votosNao);
    }

    private void encerrarSessao(Sessao sessao)    {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            List<Voto> votos = votoService.buscarSessao(sessao.getId());

            sessao.setAbertura(!sessao.getAbertura());
            sessao.setTotalVotos(votos.size());

            sessaoRepository.save(sessao);
        };

        ses.schedule(task, sessao.getTempoMinutos(), TimeUnit.MINUTES);
        ses.shutdown();
    }
}
