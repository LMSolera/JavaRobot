package com.lpn3.licitamatch.view;


import com.lpn3.licitamatch.model.Licitacao;
import com.lpn3.licitamatch.model.Proposta;
import com.lpn3.licitamatch.model.Comparacao;
import com.lpn3.licitamatch.model.Usuario;
import com.lpn3.licitamatch.service.ComparacaoService;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class UploadController {

    @FXML
    private Button botaoProposta;
    @FXML
    private Button botaoLicitacao;
    @FXML
    private Button botaoComparar;

    @FXML
    private Label labelProposta;
    @FXML
    private Label labelLicitacao;
    @FXML
    private Label labelResultado;

    private Proposta propostaSalva;
    private Licitacao licitacaoSalva;

    private final LicitacaoService licitacaoService = new LicitacaoService();
    private final PropostaService propostaService = new PropostaService();
    private final ComparacaoService comparacaoService = new ComparacaoService();

    @FXML
    private void selecionarArquivoLicitacao(ActionEvent event) {
        File arquivo = escolherArquivoPDF("Selecionar Arquivo de Licitação");
        if (arquivo != null) {
            try {
                Usuario usuarioLogado = UserSession.getInstance().getLoggedInUser();
                // Salva o objeto retornado pelo serviço.
                this.licitacaoSalva = licitacaoService.salvarNovaLicitacao(usuarioLogado, arquivo);
                labelLicitacao.setText(arquivo.getName());
                
                licitacaoSalva.setFilePdf(arquivo);
                
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Licitação no banco com ID: " + licitacaoSalva.getId());               
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível salvar a licitação: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void selecionarArquivoProposta(ActionEvent event) {
        File arquivo = escolherArquivoPDF("Selecionar Arquivo de Proposta");
        if (arquivo != null) {
            try {
                Usuario usuarioLogado = UserSession.getInstance().getLoggedInUser();
                // Salva o objeto retornado pelo serviço.
                this.propostaSalva = propostaService.salvarNovaProposta(usuarioLogado, arquivo);
                labelProposta.setText(arquivo.getName());
                
                propostaSalva.setFilePdf(arquivo);
                
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Proposta no banco com ID: " + propostaSalva.getId());         
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível salvar a proposta: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void compararArquivos(ActionEvent event) {
        if (licitacaoSalva == null || propostaSalva == null) {
            showAlert(Alert.AlertType.WARNING, "Atenção", "É necessário salvar uma Licitação e uma Proposta antes de comparar.");
            return;
        }

        try {
            Comparacao resultado = comparacaoService.realizarComparacao(propostaSalva, licitacaoSalva);

            // --- CÓDIGO PARA ABRIR A NOVA TELA ---
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Resultado.fxml"));
            Parent root = loader.load();

            // Pega a instância do controller da nova tela
            ResultadoController resultadoController = loader.getController();
            // Passa os dados do resultado para o controller
            resultadoController.initData(resultado);

            // Mostra a nova cena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Resultado da Análise");
            stage.show();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro na Comparação", "Ocorreu um erro ao analisar os documentos: " + e.getMessage());
            e.printStackTrace();
        }
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

    @FXML
    private void handleLogout(ActionEvent event) {
        // 1. Limpa os dados da sessão
        UserSession.getInstance().clearSession();

        // 2. Navega de volta para a tela de login
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")); 
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível retornar à tela de login.");
        }
    }
    private void showAlert(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
