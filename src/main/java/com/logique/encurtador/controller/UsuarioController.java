package com.logique.encurtador.controller;

import com.logique.encurtador.model.Usuario;
import com.logique.encurtador.service.UsuarioService;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    public final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String getLogin(Model model){
        model.addAttribute("loginRequest", new Usuario());
        return "login";
    }

//    @GetMapping("/home")
//    public String getHome(Model model){
////        model.addAttribute("usuario", new Usuario());
//        return "home";
//    }

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

    @PostMapping("/home")
    public String login(@ModelAttribute Usuario usuario, Model model) {
        Usuario autenticacao = usuarioService.autenticacao(usuario.getLogin(), usuario.getSenha());
        System.out.println("loginUsuario " + autenticacao);
        if ( autenticacao != null ){
            model.addAttribute("usuarioLogin", autenticacao);
            return "home";
        } else {
            return "erro";
        }
    }


}


