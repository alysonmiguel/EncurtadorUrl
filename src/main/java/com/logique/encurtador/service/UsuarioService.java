package com.logique.encurtador.service;

import com.logique.encurtador.model.Usuario;
import com.logique.encurtador.repository.UsuarioRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrarUsuario(String login, String senha, String email){

        if (login.isEmpty() && senha.isEmpty()){
            return null;
        } else {

            if (usuarioRepository.findFirstByLogin(login).isPresent()){
                System.out.println("Login já registrado na aplicação");
                return null;
            }

            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setSenha(bc.encode(senha));
            usuario.setEmail(email);
            return  (usuarioRepository.save(usuario));
        }
    }

    public Usuario autenticacao(String login, String senha){
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario != null) {
            return bc.matches(senha, usuario.getSenha()) ? usuario : null;
        } else {
            return null;
        }
    }

    public Usuario findByLogin(String login){
        return  usuarioRepository.findByLogin(login);
    }
}
