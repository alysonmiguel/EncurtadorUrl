package com.logique.encurtador.service;

import com.logique.encurtador.model.Url;
import com.logique.encurtador.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlService {

    @Autowired
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public List<Url> findAllByUsuarioId(Integer id){
        return urlRepository.findAllByUsuarioId(id);
    }

    public Url createUrl(Url url){
        return urlRepository.save(url);
    }
}
