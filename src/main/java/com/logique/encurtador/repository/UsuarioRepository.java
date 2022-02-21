package com.logique.encurtador.repository;

import com.logique.encurtador.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByLogin(String login);
    Optional<Usuario> findFirstByLogin(String login);

}
