package com.lpn3.licitamatch.controller;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;




public class APIConnection {
    public static void main(String[] args) {
        String openRouterApiKey = "Chave";
        
        String openRouterApiUrl = "https://openrouter.ai/api/v1/chat/completions";

        String jsonInputString = """
        {
          "model": "deepseek/deepseek-r1-0528:free",
          "messages": [
            {"role": "system", "content": "Você é um profesor"},
            {"role": "user", "content": "Em português, explique o teorema de pitágoras sem adição de "*" na resposta."}
          ],
          "max_tokens": 1000,
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
                System.err.println("Erro na requisição. Detalhes acima."); 
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao enviar a requisição HTTP para OpenRouter: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
