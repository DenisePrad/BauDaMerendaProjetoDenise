package com.bau.merenda.service;

import com.bau.merenda.model.Usuario;
import com.bau.merenda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public void cadastrar(String usuario, String senha) {
        if (usuarioRepo.findByUsuario(usuario) != null) {
            throw new RuntimeException("Usuário já existe!");
        }
        
        Usuario novo = new Usuario();
        novo.setUsuario(usuario);
        novo.setSenha(senha); // Salvando a senha em texto plano (não é seguro!)
        
        usuarioRepo.save(novo);
    }

    public Usuario login(String usuario, String senha) {
        Usuario user = usuarioRepo.findByUsuario(usuario);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        
        // Verificação de senha em texto plano
        if (!senha.equals(user.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }
        
        return user;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepo.findAll();
    }
}