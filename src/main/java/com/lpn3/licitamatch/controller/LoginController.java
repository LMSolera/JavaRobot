package com.lpn3.licitamatch.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.Node;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink linkRegistro;

    //metodo para ir UPLOAD
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
    // Ação do botão de Login
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro de validação", "Por favor, preencha todos os campos.");
            return;
        }

        if (!isEmailValido(email)) {
            mostrarAlerta("Email inválido", "Digite um email válido.");
            return;
        }

// Simulação de autenticação — substitua com consulta ao banco de dados depois
        if (email.equals("admin@email.com") && senha.equals("123456")) {
            redirecionarParaUpload(event);
        } else {
            mostrarAlerta("Erro", "Credenciais inválidas.");
        }
    }

    // Ação ao clicar em "Novo? Registre-se"
    @FXML
    private void handleIrParaRegistro(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
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
