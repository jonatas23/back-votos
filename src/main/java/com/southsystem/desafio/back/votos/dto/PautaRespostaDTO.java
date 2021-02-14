package com.southsystem.desafio.back.votos.dto;

import com.southsystem.desafio.back.votos.entities.Pauta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class PautaRespostaDTO {

    private Long id;
    private String nome;
    private String descricao;

    public PautaRespostaDTO transformaParaObjeto(Pauta pauta) {
        this.id = pauta.getId();
        this.nome = pauta.getNome();
        this.descricao = pauta.getDescricao();
        return this;
    }

    @Override
    public String toString() {
        return "Pauta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
