package com.southsystem.desafio.back.votos.dto;


import com.southsystem.desafio.back.votos.entities.Pauta;
import com.southsystem.desafio.back.votos.entities.Sessao;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SessaoDTO {

    private Boolean abertura;
    private Integer tempoMinutos;
    private Long idPauta;

    public Sessao transformaParaObjeto(Pauta pauta){
        return new Sessao(abertura, tempoMinutos, pauta);
    }
}
