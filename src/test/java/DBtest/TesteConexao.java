package DBtest;

import com.lpn3.licitamatch.dao.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) throws SQLException {
        
        Connection conexao = null;
        
        // O bloco try-catch-finally é a forma mais segura de trabalhar com recursos
        // como conexões de banco de dados.
        try {
            System.out.println("Tentando obter a conexão com o banco de dados...");
            
            // 1. Tenta obter a conexão usando nossa fábrica
            conexao = ConnectionFactory.getConnection();

            // 2. Verifica se a conexão retornada não é nula e se está aberta
            if (conexao != null && !conexao.isClosed()) {
                System.out.println("=============================================");
                System.out.println(">>> SUCESSO! Conexão com o banco de dados estabelecida.");
                System.out.println("=============================================");
            } else {
                // Isso não deveria acontecer se não houver exceção, mas é uma verificação extra.
                System.err.println("A conexão retornou nula ou já está fechada.");
            }

        } catch (RuntimeException e) {
            // Se a ConnectionFactory lançar uma exceção (ex: não achou o arquivo, senha errada),
            // nós a capturamos aqui.
            System.err.println("=============================================");
            System.err.println(">>> FALHA! Não foi possível conectar ao banco de dados.");
            System.err.println("=============================================");
            System.err.println("Causa do erro: " + e.getMessage());
            // Imprime o "rastro" completo do erro para ajudar a depurar
            e.printStackTrace();
            
        } finally {
            // 3. O bloco 'finally' SEMPRE é executado, tendo a conexão funcionado ou não.
            // É o lugar perfeito para garantir que a conexão seja fechada.
            System.out.println("\nTentando fechar a conexão...");
            ConnectionFactory.closeConnection();
            System.out.println("Conexão fechada.");
        }
    }
}