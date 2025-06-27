package com.lpn3.licitamatch.service;

import com.lpn3.licitamatch.dao.UsuarioDAO;
import com.lpn3.licitamatch.dao.UsuarioDAOImpl;
import com.lpn3.licitamatch.model.Usuario;
import org.mindrot.jbcrypt.BCrypt; //biblioteca de criptografia
import java.util.Optional;


public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public Usuario registrarNovoUsuario(String nome, String email, String senha) {
        Optional<Usuario> usuarioExistente = usuarioDAO.buscarPorEmail(email);
        
        // 1. CONFERIR EXISTENCIA DO EMAIL
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        }

        // 2. REGRA DE NEGÓCIO: Criptografar a senha
        String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

        // 3. Preparar o objeto para salvar
        Usuario novoUsuario = new Usuario(nome, email);
        novoUsuario.setSenhaHash(senhaHash); // Define o hash seguro

        // 4. Chamar o DAO para persistir o usuário no banco
        return usuarioDAO.salvar(novoUsuario);
    }
}