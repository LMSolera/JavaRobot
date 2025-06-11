package com.lpn3.licitamatch.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    
    @Override 
    public void start(Stage primaryStage) {
        try {
            // 1. Carrega o arquivo FXML (a View)
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Upload.fxml")); 

            // 2. Cria a Cena
            Scene scene = new Scene(root);

            // 3. Configura o Palco (a janela principal)
            primaryStage.setTitle("Tela de upload");
            primaryStage.setScene(scene);
            
            // 4. Mostra a janela
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main (String[] args){
        launch(args);
    }
}
