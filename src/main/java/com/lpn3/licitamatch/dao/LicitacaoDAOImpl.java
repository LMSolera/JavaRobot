package com.lpn3.licitamatch.dao;

import com.lpn3.licitamatch.model.Licitacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LicitacaoDAOImpl implements LicitacaoDAO {

    private final Connection conexao;

    public LicitacaoDAOImpl() {
        this.conexao = ConnectionFactory.getConnection();
    }

    @Override
    public Licitacao salvar(Licitacao licitacao) {      
        // Query SQL para checar se já existe uma licitação com conteúdo e nome iguais no banco
        String sqlCheck = "SELECT * FROM Licitacao WHERE arquivo_pdf = ? AND nome_arquivo = ?";       
        try (PreparedStatement stmtCheck = conexao.prepareStatement(sqlCheck)) {         
            stmtCheck.setBytes(1, licitacao.getArquivoPdf());
            stmtCheck.setString(2, licitacao.getNomeArquivo());           
            ResultSet resultado = stmtCheck.executeQuery();           
            if (!resultado.next()) {
                String sql = "INSERT INTO Licitacao (id_usuario_fk, nome_arquivo, arquivo_pdf, data_envio) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, licitacao.getIdUsuarioFk());
                    stmt.setString(2, licitacao.getNomeArquivo());
                    stmt.setBytes(3, licitacao.getArquivoPdf());
                    stmt.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now()));
                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("A criação da licitação falhou, nenhuma linha foi afetada.");
                    }
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            licitacao.setId(generatedKeys.getInt(1));
                        } else {
                            throw new SQLException("A criação da licitação falhou, nenhum ID foi obtido.");
                        }
                    }

                } catch (SQLException e) {
                    throw new RuntimeException("Erro ao salvar licitação no banco: " + e.getMessage(), e);
                }
            } else {
                licitacao.setId(resultado.getInt(1));
            }            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar repetições no banco: " + e.getMessage());
            e.printStackTrace();
        }    
        return licitacao;
    }

    @Override
    public Optional<Licitacao> buscarPorId(int id) {

        String sql = "SELECT * FROM Licitacao WHERE id_licitacao = ?";
        Licitacao licitacao = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    licitacao = new Licitacao();
                    licitacao.setId(rs.getInt("id_licitacao"));
                    licitacao.setIdUsuarioFk(rs.getInt("id_usuario_fk"));
                    licitacao.setNomeArquivo(rs.getString("nome_arquivo"));
                    licitacao.setArquivoPdf(rs.getBytes("arquivo_pdf")); // Pega o conteúdo do PDF
                    licitacao.setDataEnvio(rs.getTimestamp("data_envio").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar licitação por ID: " + e.getMessage(), e);
        }
        return Optional.ofNullable(licitacao);

    }

    @Override

    public List<Licitacao> buscarPorUsuario(int idUsuario) {

        List<Licitacao> licitacoes = new ArrayList<>();

        String sql = "SELECT * FROM Licitacao WHERE id_usuario_fk = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Licitacao licitacao = new Licitacao();
                    licitacao.setId(rs.getInt("id_licitacao"));
                    licitacao.setIdUsuarioFk(rs.getInt("id_usuario_fk"));
                    licitacao.setNomeArquivo(rs.getString("nome_arquivo")); 
                    licitacao.setDataEnvio(rs.getTimestamp("data_envio").toLocalDateTime());
                    licitacoes.add(licitacao);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar licitações por usuário: " + e.getMessage(), e);
        }
        return licitacoes;

    }

    @Override

    public void deletar(int id) {

        String sql = "DELETE FROM Licitacao WHERE id_licitacao = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar licitação: " + e.getMessage(), e);
        }
    }

}
