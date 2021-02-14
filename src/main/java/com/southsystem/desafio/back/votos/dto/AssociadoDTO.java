package com.southsystem.desafio.back.votos.dto;

import com.southsystem.desafio.back.votos.entities.Associado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AssociadoDTO {

    private String nome;
    private String cpf;

    public Associado transformaParaObjeto() {
        return new Associado(nome, cpf);
    }

}
