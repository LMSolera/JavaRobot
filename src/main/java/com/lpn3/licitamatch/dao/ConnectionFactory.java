package com.lpn3.licitamatch.dao;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//padrão Singleton

public class ConnectionFactory {
    
    private static Connection conexao = null;
    
    // Construtor privado para impedir a instanciação direta.
    private ConnectionFactory() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }
    
    public static Connection getConnection(){
        try{
            if(conexao == null || conexao.isClosed()){
                conexao = newConnection();
            }
        } catch (SQLException e){ //em caso de erro, melhor criar uma nova
            conexao = newConnection();
        }
        return conexao;
    }

    private static Connection newConnection() {
        try{
            Properties props = loadProperty();
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
        
    }

    private static Properties loadProperty() {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("database.properties")) {
            
            if (input == null) {
                throw new RuntimeException("Arquivo 'database.properties' não encontrado no classpath.");
            }
            
            Properties props = new Properties();
            props.load(input); // Carrega as propriedades do arquivo
            return props;

        } catch (IOException e) {
            // Lança uma exceção personalizada para erros de leitura do arquivo
            throw new RuntimeException("Erro ao ler o arquivo de propriedades.", e);
        }
    }
    public static void closeConnection() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar a conexão com o banco de dados.", e);
        }
    }
}
