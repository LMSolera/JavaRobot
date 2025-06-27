package com.lpn3.licitamatch.view;

import com.lpn3.licitamatch.model.Usuario;
import com.lpn3.licitamatch.service.UsuarioService; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional; 
import javafx.scene.Node;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    // Instancia a camada de serviço.
    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String senha = senhaField.getText();

        // 1. Validações de tela 
        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro de validação", "Por favor, preencha todos os campos.");
            return;
        }
        if (!isEmailValido(email)) {
            mostrarAlerta("Email inválido", "Digite um email válido.");
            return;
        }

        // 2. Lógica de Autenticação (usando o Service)
        try {
            // Chama o serviço para tentar autenticar o usuário.
            Optional<Usuario> usuarioAutenticado = usuarioService.autenticar(email, senha);

            // 3. Verifica o resultado da autenticação
            if (usuarioAutenticado.isPresent()) {
                // O usuário existe e a senha está correta.
                mostrarAlerta("Login bem-sucedido", "Bem-vindo, " + usuarioAutenticado.get().getNome() + "!");
                redirecionarParaUpload(event); // Redireciona para a próxima tela
            } else {
                //O usuário não existe ou a senha está incorreta.
                mostrarAlerta("Erro", "E-mail ou senha inválidos.");
            }
        } catch (RuntimeException e) {
            // Captura erros de conexão com o banco ou outros problemas
            e.printStackTrace(); 
            mostrarAlerta("Erro de Sistema", "Não foi possível conectar ao banco de dados. Tente novamente mais tarde.");
        }
    }

    // Ação para ir para a tela de Upload
    private void redirecionarParaUpload(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Upload.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Tela de Upload");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ação ao clicar em "Novo? Registre-se"
    @FXML
    private void handleIrParaRegistro(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Registro.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registro de Usuário");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível carregar a tela de registro.");
        }
    }

    // Utilitário para verificar formato de email
    private boolean isEmailValido(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    // Utilitário para mostrar alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}