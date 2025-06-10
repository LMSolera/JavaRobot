package com.lpn3.licitamatch.view;

import java.awt.Button;
import java.awt.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.classfile.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class UploadController implements Initializable {

    //Initializes the controller class.
    
    @FXML private Button botaoProposta;
    @FXML private Button botaoLicitacao;
    @FXML private Button botaoComparar;
    @FXML private Label labelProposta;
    @FXML private Label labelArquivo;
    @FXML private Label labelResultado;

    private File file1;
    private File file2;
    private static final String PROPOSALS_FOLDER = "uploads/propostas";
    private static final String BIDDING_FOLDER = "uploads/licitacoes"
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    //escolher arquivo
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
    private void procurarArq(ActionEvent event) {
        String caminho = chooseFile();
        if (caminho != null) {
            labelArquivo1.setText(new File(caminho).getName());
            file1 = new File(caminho);
        }
    }

    @FXML
    private void enviarLicitacao(ActionEvent event) {
        if (file1 != null) {
            savePdfFile(file1.getAbsolutePath(), BIDDING_FOLDER);
        } else {
            showAlert(Alert.AlertType.WARNING, "Atenção", "Nenhum arquivo de licitação selecionado.");
        }
    }

    @FXML
    private void procurarProposta(ActionEvent event) {
        String caminho = chooseFile();
        if (caminho != null) {
            labelArquivo2.setText(new File(caminho).getName());
            file2 = new File(caminho);
        }
    }

    @FXML
    private void enviarProposta(ActionEvent event) {
        if (file2 != null) {
            savePdfFile(file2.getAbsolutePath(), PROPOSALS_FOLDER);
        } else {
            showAlert(Alert.AlertType.WARNING, "Atenção", "Nenhum arquivo de proposta selecionado.");
        }
    }

    private void showAlert(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private Stage getStage() {
        return (Stage) botaoArquivo1.getScene().getWindow();
    }
}
    
    

    
    
}
