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
    
    private void savePDF (String origemCaminho, String pastaDestino){
        File origem = new File(origemCaminho);
        File pastaDestino = new File(PastaDestino);
        
        if(!pastaDestino.exists()){
            pastaDestino.mkdirs();
        }
        
        
    }
    
    

    
    
}
