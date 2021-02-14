package com.southsystem.desafio.back.votos.repository;

import com.southsystem.desafio.back.votos.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

}
