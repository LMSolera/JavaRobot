package com.lpn3.licitamatch.dao;

import com.lpn3.licitamatch.model.Proposta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropostaDAOImpl implements PropostaDAO {

    private final Connection conexao;

    public PropostaDAOImpl() {
        this.conexao = ConnectionFactory.getConnection();
    }

    @Override
    public Proposta salvar(Proposta proposta) {
        // Query SQL para checar se já existe uma licitação com conteúdo e nome iguais no banco
        String sqlCheck = "SELECT * FROM Proposta WHERE arquivo_pdf = ? AND nome_arquivo = ?"; 
        try (PreparedStatement stmtCheck = conexao.prepareStatement(sqlCheck)) {
            stmtCheck.setBytes(1, proposta.getArquivoPdf());
            stmtCheck.setString(2, proposta.getNomeArquivo());           
            ResultSet resultado = stmtCheck.executeQuery();
            if (!resultado.next()) {
                String sql = "INSERT INTO Proposta (id_usuario_fk, nome_arquivo, arquivo_pdf, data_envio) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, proposta.getIdUsuarioFk());
                    stmt.setString(2, proposta.getNomeArquivo());
                    stmt.setBytes(3, proposta.getArquivoPdf());
                    stmt.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now()));
                    stmt.executeUpdate();
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            proposta.setId(rs.getInt(1));
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Erro ao salvar proposta: " + e.getMessage(), e);
                }
            } else {
                proposta.setId(resultado.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar repetições no banco: " + e.getMessage());
            e.printStackTrace();
        } 
        
        return proposta;
    }

    @Override
    public Optional<Proposta> buscarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Proposta> buscarPorUsuario(int idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void deletar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}