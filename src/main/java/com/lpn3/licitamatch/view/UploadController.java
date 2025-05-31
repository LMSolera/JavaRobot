package com.lpn3.licitamatch.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    private void selecionarArquivoProposta(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo 1");
        File selectedFile = fileChooser.showOpenDialog(getStage());

        if (selectedFile != null) {
            file1 = selectedFile;
            labelArquivo1.setText(selectedFile.getName());
            
            File destinationFolder = new File(./sentFiles)
        
        }
    }
    
}
