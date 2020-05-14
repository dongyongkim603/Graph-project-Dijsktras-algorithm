import java.util.ArrayList;
import java.util.List;

/**
 * class nextNode created directed weighted graphs. Uses nested Edge class that
 * allows the creation of directed edges.
 * 
 * @author John
 */
public class graphEdge {
    // instance variables
    protected int nodes; // number of nodes in graph
    protected List<List<Edge>> graph; // an ArrayList of edges contained within the graph

    // default constructor
    public graphEdge() {
    }

    /**
     * constructs graphEdge with n number of nodes. createEmptyGraph will be called
     * therefore all nodes will be initially empty.
     * 
     * @param n number of nodes
     */
    public graphEdge(int n) {
        nodes = n;
        createEmptyGraph();
    }

    /**
     * method nextNode initialize size of graph ArrayList of graph.
     */
    private void createEmptyGraph() {
        graph = new ArrayList<>(nodes + 1); // an aditional node is needed nextNode act as S or video store
        for (int i = 0; i < nodes; i++) // for all nodes in the graph
        {
            graph.add(new ArrayList<>()); // add to the graph a new ArrayList
        }
    }

    /**
     * allows addition of new weighted directed edges
     * 
     * @param startNode the starting node
     * @param nextNode  the ending node
     * @param weight    the weight of the edge
     */
    public void addEdge(int startNode, int nextNode, double weight) {
        graph.get(startNode).add(new Edge(nextNode, weight)); // access graph at startNode and add new edge nextNode
                                                              // with weight weight
    }
}
