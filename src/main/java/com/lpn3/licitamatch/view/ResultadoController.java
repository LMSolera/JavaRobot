package com.lpn3.licitamatch.view;

import com.lpn3.licitamatch.model.Comparacao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultadoController {

    @FXML private ProgressIndicator notaIndicator;
    @FXML private Label notaLabel;
    @FXML private TextArea similaresTextArea;
    @FXML private TextArea diferentesTextArea;

    /**
     * Método público para inicializar a tela com os dados da comparação.
     * Será chamado pelo UploadController antes de exibir a cena.
     */
    public void initData(Comparacao resultado) {
        // A nota no banco é 0-100, no progress indicator é 0.0-1.0
        double progresso = resultado.getNota() / 100.0;
        notaIndicator.setProgress(progresso);
        
        notaLabel.setText(resultado.getNota() + " / 100");
        similaresTextArea.setText(resultado.getTxtSemelhanca());
        diferentesTextArea.setText(resultado.getTxtDiferenca());
    }

    @FXML
    private void handleVoltar(ActionEvent event) {
        try {
            Parent uploadRoot = FXMLLoader.load(getClass().getResource("/fxml/Upload.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(uploadRoot));
            stage.setTitle("Tela de Upload");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}