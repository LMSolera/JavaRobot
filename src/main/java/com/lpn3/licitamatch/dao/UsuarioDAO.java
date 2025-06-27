package com.lpn3.licitamatch.dao;

import com.lpn3.licitamatch.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 A interface para o Data Access Object (DAO) da entidade Usuario.
 Define o contrato para as operações de persistência (CRUD).
 Algumas operações CRUD estão aqui para possíveis aprimoramentos do software
 */
public interface UsuarioDAO {

    /**
     * Salva um novo usuário no banco de dados.
     * @param usuario O objeto Usuario a ser salvo.
     * @return O objeto Usuario salvo, com o ID gerado pelo banco.
     */
    Usuario salvar(Usuario usuario);

    /**
     * Atualiza os dados de um usuário existente.
     * @param usuario O objeto Usuario com os dados atualizados.
     */
    void atualizar(Usuario usuario);

    /**
     * Deleta um usuário do banco de dados pelo seu ID.
     * @param id O ID do usuário a ser deletado.
     */
    void deletar(int id);

    /**
     * Busca um usuário pelo seu ID.
     * @param id O ID do usuário.
     * @return Um Optional contendo o usuário se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<Usuario> buscarPorId(int id);
    
    /**
     * Busca um usuário pelo seu email. Útil para a tela de login e para evitar duplicatas.
     * @param email O email do usuário.
     * @return Um Optional contendo o usuário se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<Usuario> buscarPorEmail(String email);

    /**
     * Busca todos os usuários cadastrados.
     * @return Uma lista com todos os usuários.
     */
    List<Usuario> buscarTodos();
}