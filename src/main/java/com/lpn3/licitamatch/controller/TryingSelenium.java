package com.lpn3.licitamatch.controller;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver; 
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class TryingSelenium {
    public static WebDriver inicializarDriver () {
        File driverPath = new File("Drivers\\chromedriver.exe");
        ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(driverPath).build();
        ChromeOptions config = new ChromeOptions();
//        config.addArguments("--headless=new"); 
//        deixar desligado por enquanto por motivos de teste
        WebDriver page = new ChromeDriver(service, config);
        return page;
    }
    
    public static WebElement encontrarChatbox (WebDriver pagina) {
        pagina.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        WebElement temp = pagina.findElement(By.id("chat-input-box"));
        return temp;
    }
    
    public static String entradaUsuario () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira sobre o que quer saber: ");
        String entrada = scanner.nextLine();
        return entrada;
    }
    
    public static void pegarResposta (WebDriver pagina) {
        pagina.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        List<WebElement> divResposta = pagina.findElements(By.xpath("/html/body/div[2]/main/div/div[2]/div[2]/div/div/div/div/div/div/div/div[1]/div/div/div[2]/div[2]/div"));
        pagina.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        divResposta = pagina.findElements(By.cssSelector("div[class='prose break-words dark:prose-invert prose-p:leading-relaxed prose-pre:p-0 fix-max-with-100']"));
        System.out.println("Prompt: " + divResposta.get(0).getText());
        System.out.println("Resposta: " + divResposta.get(1).getText());
    }
    
    public static void main (String[] args){
        WebDriver pagina = inicializarDriver();
        pagina.get("https://www.blackbox.ai/");
//        pagina.manage().window().minimize();

        pagina.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pagina.findElement(By.xpath("/html/body/div[4]/button")).click();
        
        pagina.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pagina.findElement(By.xpath("/html/body/div[2]/main/div/div[2]/div[2]/div/div/div/div/div/div/div[1]/div[1]/div/div/div/div/div[1]/div/div[3]/div[1]/div/form/div[4]/div[2]/div[1]/button[4]")).click();
        pagina.findElement(By.xpath("/html/body/div[3]/div/div[2]")).click();
        pagina.findElement(By.xpath("/html/body/div[4]/a/div")).click();
        pagina.findElement(By.xpath("/html/body/div[4]/form/div/div[2]/div[1]/div/input")).sendKeys("placeholder@gmail.com");
        pagina.findElement(By.xpath("/html/body/div[4]/form/div/div[2]/div[2]/div/input")).sendKeys("PLACEholder1234");
        pagina.findElement(By.xpath("/html/body/div[4]/form/div/button[2]")).click();
        
        pagina.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        for (int i = 0; i < 4; i++) {
            try {
                pagina.findElement(By.xpath("/html/body/div[2]/main/div/div[2]/div[2]/div/div/div/div/div/div/div[1]/div[1]/div/div/div/div/div[1]/div/div[3]/div[1]/div/form/div[4]/div[2]/div[1]/button[4]")).click();
                pagina.findElement(By.xpath("/html/body/div[3]/div/div[2]")).click();
                break;
            } catch (Exception e) {}
        } // Continuar trabalhando daqui pra frente, pra conseguir enviar arquivos
        
        // Importante:
        // Alterar a forma como arquivos são guardados para demonstrar se são propostas ou solicitações no nome
        // Pois a plataforma, quande recebe um diretório completo, ignora hierarquia de pastas, e apenas observa 
        // os arquivos diretamente.

        
        WebElement chatbox = encontrarChatbox(pagina);
        String prompt = "Com o objetivo de realizar diversas análises comparativas, a partir dos arquivos presentes no diretório 'Licitações' "
                + "e dos arquivos presentes no diretório 'Propostas', compare (1 para 1) cada licitação com cada proposta, e responda, em "
                + "formato JSON sem a adição de tags HTML seguindo o seguinte formato de objetos: 'nome_licitacao', "
                + "'nome_proposta', 'semelhancas', 'diferencas', 'nota'. A nota deve varia de 0 a 100 (Quanto maior, mais semelhante). Não "
                + "responda nada além disso.";
        chatbox.sendKeys(prompt);
        chatbox.submit();
 
        pegarResposta(pagina);
        pagina.quit();    
    }   
}
            