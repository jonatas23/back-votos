package com.southsystem.desafio.back.votos.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SessaoVotosDTO {

    private Integer totalVotos;
    private Long votosSim;
    private Long votosNao;

}
