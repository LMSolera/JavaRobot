package com.lpn3.licitamatch.controller;

import com.google.gson.Gson;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


public class APIConnection {
    
    static File licitacao = null;
    static File proposta = null;
    static String dadosProposta;
    static String dadosLicitacao;
    
    public static void compararDocumentos(File arquivoProposta, File arquivoLicitacao) {
        String openRouterApiKey = "Chave";
        
        String openRouterApiUrl = "https://openrouter.ai/api/v1/chat/completions";
        
        proposta = arquivoProposta;
        licitacao = arquivoLicitacao;
        
        try {
            PDDocument documentoProposta;
            documentoProposta = PDDocument.load(proposta);
            PDFTextStripper stripperDeTexto = new PDFTextStripper();
            dadosProposta = stripperDeTexto.getText(documentoProposta);
            documentoProposta.close();
        } catch (Exception e) {
            System.out.println("Excecao ao tentar ler conteúdo da Proposta: " + e);
            e.printStackTrace();
        }
        
        try {
            PDDocument documentoLicitacao;
            documentoLicitacao = PDDocument.load(licitacao);
            PDFTextStripper stripperDeTexto = new PDFTextStripper();
            dadosLicitacao = stripperDeTexto.getText(documentoLicitacao);
            documentoLicitacao.close();
        } catch (Exception e) {
            System.out.println("Excecao ao tentar ler conteúdo da Licitacao: " + e);
            e.printStackTrace();
        }
        
        String jsonInputString = """
        {
          "model": "deepseek/deepseek-r1-0528:free",
          "messages": [
            {"role": "system", "content": "Você tem como objetivo comparar documentos de maneira crítica e analítica"},
            {"role": "user", "content": 
                                 "Com base nessa licitação:
                                 """ + dadosLicitacao + """                             
                                 Realize a análise da seguinte proposta:
                                 """ + dadosProposta + """                       
                                 Ao final da análise, você deve avaliar a proposta trazendo as seguintes explicações:
                                    Características Similares:
                                    Características Diferentes:
                                    Nota de análise de 0 - 10:
                                 A nota deve considerar tais características:
                                    0 - 3 : Com base nas diferenças de custo.
                                    0 - 3 : Com base nas diferenças de produto esperado, e produto ofertado.
                                    0 - 2 : Com base na data de entrega esperada e data de entrega ofertada.
                                    0 - 2 : Com base na estrutura da proposta.                                          
                                 "}
          ],
          "max_tokens": 5000,
          "temperature": 0.7
        }
        """;
        
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(15)) // Timeout para conexão
                .build();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(openRouterApiUrl))
                .timeout(Duration.ofSeconds(45))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + openRouterApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonInputString));

        HttpRequest request = requestBuilder.build();

        System.out.println("Enviando requisição para OpenRouter: " + openRouterApiUrl);
        System.out.println("Cabeçalhos: " + request.headers().map());
        System.out.println("Corpo da requisição:\n" + jsonInputString);


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n--- Resposta do OpenRouter ---");
            System.out.println("Status Code: " + response.statusCode());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println("Requisição bem-sucedida!");
                
                String jsonString = response.body();            
                Gson gson = new Gson();
                
                Map<?, ?> mapaRespostaCompleta = gson.fromJson(jsonString, Map.class);
                               
                for (Map.Entry<?, ?> entry : mapaRespostaCompleta.entrySet()) {
                    if (entry.getKey().toString().equals("choices")) {
                        String[] conteudo = entry.getValue().toString().split("content=");
                        conteudo = conteudo[1].split("Caracteres:");
                        conteudo = conteudo[0].split(", refusal=null");
                        System.out.println("Resposta: \n" + conteudo[0]);
                        conteudo = conteudo[1].split("reasoning=");
                        conteudo = conteudo[1].split("}}]");
                        System.out.println("\nRaciocínio: \n" + conteudo[0]);
                    }
                }
                
            } else {
                System.err.println("Erro na requisição."); 
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao enviar a requisição HTTP para OpenRouter: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
