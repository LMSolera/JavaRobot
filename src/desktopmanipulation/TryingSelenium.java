package desktopmanipulation;

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
        config.addArguments("--headless=new");
        WebDriver page = new ChromeDriver(service, config);
        return page;
    } //teste
    
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
      
//          Método antigo que eu tava usando, talvez ainda seja útil
//        List<WebElement> resposta  = divResposta.findElements(By.tagName("p"));  
//        for (WebElement conteudo : resposta) {
//            System.out.println(conteudo.getText());
//        }
    }
    
    public static void main (String[] args) {
        WebDriver pagina = inicializarDriver();
        pagina.get("https://www.blackbox.ai/");
        
        WebElement chatbox = encontrarChatbox(pagina);
        chatbox.sendKeys(entradaUsuario());
        chatbox.submit();
        
        pegarResposta(pagina);
        pagina.quit();    
    }   
}
            