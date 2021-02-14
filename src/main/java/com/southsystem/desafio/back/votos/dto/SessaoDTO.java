package com.southsystem.desafio.back.votos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SessaoDTO {

    private Boolean abertura;
    private Integer tempoMinutos;
    private Long idPauta;

}
