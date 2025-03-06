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
    
    private static Robot instantiateRobot (Robot robot) {
        try {
            robot = new Robot();
            return robot;
        } catch (Exception e) {
            System.out.println ("Deu errado com o roboto: " + e + "\n");
            return null;
        }
    }
    
    private static void runSearch (URI url) {
        try {
            Desktop desk = null;
            desk = desk.getDesktop();
            desk.browse(url);

        } catch (Exception e) {
            System.out.println ("Deu errado: " + e + "\n");
            System.out.println ("URI attempt: " + url + "\n");
            System.out.println("Aperte algo para continuar...");
            new java.util.Scanner(System.in).nextLine();
        }
    }
    
    private static void typeText (String text) {
        try {
            Robot robot = new Robot();
            robot.delay(2000);
            String sample = "Em português, me dê descrição completa e bem estruturada sobre a seguinte palavra: \"" + text + "\"" + ". Se possível, me passe fontes para sua descrição.";
            StringSelection stringSelection = new StringSelection(sample);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
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
    
    private static String getUserInput () {
        System.out.print ("Defina uma palavra: ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    
    public static void main(String[] args) {
        URI url = null;
        String userInput = getUserInput();
        url = url.create("https://www.blackbox.ai/");
        runSearch(url);
        typeText(userInput);
        Robot roboto = null;
        roboto = instantiateRobot(roboto);
        roboto.keyPress(KeyEvent.VK_ENTER);
        roboto.keyRelease(KeyEvent.VK_ENTER);
    }       
}
    