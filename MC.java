import java.io.FileNotFoundException;

/**
 * a solution to the actor representative graph problem
 * 
 * @author John
 */
public class MC {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Menu menu = new Menu();
        menu.run();
        System.out.println("The program is now calcualting cheapest route");
    }
}
