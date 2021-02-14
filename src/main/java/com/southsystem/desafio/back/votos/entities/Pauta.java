package com.southsystem.desafio.back.votos.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Boolean sessaoAbertura;

    private Integer tempoMinutos;

    private Long totalVotosSim;

    private Long totalVotosNao;

    public Pauta(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
