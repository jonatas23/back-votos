package com.southsystem.desafio.back.votos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class VotoDTO {

    private String voto;
    private Long idSessao;
    private String cpfAssociado;

}
