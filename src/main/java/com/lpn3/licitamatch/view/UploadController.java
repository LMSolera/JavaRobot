package com.lpn3.licitamatch.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UploadController implements Initializable {

    @FXML private Button botaoProposta;
    @FXML private Button botaoLicitacao;
    @FXML private Button botaoComparar;

    @FXML private Label labelProposta;
    @FXML private Label labelLicitacao;
    @FXML private Label labelResultado;

    private File file1; // Proposta
    private File file2; // Licitação

    private static final String PROPOSALS_FOLDER = "uploads/propostas";
    private static final String BIDDING_FOLDER = "uploads/licitacoes";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private String chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo");
        File selectedFile = fileChooser.showOpenDialog(getStage());
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    private void savePdfFile(String origemCaminho, String pastaDestino) {
        File origem = new File(origemCaminho);
        File pasta = new File(pastaDestino);

        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File destino = new File(pasta, origem.getName());

        try (FileInputStream in = new FileInputStream(origem);
             FileOutputStream out = new FileOutputStream(destino)) {

            byte[] buffer = new byte[1024];
            int bytesLidos;
            while ((bytesLidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLidos);
            }

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Arquivo salvo com sucesso:\n" + destino.getAbsolutePath());

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao salvar o arquivo:\n" + e.getMessage());
        }
    }

    @FXML
    private void selecionarArquivo1(ActionEvent event) {
        String caminho = chooseFile();
        if (caminho != null) {
            file1 = new File(caminho);
            labelProposta.setText(file1.getName());
            savePdfFile(file1.getAbsolutePath(), PROPOSALS_FOLDER);
        }
    }

    @FXML
    private void selecionarArquivo2(ActionEvent event) {
        String caminho = chooseFile();
        if (caminho != null) {
            file2 = new File(caminho);
            labelLicitacao.setText(file2.getName());
            savePdfFile(file2.getAbsolutePath(), BIDDING_FOLDER);
        }
    }

    @FXML
    private void compararArquivos(ActionEvent event) {
        if (file1 == null || file2 == null) {
            showAlert(Alert.AlertType.WARNING, "Atenção", "Selecione ambos os arquivos antes de comparar.");
            return;
        }

        // Chamar o metodo que envia os arquivos para IA e etc
        labelResultado.setText("Comparação simulada entre os arquivos:\n" +
                file1.getName() + " e " + file2.getName());
    }

    private void showAlert(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private Stage getStage() {
        return (Stage) botaoProposta.getScene().getWindow();
    }
}
