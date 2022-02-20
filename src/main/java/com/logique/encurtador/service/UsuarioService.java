package com.logique.encurtador.service;

import com.logique.encurtador.model.Usuario;
import com.logique.encurtador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrarUsuario(String login, String senha, String email){

//        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        if (login.isEmpty() && senha.isEmpty()){
            return null;
        } else {

            if (usuarioRepository.findFirstByLogin(login).isPresent()){
                System.out.println("Login já registrado na aplicação");
                return null;
            }

            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setSenha(senha);
            usuario.setEmail(email);
            return  (usuarioRepository.save(usuario));
        }
    }

    public Usuario autenticacao(String login, String senha){
        return  usuarioRepository.findByLoginAndSenha(login, senha).orElse(null);
    }

    public Usuario findByLogin(String login){
        return  usuarioRepository.findByLogin(login);
    }
}
