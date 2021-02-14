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
    private Pauta pauta;

    @ManyToOne(cascade = CascadeType.ALL)
    private Associado associado;

    public Voto(String voto, Pauta pauta, Associado associado) {
        this.voto = voto;
        this.pauta = pauta;
        this.associado = associado;
    }
}
