package desktopmanipulation;

import java.awt.Desktop;
import java.awt.Robot;
import java.net.URI;
import java.awt.Toolkit;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;


public class DesktopManipulation {
    
    private static Robot instanciarRobo (Robot robot) {
        try {
            robot = new Robot();
            return robot;
        } catch (Exception e) {
            System.out.println ("Deu errado com o roboto: " + e + "\n");
            return null;
        }
    }
    
    private static void runSearch (URI url) { 
        if(Desktop.isDesktopSupported()){ //conferir se o sistema suporta
            Desktop desk = null;
            desk = Desktop.getDesktop();  //cria meio que a interface para que o java mexa com o sistema operacional
            try {

                desk.browse(url); //inicia o navegador padrão na url
            } catch (Exception e) {
                System.out.println ("Deu errado: " + e + "\n");
                System.out.println ("URI attempt: " + url + "\n");
                System.out.println("Aperte algo para continuar...");
                Scanner hold = new java.util.Scanner(System.in);
                hold.nextLine();
                hold.close();
            }
        }else{
            System.out.println("Desktop não é suportado neste sistema.");
        }
    }
    
    private static void escreverTexto (String text) {
        try {
            Robot robot = new Robot();
            robot.delay(5000); // depois de instanciar, delay de 5 seg
            String sample = "Em português, me dê descrição completa e bem estruturada sobre a seguinte palavra: \"" + text + "\"" + ". Se possível, me passe fontes para sua descrição.";
            StringSelection stringSelection = new StringSelection(sample); //cria um objeto que contém o texto para transferir para a área de transferência
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //retorna a área de transferencia do sistema
            clipboard.setContents(stringSelection, stringSelection); //coloca os dados da string no clipboard
            //robo apertando os botões
            robot.keyPress(KeyEvent.VK_ALT);
            robot.delay(300);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.delay(300);
            robot.keyPress(KeyEvent.VK_X);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_X);
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);      
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch  (Exception e) {
            System.out.println("Deu errado com o roboto: " + e + "\n");
        }
    }
    
    // pega a palavra que o usuario digita
    private static String getUserInput () {
        System.out.print ("Defina uma palavra: ");
        Scanner scan = new Scanner(System.in);
        String entrada = scan.nextLine();
        scan.close();
        return entrada;
    }
    
    public static void main(String[] args) {
        URI url = null;  // URI = tipo url
        String userInput = getUserInput(); 
        url = URI.create("https://www.blackbox.ai/"); 
        runSearch(url);
        escreverTexto(userInput);
        Robot roboto = null;
        roboto = instanciarRobo(roboto); //instancia o roboto
        roboto.keyPress(KeyEvent.VK_ENTER);
        roboto.keyRelease(KeyEvent.VK_ENTER);
    }       
}
    