package com.lpn3.licitamatch.dao;
import com.lpn3.licitamatch.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UsuarioDAOImpl implements UsuarioDAO {

    private final Connection conexao;

    public UsuarioDAOImpl() {
        this.conexao = ConnectionFactory.getConnection();
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha_hash, data_criacao) VALUES (?, ?, ?, ?)";

        // Usamos try-with-resources para garantir que o PreparedStatement seja fechado.
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaHash());
            stmt.setDate(4, java.sql.Date.valueOf(usuario.getDataCriacao()));
            stmt.executeUpdate();
            
            // Recupera o ID gerado pelo banco de dados.
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            // Lança uma exceção mais específica para a camada de serviço/controller tratar.
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage(), e);
        }
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = "SELECT id_usuario, nome, email, senha_hash FROM Usuario WHERE email = ?";
        Usuario usuario = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenhaHash(rs.getString("senha_hash"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por e-mail: " + e.getMessage(), e);
        }
        
        // Optional.ofNullable cria um Optional que pode conter um valor ou ser vazio.
        return Optional.ofNullable(usuario);
    }
    
    // --- Outras implementações ---

    @Override
    public void atualizar(Usuario usuario) {
        // Implementação para atualizar o usuário
        // SQL: "UPDATE Usuario SET nome = ?, email = ?, senha_hash = ? WHERE id_usuario = ?"
    }

    @Override
    public void deletar(int id) {
        // Implementação para deletar o usuário
        // SQL: "DELETE FROM Usuario WHERE id_usuario = ?"
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) {
        // Implementação para buscar por ID, similar a buscarPorEmail
        return Optional.empty();
    }

    public List<Usuario> buscarTodos() {
        // Implementação para buscar todos os usuários
        // SQL: "SELECT id_usuario, nome, email FROM Usuario"
        return new ArrayList<>(); // Retorno de exemplo
    }
}
