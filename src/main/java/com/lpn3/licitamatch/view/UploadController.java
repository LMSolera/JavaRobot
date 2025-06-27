package com.lpn3.licitamatch.view;

import com.lpn3.licitamatch.model.Usuario;
import com.lpn3.licitamatch.service.LicitacaoService;
import com.lpn3.licitamatch.service.PropostaService; // Importa o serviço de Proposta
import com.lpn3.licitamatch.session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class UploadController {

    @FXML private Button botaoProposta;
    @FXML private Button botaoLicitacao;
    @FXML private Button botaoComparar;

    @FXML private Label labelProposta;
    @FXML private Label labelLicitacao;
    @FXML private Label labelResultado;

    private File arquivoProposta;
    private File arquivoLicitacao;

    // Instancia os serviços necessários.
    private final LicitacaoService licitacaoService = new LicitacaoService();
    private final PropostaService propostaService = new PropostaService(); // Instancia o serviço de Proposta

    @FXML
    private void selecionarArquivoLicitacao(ActionEvent event) {
        File arquivo = escolherArquivoPDF("Selecionar Arquivo de Licitação");
        
        if (arquivo != null) {
            try {
                // 1. Pega o usuário que está logado a partir da sessão.
                Usuario usuarioLogado = UserSession.getInstance().getLoggedInUser();
                
                // 2. Chama o serviço para orquestrar o salvamento.
                licitacaoService.salvarNovaLicitacao(usuarioLogado, arquivo);
                
                // 3. Atualiza a interface se tudo deu certo.
                this.arquivoLicitacao = arquivo;
                labelLicitacao.setText(arquivo.getName());
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Arquivo de Licitação salvo no banco de dados!");

            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Arquivo", "Não foi possível ler o arquivo selecionado.");
                e.printStackTrace();
            } catch (RuntimeException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Sistema", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void selecionarArquivoProposta(ActionEvent event) {
        File arquivo = escolherArquivoPDF("Selecionar Arquivo de Proposta");
        
        if (arquivo != null) {
            try {
                // 1. Pega o usuário que está logado.
                Usuario usuarioLogado = UserSession.getInstance().getLoggedInUser();
                
                // 2. Chama o serviço de PROPOSTA para salvar o arquivo.
                propostaService.salvarNovaProposta(usuarioLogado, arquivo);
                
                // 3. Atualiza a interface.
                this.arquivoProposta = arquivo;
                labelProposta.setText(arquivo.getName());
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Arquivo de Proposta salvo no banco de dados!");

            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Arquivo", "Não foi possível ler o arquivo selecionado.");
                e.printStackTrace();
            } catch (RuntimeException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Sistema", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void compararArquivos(ActionEvent event) {
        if (arquivoLicitacao == null || arquivoProposta == null) {
            showAlert(Alert.AlertType.WARNING, "Atenção", "Selecione ambos os arquivos antes de comparar.");
            return;
        }
        // Sua lógica de comparação aqui...
        labelResultado.setText("Comparação entre:\n" + arquivoLicitacao.getName() + "\n" + arquivoProposta.getName());
    }
    
    /**
     * Método auxiliar para abrir o FileChooser e evitar repetição de código.
     */
    private File escolherArquivoPDF(String titulo) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(titulo);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        return fileChooser.showOpenDialog(getStage());
    }

    private Stage getStage() {
        return (Stage) labelLicitacao.getScene().getWindow();
    }

    private void showAlert(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
