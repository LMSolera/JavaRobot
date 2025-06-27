package com.lpn3.licitamatch.view; 

import com.lpn3.licitamatch.service.UsuarioService; 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.regex.Pattern;

public class RegistroController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button registerButton;
    @FXML
    private Hyperlink goToLoginLink;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}$");

    //camada de serviço.
    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void initialize() {
    }

    @FXML 
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // 1. Validações de Tela 
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erro de Registro", "Todos os campos são obrigatórios.");
            return;
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            showAlert(Alert.AlertType.ERROR, "Erro de Registro", "O formato do e-mail é inválido.");
            return;
        }
        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Erro de Registro", "A senha deve ter no mínimo 8 caracteres.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erro de Registro", "As senhas não coincidem.");
            return;
        }

        // 2. Lógica de Negócio e Persistência (usando o Service)
        try {
            // Chama o serviço para registrar o usuário.
            // O serviço cuidará da verificação de e-mail e da criptografia.
            usuarioService.registrarNovoUsuario(username, email, password);

            showAlert(Alert.AlertType.INFORMATION, "Registro bem-sucedido", "Conta criada com sucesso! Você já pode fazer o login.");
            limparCampos();
            
            goToLoginLink(event);

        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Registro", e.getMessage());
        }
    }

    @FXML 
    private void goToLoginLink(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")); 
            Scene loginScene = new Scene(loginRoot);
            Stage stage = (Stage) goToLoginLink.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível abrir a tela de login.");
        }
    }

    private void limparCampos() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}