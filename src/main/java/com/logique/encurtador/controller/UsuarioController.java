package com.logique.encurtador.controller;

import com.logique.encurtador.model.Url;
import com.logique.encurtador.model.Usuario;
import com.logique.encurtador.service.UrlService;
import com.logique.encurtador.service.UsuarioService;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.List;

@Controller
public class UsuarioController {

    public final UsuarioService usuarioService;
    public final UrlService urlService;

    public UsuarioController(UsuarioService usuarioService, UrlService urlService) {
        this.usuarioService = usuarioService;
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String getLogin(Model model){
        model.addAttribute("loginRequest", new Usuario());
        return "login";
    }

    @GetMapping("/cadastrarUsuario")
    public String getCadastro(Model model) {
        model.addAttribute("cadastrarUsuario", new Usuario());
        return "cadastro_usuario";
    }

    @PostMapping("/cadastrarUsuario")
    public String cadastro(@ModelAttribute Usuario usuario) {
        Usuario registroUser = usuarioService.cadastrarUsuario(usuario.getLogin(), usuario.getSenha(), usuario.getEmail());
        return registroUser == null ? "erro" : "redirect:/";
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        Cookie c = null;

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("session")){
                c = cookie;
                break;
            }
        }
        if (c != null) {
            List<Url> urls = urlService.findAllByUsuarioId(Integer.parseInt(new String(Base64.getDecoder().decode(c.getValue()))));

            model.addAttribute("urls", urls);
            return "home";
        }
        return "redirect:/";
    }

    @PostMapping("/autenticacao")
    public String login(@ModelAttribute Usuario usuario, Model model, HttpServletResponse response, RedirectAttributes ra) {
        Usuario autenticacao = usuarioService.autenticacao(usuario.getLogin(), usuario.getSenha());
        if ( autenticacao != null ){

            Cookie c = new Cookie("session", Base64.getEncoder().encodeToString(autenticacao.getId().toString().getBytes()));
            c.setMaxAge(20*60);
            response.addCookie(c);

            ra.addFlashAttribute("usuarioLogin", autenticacao);

            return "redirect:/home";
        } else {
            return "erro";
        }
    }


}


