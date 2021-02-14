package com.southsystem.desafio.back.votos.dto;

import com.southsystem.desafio.back.votos.entities.Pauta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PautaDTO {

    private String nome;
    private String descricao;

    public Pauta transformaParaObjeto() {
        return new Pauta(nome, descricao);
    }
}
