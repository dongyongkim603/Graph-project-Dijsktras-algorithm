import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class to read in .txt files.
 * 
 * @author John
 */
public class readFile {
    // instance variable
    private Scanner instanceScanner; // new scanner object

    /**
     * sets the scanner object to target the newly created file object. May throw
     * FileNotFoundException
     */
    public void openFile(String name) {
        try {
            instanceScanner = new Scanner(new File(name)); // sets the scanner to new scanner with new file object as
                                                           // parameter
        } catch (FileNotFoundException e) // file was not found
        {
            System.out.println("file not found");
        }
    }

    /**
     * creates a String array of each token found in the .txt file.
     * 
     * @param file the target file to be read in
     * @return a String array of each token read in from file
     */
    public String[] readFile(File file) {
        String[] output = new String[(int) file.length()]; // create String array of size file length
        int i = 0; // index variable
        while (instanceScanner.hasNextLine()) // while the scanner has more to read
        {
            String a = instanceScanner.next(); // set String a to the next found by scanner
            System.out.printf("%s", a);
            output[i] = a; // output at index i is set to String a
            i++; // increment i
        }
        return output; // return the String array
    }

    /**
     * makes sure to close the file I/O object
     */
    public void closeFile() {
        instanceScanner.close();
    }

    // ------------------------------------------------------------------------------

    /**
     * takes the input from the input file and converts each token into a DLL of
     * Strings
     * 
     * @param file the input file
     * @return a DLL of Strings
     * @throws FileNotFoundException
     */
    public DoublyLinkedList<String> fileToDLL(File file) throws FileNotFoundException {
        instanceScanner = new Scanner(file); // sets instanceScanner to new scanner with file passed in
        DoublyLinkedList<String> output = new DoublyLinkedList<>(); // creates new String DLL
        while (instanceScanner.hasNextLine()) // while the scanner has more to read
        {
            String a = instanceScanner.next(); // set String a to the next found by scanner
            output.addLast(a); // adds int String to DLL
        }
        return output; // return the DLL
    }

}
