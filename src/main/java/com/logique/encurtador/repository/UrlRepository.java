package com.logique.encurtador.repository;

import com.logique.encurtador.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Integer> {
    List<Url> findAllByUsuarioId(Integer id);

}
