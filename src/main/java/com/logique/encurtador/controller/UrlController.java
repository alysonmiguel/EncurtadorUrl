package com.logique.encurtador.controller;

import com.logique.encurtador.model.Url;
import com.logique.encurtador.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Controller
public class UrlController {

    public final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("encurtarUrl")
    public String encurtarUrl(@ModelAttribute Url url, HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        Cookie c = null;

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("session")){
                c = cookie;
                break;
            }
        }
        if (c != null) {
            url.setUsuarioId(Integer.parseInt(new String(Base64.getDecoder().decode(c.getValue()))));
        }
        LocalDateTime tempoAtual = LocalDateTime.now();
        url.setData(tempoAtual);
        System.out.println("URL" + url);

        urlService.salvarUrl(url, makeUrl(request));

        return "redirect:/home";
    }

    @GetMapping("getUrl/{encurtada}")
    public void getUrl(@PathVariable (name = "encurtada") String encurtada, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String urlReferencia = makeUrl(request) + "/getUrl/" + encurtada;
        Url url = urlService.getUrl(urlReferencia);

        if (url != null){
            System.out.println(url.getUrlOriginal());
            response.sendRedirect(url.getUrlOriginal());
        }
    }

    public static String makeUrl(HttpServletRequest request){
        return request.getServerName() +":"+ request.getServerPort();
    }
}
