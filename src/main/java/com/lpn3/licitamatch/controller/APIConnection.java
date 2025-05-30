package com.lpn3.licitamatch.controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.io.StringReader;
import java.time.Duration;
import java.util.Map;


// API Key DP - sk-19aa9fd431f5433cb280b598ece1a461
// API Key OR - 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

public class APIConnection {
    public static void main(String[] args) {
        String openRouterApiKey = "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";
        String yourSiteUrl = null; // Opcional
        String yourAppName = null; // Opcional
        
        String openRouterApiUrl = "https://openrouter.ai/api/v1/chat/completions";

        String jsonInputString = """
        {
          "model": "deepseek/deepseek-r1-0528:free",
          "messages": [
            {"role": "system", "content": "Você é um profesor"},
            {"role": "user", "content": "Explique o teorema de pitágoras dentro de 350 caracteres"}
          ],
          "max_tokens": 500,
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

        if (yourSiteUrl != null && !yourSiteUrl.isEmpty()) {
            requestBuilder.header("HTTP-Referer", yourSiteUrl);
        }
        if (yourAppName != null && !yourAppName.isEmpty()) {
            requestBuilder.header("X-Title", yourAppName);
        }

        HttpRequest request = requestBuilder.build();

        System.out.println("Enviando requisição para OpenRouter: " + openRouterApiUrl);
        System.out.println("Cabeçalhos: " + request.headers().map());
        System.out.println("Corpo da requisição: " + jsonInputString);


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n--- Resposta do OpenRouter ---");
            System.out.println("Status Code: " + response.statusCode());
//            System.out.println("Corpo da Resposta: " + response.body());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println("Requisição bem-sucedida!");
                
                String jsonString = response.body();            
                Gson gson = new Gson();
                
                JsonReader reader = gson.newJsonReader(new StringReader(jsonString));
                
                Map<?, ?> mapaResposta = gson.fromJson(reader, Map.class);
                
                for (Map.Entry<?, ?> entry : mapaResposta.entrySet()) {
                    System.out.println (entry.getKey() + " : " + entry.getValue());
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
