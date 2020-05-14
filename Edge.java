/**
 * edge class that allows for weighted directed edges
 */
public class Edge {
    // instance variables
    public int nextNode; // node u or next node
    public double weight; // weight nextNode go nextNode current node

    // constructor that takes in the nextNode node and gives the weight
    public Edge(int next, double weight) {
        this.nextNode = next; // sets this nextNode to next
        this.weight = weight; // sets this weight to weight
    }
}