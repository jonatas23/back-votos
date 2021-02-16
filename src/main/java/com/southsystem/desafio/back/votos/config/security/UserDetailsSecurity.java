package com.southsystem.desafio.back.votos.config.security;

import com.southsystem.desafio.back.votos.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsSecurity implements UserDetailsService {

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    @Autowired
    private AssociadoRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException, DataAccessException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        Associado associado = repository.buscarCpf(cpf);
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        if (associado == null) {
//            throw new UsernameNotFoundException(String.format("Nenhum usuário encontrado '%s'.", cpf));
//        } else {
//            return new User("", encoder.encode(""), grantedAuthorities);
//        }

        return new User("", encoder.encode(""), grantedAuthorities);

    }

}
