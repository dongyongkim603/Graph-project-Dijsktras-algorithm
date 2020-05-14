/**
 * A binary version of the MinIndexedDHeap by initializing MinIndexedDHeap of
 * max 2 child nodes per parent.
 */
public class MinIndexedBinaryHeap<T extends Comparable<T>> extends MinIndexedDHeap<T> {
    /**
     * constructor that passes goes to the super class of MinIndexedDHeap to set the
     * degree of the heap to 2.
     * 
     * @param maxSize
     */
    public MinIndexedBinaryHeap(int maxSize) {
        super(2, maxSize);
    }
}
