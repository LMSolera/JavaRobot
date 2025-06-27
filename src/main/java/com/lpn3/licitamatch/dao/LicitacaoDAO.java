package com.lpn3.licitamatch.dao;

import com.lpn3.licitamatch.model.Licitacao;
import java.util.List;
import java.util.Optional;

public interface LicitacaoDAO {

    Licitacao salvar(Licitacao licitacao);

    Optional<Licitacao> buscarPorId(int id);

    List<Licitacao> buscarPorUsuario(int idUsuario);

    void deletar(int id);

}
