package com.logique.encurtador.utils;

import com.logique.encurtador.model.Url;

import java.util.Base64;

public class Encurtado {

    public static String encurtar(Url url){

        String idUsuario = url.getUsuarioId().toString();
        idUsuario += url.getId();

        return  Base64.getEncoder().encodeToString(idUsuario.getBytes());
    }
}
