package com.southsystem.desafio.back.votos.repository;

import com.southsystem.desafio.back.votos.entities.Voto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Query("SELECT v from Voto v where v.sessao.id = :idSessao")
    public List<Voto> findSessao(@Param("idSessao") Long idSessao);
}
