import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A menu class that solves the graph problem of cheapest hire of actors. The
 * implementation of the menu class allows for more dynamic data to be used
 * rather than static data.
 * 
 * @author John
 */
public class Menu {
    // instance variables
    private final DoublyLinkedList<Double> initalCost = new DoublyLinkedList<>();// use DLL to hold inital costs uses
                                                                                 // LIFO
    private DoublyLinkedList<String> DLL = new DoublyLinkedList<>(); // holds each token of the input.txt
    private int nodes; // the number of actors
    private int target; // the target actor
    private graphEdge edgeGraph; // an initialized empty graph
    private List<Integer> path = new ArrayList<>(); // will store the shortest path taken
    private double shortestPath; // stores the total weight of shortest path

    // default constructor
    public Menu() {
    }

    /**
     * The run method calls all the methods to read the input.txt, create a directed
     * weighted edge graph, calls Dijkstra to find the shortest path.
     * 
     * @throws FileNotFoundException
     */
    public void run() throws FileNotFoundException {
        readFileDLL(); // calls the read file method
        getStartingValues(); // calls the initial values method to get base costs
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                double cost = initalCost.removeFirst(); // gets the cost to hire the actor j
                double discount = Double.parseDouble(DLL.removeFirst()); // parses the values stored as strings and
                                                                         // converts into double
                if (i == j) // if i equal j put in regular cost
                {
                    edgeGraph.addEdge(i, j, cost); // sets the actors cost to hire self aka regular cost
                } else // else use the recognition formula
                {
                    edgeGraph.addEdge(i, j, cost * (1 - discount));
                }
                initalCost.addLast(cost); // re-adds the inital cost back to the list
            }
        }
        Dijkstra dj = new Dijkstra(nodes + 1, edgeGraph); // creates a new instance of the Dijkstra class
        shortestPath = dj.dijkstra(nodes, target - 1); // sets the shortestPath to the return of the dijkstra method
        path = dj.reconstructPath(nodes, target - 1, nodes + 1, shortestPath); // sets path to the return of the
                                                                               // reconstruct method
        writeFile(); // calls the method to write the outputs to the text file
    }

    /**
     * Takes the data from the instance variables and generates the directed
     * weighted edge graph. The values are converted from String type into proper
     * Integer and Double type. The graph that is initialized will be empty and have
     * one additional node+1 node which will serve as the starting node videoStore =
     * S
     */
    public void getStartingValues() {
        target = Integer.parseInt(DLL.removeLast()); // the actor in the store wishes to hire by removing last token in
                                                     // list
        nodes = Integer.parseInt(DLL.removeFirst()); // find the number of actors by removing first token in list
        edgeGraph = new graphEdge(nodes + 1); // generates graph of size nodes+1 plus one becuase of strating node
        for (int i = 0; i < nodes; i++) // sets up the directed edges of the starting node
        {
            double baseCost = Double.parseDouble(DLL.removeFirst()); // base cost is the direct cost of hiring actors by
                                                                     // S
            initalCost.addLast(baseCost); // will act like a queue
            edgeGraph.addEdge(nodes, i, baseCost); // adds the weighted edges of starting node to graph
        }
    }

    /**
     * reads in the file from input.txt by creating a instance class of the
     * readFile. a new file is then created from the referenced file input.txt and
     * fileToDLL method is called. the return value of this method is a
     * DoublyLinkedList and the Menu class instance variable DLL is set to this
     * return.
     * 
     * @throws FileNotFoundException
     */
    public void readFileDLL() throws FileNotFoundException {
        readFile read = new readFile(); // new instance of the readFile class
        File inFile = new File("input.txt");
        DLL = read.fileToDLL(inFile); // sets the DLL to the return value of the fillToDLL
    }

    /**
     * writes the values of the shortestPath and path class instance variables to a
     * text file called output.txt by using a PrintWriter.
     */
    public void writeFile() {
        String fileName = "output.txt"; // reletive path of file
        try {
            PrintWriter outputStream = new PrintWriter(fileName); // create new instance of the PrintWriter
            outputStream.println(shortestPath); // writes the weight of shortest path
            outputStream.println(path); // writes the path taken
            outputStream.close(); // close the writer
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
