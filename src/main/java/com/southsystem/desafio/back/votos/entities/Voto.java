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
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String voto;

    @ManyToOne(cascade = CascadeType.ALL)
    private Sessao sessao;

    @ManyToOne(cascade = CascadeType.ALL)
    private Associado associado;

    public Voto(String voto, Sessao sessao, Associado associado) {
        this.voto = voto;
        this.sessao = sessao;
        this.associado = associado;
    }
}
