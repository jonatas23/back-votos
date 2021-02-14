package com.southsystem.desafio.back.votos.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean abertura;

    private Integer tempoMinutos;

    private Integer totalVotos;

    @ManyToOne(cascade = CascadeType.ALL)
    private Pauta pauta;

    public Sessao(Boolean abertura, Integer tempoMinutos, Pauta pauta) {
        this.abertura = abertura;
        this.tempoMinutos = tempoMinutos;
        this.pauta = pauta;
    }
}
