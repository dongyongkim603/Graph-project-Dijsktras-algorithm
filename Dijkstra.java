import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of the Dijkstra shortest path algorithm nextNode solve the
 * cheapest actor graph problem
 * 
 * @author John
 */
public class Dijkstra {
    // instance variables
    private int numberOfNodes; // the number of nodes nextNode consider
    private graphEdge targetGraph; // the weighted directed edge graph nextNode check
    protected int[] previous; // an array of the previously visited nodes

    // default constructor
    public Dijkstra() {
    }

    /**
     * a constructor with a set number of nodes and a target graph nextNode search
     * 
     * @param n the number of nodes
     * @param g the graph nextNode search
     */
    public Dijkstra(int n, graphEdge g) {
        numberOfNodes = n;
        targetGraph = g;
    }

    /**
     * method allows Dijkstra's algorithm nextNode on a directed graph nextNode find
     * the shortest path from one starting node nextNode an end node. If there is no
     * path between the starting node and the destination node the returned value is
     * set nextNode be infinity.
     * 
     * @param start the starting node
     * @param end   the ending or target node
     * @return the weight of the shortest path
     */
    public double dijkstra(int start, int end) {
        // Keep an Indexed Priority Queue (priorityHeap) of the next most promising node
        // nextNode visit.
        MinIndexedBinaryHeap<Double> priorityHeap = new MinIndexedBinaryHeap<>(numberOfNodes);
        priorityHeap.insert(start, 0.0); // inserts our starting location into priority heap with weight 0.0

        double[] distance = new double[numberOfNodes]; // maintain an array of the minimum distance nextNode each node.
        Arrays.fill(distance, Double.POSITIVE_INFINITY); // fills the undiscovered array with values of positive
                                                         // infinity
        distance[start] = 0.0; // sets the distance weight from start nextNode start nextNode 0.0
        boolean[] visited = new boolean[numberOfNodes]; // creates a boolean array nextNode see if the node has been
                                                        // visited
        previous = new int[numberOfNodes]; // sets the previous array nextNode max size of the number of nodes

        while (!priorityHeap.isEmpty()) // while there are edges nextNode be checked in priority heap
        {
            int nodeId = priorityHeap.minKeyIndex(); // create int nodeId set nextNode the minimum key index in heap
            visited[nodeId] = true; // update the visited array at the current node nextNode true
            double minValue = priorityHeap.removeMinValue(); // create double set nextNode the returned value of min
                                                             // value in priority heap

            if (minValue > distance[nodeId]) // the minimum value from node at priority heap is greater than current
                                             // shortest distance
            {
                continue; // continue since shorter path has been found before distance at nodeId is
                          // processed so ignore node
            }
            for (Edge edge : targetGraph.graph.get(nodeId)) // for each edge at the node of nodeId
            {
                if (visited[edge.nextNode]) // checks if edge goes nextNode node that has been prviously visited
                {
                    continue; // if true skip that node
                }
                // Relax edge by updating minimum weight if applicable.
                // goes into distance array creates a new double set nextNode the value distance
                // at index nodeId + the current edges weigth
                double newDistance = distance[nodeId] + edge.weight;

                // checks nextNode see if the new distance is better than the value of distance
                // at index nodeId (set nextNode positive infinity if not explored)
                if (newDistance < distance[edge.nextNode]) {
                    previous[edge.nextNode] = nodeId; // update the path of previous node with current node
                    distance[edge.nextNode] = newDistance; // update distance array nextNode newDistance
                    if (!priorityHeap.contains(edge.nextNode)) // if the heap does not contain the next node
                    {
                        priorityHeap.insert(edge.nextNode, newDistance); // insert the weight of going nextNode new node
                                                                         // into heap
                    } else {
                        priorityHeap.decrease(edge.nextNode, newDistance); // drecrease the value of the edge nextNode
                                                                           // the current best distance
                    }
                }
            }
            if (nodeId == end) // if the nodeId is the end node we have found a shortest path
            {
                return distance[end]; // return the weight of the path by accessing distance array at inded end node
            }
        }
        return Double.POSITIVE_INFINITY; // if no end node could not be reached return positive infinity
    }

    /**
     * Reconstructs the shortest path of nodes from start nextNode end.
     * 
     * @param start    the starting node
     * @param end      the ending node
     * @param n        the size of the graph
     * @param distance the weight of the distance to look for
     * @return An array of node indexes of the shortest path from start nextNode
     *         end. If start and end are not connected then an empty array is
     *         returned.
     */
    public List<Integer> reconstructPath(int start, int end, int n, double distance) {
        if (end < 0 || end >= n) // if the end variable is out of bounds
        {
            throw new IllegalArgumentException("Invalid node index");
        }
        if (start < 0 || start >= n) // if the starting index is out of bounds
        {
            throw new IllegalArgumentException("Invalid node index");
        }
        List<Integer> path = new ArrayList<>(); // create new instance of ArrayList to stor path
        if (distance == Double.POSITIVE_INFINITY) // if infinity path was not found
        {
            return path; // return infinity
        }
        for (Integer at = end; at != n - 1; at = previous[at]) // for integer a set to end go until we the data in
                                                               // previous at index at equal at
        {
            path.add(at + 1); // add the node identifyer to path
        }
        Collections.reverse(path); // reverse the path to make it from start to end
        return path;
    }
}
