package com.lpn3.licitamatch.dao;

import com.lpn3.licitamatch.model.Comparacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComparacaoDAOImpl implements ComparacaoDAO {

    private final Connection conexao;

    public ComparacaoDAOImpl() {
        this.conexao = ConnectionFactory.getConnection();
    }

    @Override
    public void salvar(Comparacao comparacao) {
        String sql = "INSERT INTO Comparacao (id_licitacao_fk, id_proposta_fk, txt_semelhanca, txt_diferenca, nota) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, comparacao.getIdLicitacaoFk());
            stmt.setInt(2, comparacao.getIdPropostaFk());
            stmt.setString(3, comparacao.getTxtSemelhanca());
            stmt.setString(4, comparacao.getTxtDiferenca());
            stmt.setInt(5, comparacao.getNota());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar o resultado da comparação: " + e.getMessage(), e);
        }
    }
}