package com.lpn3.licitamatch.controller;

import com.google.gson.Gson;
import com.lpn3.licitamatch.model.Comparacao;
import com.lpn3.licitamatch.model.Licitacao;
import com.lpn3.licitamatch.model.Proposta;

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
    
    public static Comparacao compararDocumentos(Proposta arquivoProposta, Licitacao arquivoLicitacao) {
        String openRouterApiKey = "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";
        
        String openRouterApiUrl = "https://openrouter.ai/api/v1/chat/completions";
        
        proposta = arquivoProposta.getFilePdf();
        licitacao = arquivoLicitacao.getFilePdf();
        
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
                                                        
                                                        
                                                        
                                 Realize uma simples análise da seguinte proposta:
                                
                                 """ + dadosProposta + """   
                                                       
                                                       
                                                                                                        
                                 Sua resposta deve conter dois textos (Semelhanças e Diferenças) e uma nota (0 a 100), que deve considerar tais características:
                                    0 - 30 : Com base nas diferenças de custo.
                                    0 - 30 : Com base nas diferenças de produto esperado, e produto ofertado.
                                    0 - 20 : Com base na data de entrega esperada e data de entrega ofertada.
                                    0 - 20 : Com base na estrutura da proposta.                                          
                                 Todas essas informações devem estar organizadas em formato JSON, com os seguintes objetos:
                                    semelhancas: (Um TEXTO explicando as semelhanças)
                                    diferencas: (Um TEXTO explicado as diferenças)
                                    nota: (Valor de 0 a 100)
                                 Sua resposta deve conter SOMENTE o conteúdo do arquivo JSON que não deve ultrapassar 1000 caracteres."}
          ],
          "max_tokens": 2000,
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
                
                String conteudoRespostaJson = "";
                for (Map.Entry<?, ?> entry : mapaRespostaCompleta.entrySet()) {
                    if (entry.getKey().toString().equals("choices")) {
                        String[] conteudo = entry.getValue().toString().split("content=");
                        conteudo = conteudo[1].split("Caracteres:");
                        conteudo = conteudo[0].split(", refusal=null");
                        conteudoRespostaJson = conteudo[0];
                        conteudo = conteudo[1].split("reasoning=");
                        conteudo = conteudo[1].split("}}]");
                    }
                }
                
                System.out.println(conteudoRespostaJson);
                conteudoRespostaJson = conteudoRespostaJson.replace("```json", "");
                conteudoRespostaJson = conteudoRespostaJson.replace("```", "");
                System.out.println(conteudoRespostaJson);
                
                Map<?, ?> mapaRespostaJson = gson.fromJson(conteudoRespostaJson, Map.class);
                
                String semelhanca;
                String diferenca;
                float nota;
                
                int contador = 0;
                String[] temp = new String[mapaRespostaJson.size()];
                for (Map.Entry<?, ?> entry : mapaRespostaJson.entrySet()) {
                    temp[contador] = entry.toString().split("=")[1];
                    contador++;
                }
                
                semelhanca = temp[0];
                diferenca = temp[1];
                nota = Float.parseFloat(temp[2]);
                
                System.out.println(semelhanca + "\n");
                System.out.println(diferenca + "\n");
                System.out.println(nota + "\n");
                
                Comparacao tempComp = new Comparacao();
                tempComp.setTxtSemelhanca(semelhanca);
                tempComp.setTxtDiferenca(diferenca);
                tempComp.setNota((int)nota);
                return tempComp;
            } else {
                System.err.println("Erro na requisição.");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao enviar a requisição HTTP para OpenRouter: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
