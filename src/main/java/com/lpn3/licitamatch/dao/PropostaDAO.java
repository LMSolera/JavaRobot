package com.lpn3.licitamatch.dao;

import com.lpn3.licitamatch.model.Proposta;
import java.util.List;
import java.util.Optional;

public interface PropostaDAO {

    /**
     * Salva uma nova proposta no banco de dados.
     * @param proposta O objeto Proposta a ser salvo.
     * @return A proposta salva com o ID gerado.
     */
    Proposta salvar(Proposta proposta);

    /**
     * Busca uma proposta pelo seu ID.
     * @param id O ID da proposta.
     * @return Um Optional contendo a proposta se encontrada.
     */
    Optional<Proposta> buscarPorId(int id);

    /**
     * Busca todas as propostas de um usuário específico.
     * @param idUsuario O ID do usuário.
     * @return Uma lista de propostas do usuário.
     */
    List<Proposta> buscarPorUsuario(int idUsuario);

    /**
     * Deleta uma proposta pelo seu ID.
     * @param id O ID da proposta a ser deletada.
     */
    void deletar(int id);
}