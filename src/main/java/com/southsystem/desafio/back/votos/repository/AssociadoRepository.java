package com.southsystem.desafio.back.votos.repository;

import com.southsystem.desafio.back.votos.entities.Associado;
import com.southsystem.desafio.back.votos.entities.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    @Query("SELECT a from Associado a where a.cpf = :cpf")
    public Associado buscarCpf(@Param("cpf") String cpf);
}
