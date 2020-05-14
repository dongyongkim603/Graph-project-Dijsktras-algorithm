import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.NoSuchElementException;

/**
 * Class of priority heap. Uses nodeDegree number of child nodes to improve
 * speed of search methods
 * 
 * @param <T> Generic type allows for class objects to be used as input data and
 *            compared
 */
public class MinIndexedDHeap<T extends Comparable<T>> {

    // instance variables
    private int currentNumNodes; // current number of elements in the heap.
    private final int maxElements; // maximum number of elements in the heap.
    private final int nodeDegree; // the degree of every node in the heap.
    private final int[] child; // array of child nodes
    private final int[] parent; // array of parent nodes

    // The Position Map positionMap maps Key Indexes key to where the position of
    // that key is represented in the priority heap
    public final int[] positionMap;
    public final int[] inverseMap; // The Inverse Map stores the indexes of the keys in the range

    // The values associated with the keys. It is very important to note
    // that this array is indexed by the key indexes.
    public final Object[] values;

    /**
     * constructor that initializes the heap with a maximum capacity of maxSize. and
     * a degree of child nodes. Allows for the initialization of instance variable
     * arrays to the maxSize passed in.
     */
    public MinIndexedDHeap(int degree, int maxSize) {
        if (maxSize <= 0) // if maxSize is negitive
        {
            throw new IllegalArgumentException("maxSize <= 0"); // throw IllegalArgumentException
        }
        nodeDegree = max(2, degree); // nodeDegree is calculated by max 2 and input degree
        maxElements = max(nodeDegree + 1, maxSize); // maxElement of heap is caluclated by max degree+1 by input maxSize
        inverseMap = new int[maxElements]; // inverseMap is set to size of maxElements
        positionMap = new int[maxElements]; // positionMap is set to size of maxElements
        child = new int[maxElements]; // the child array is set size of maxElements
        parent = new int[maxElements]; // parent array is set to size of maxElements
        values = new Object[maxElements]; // value array is set to size maxElements
        for (int i = 0; i < maxElements; i++) // for all the elements in the array
        {
            // fill parent array with i - 1 by degree. This allows for optimization of
            // access time and proper mapping
            parent[i] = (i - 1) / nodeDegree;
            // fill child array with the product of iand degree+1. This allows for
            // optimization of access time and proper mapping
            child[i] = i * nodeDegree + 1;
            positionMap[i] = inverseMap[i] = -1; // create reverse map of positions which allows for faster swaps
        }
    }

    /**
     * @return the size of the heap
     */
    public int size() {
        return currentNumNodes;
    }

    /**
     * @return if the heap is empty
     */
    public boolean isEmpty() {
        return currentNumNodes == 0;
    }

    /**
     * checks to see if the passed in key is within the heap. May call
     * keyInBoundsOrThrow if the key is outside of the array bounds
     * 
     * @param key the key to be looked up
     * @return true if heap contains key else return false
     */
    public boolean contains(int key) {
        keyInBoundsOrThrow(key); // if key is outside bounds throw Excpetion
        return positionMap[key] != -1; // return index of key
    }

    /**
     * checks but does not remove the minimum key within the head
     * 
     * @return the minimum key
     */
    public int minKeyIndex() {
        isNotEmptyOrThrow(); // if the heap is empty
        return inverseMap[0];
    }

    /**
     * calls the minKeyIndex to find min key then uses key to remove the minimum
     * key.
     * 
     * @return the minimum key
     */
    public int removeMinKeyIndex() {
        int minKey = minKeyIndex();
        delete(minKey);
        return minKey;
    }

    /**
     * checks but does not remove the heap for the minimum value
     * 
     * @return the minimum value in heap
     */
    @SuppressWarnings("unchecked")
    public T minValue() {
        isNotEmptyOrThrow(); // may throw Exception if heap is empty
        return (T) values[inverseMap[0]]; // the index of the min value
    }

    /**
     * Calls the minValue to find minimum value within the heap. This value is then
     * removed from the heap
     * 
     * @return the removed value
     */
    public T removeMinValue() {
        T minValue = minValue(); // call to minValue
        delete(minKeyIndex()); // call to delete
        return minValue; // return the removed value data
    }

    /**
     * Inserts a new key value pair within the heap. The newly added key and value
     * are checked to see if they are an existing pair within heap. If not the key
     * is used to update the positionMap, currentNumNodes, inverseMap, and values
     * arrays. the
     * 
     * @param key   the key to be inserted
     * @param value the generic value to be inserted
     */
    public void insert(int key, T value) {
        if (contains(key)) // if key is already in the heap throw Exception
        {
            throw new IllegalArgumentException("index already exists; received: " + key);
        }
        valueNotNullOrThrow(value); // check if value is in the heap already
        positionMap[key] = currentNumNodes; // set the position at key to the end
        inverseMap[currentNumNodes] = key; // sets the inverse at the end to key
        values[key] = value; // sets the value at position key to the passed in value
        pushUp(currentNumNodes++); // pushUp the nodes by the incremented total nodes
    }

    /**
     * searches the heap for the value at location key does not remove value.
     * 
     * @param key the target location
     * @return the value found at location key
     */
    @SuppressWarnings("unchecked")
    public T valueOf(int key) {
        keyExistsOrThrow(key); // check to see if key is in heap
        return (T) values[key]; // return a generic of the value found at index key
    }

    /**
     * removes both the key value pair
     * 
     * @param key the location of the target node
     * @return the removed value
     */
    @SuppressWarnings("unchecked")
    public T delete(int key) {
        keyExistsOrThrow(key); // checks if the key exists
        final int i = positionMap[key]; // creates a final int of the positionMap at key
        swap(i, --currentNumNodes); // swaps the node i to the end of the arrays
        pullDown(i); // pullDown i if needed
        pushUp(i); // pushUp i if needed
        T value = (T) values[key]; // initializes a generic set to the value remoed node
        values[key] = null; // sets the old value to null
        positionMap[key] = -1; // sets the old position to -1
        inverseMap[currentNumNodes] = -1; // sets the old inverse position to -1
        return value; // returns the old value
    }

    /**
     * updates the value of at location key with the passed in value
     * 
     * @param key   the target location
     * @param value the value to change to
     * @return the old value at location key
     */
    @SuppressWarnings("unchecked")
    public T update(int key, T value) {
        keyExistsAndValueNotNullOrThrow(key, value); // check if the key value pair already exist
        final int i = positionMap[key]; // create a final int of the key at positionMap at index key
        T oldValue = (T) values[key]; // create generic of the old value at value key
        values[key] = value; // value at key is replaced
        pullDown(i); // will push up i if needed
        pushUp(i); // will pull down i if neeed
        return oldValue; // the old value is returned
    }

    /**
     * decrease the value of T at location of key
     * 
     * @param key   the location of the target
     * @param value the value to be decreased
     */
    public void decrease(int key, T value) {
        keyExistsAndValueNotNullOrThrow(key, value); // checks if the key value pair exist
        if (less(value, values[key])) // if the passed in value is less than value at key
        {
            values[key] = value; // value at index key is set to passed in value
            pushUp(positionMap[key]); // the node in position map key is pushed up
        }
    }

    /**
     * increases the value of the object T at the location of key
     * 
     * @param key   the location of the target value
     * @param value the value to be increased
     */
    public void increase(int key, T value) {
        keyExistsAndValueNotNullOrThrow(key, value); // checks if the key value pair exist
        if (less(values[key], value)) // if the value at index key is less than value
        {
            values[key] = value; // value at index key is set to the passed in value
            pullDown(positionMap[key]); // pullDown the position of node at key
        }
    }

    // ---------------------------Helper
    // functions-----------------------------------

    /**
     * allows the rebalancing of the heap by swapping the node i with the min child
     * j.
     * 
     * @param i the node to be pulled down
     */
    private void pullDown(int i) {
        for (int j = minChild(i); j != -1;) // for all the min children of i
        {
            swap(i, j); // swap the i and j
            i = j; // i is set to j
            j = minChild(i); // j is now the min child of i
        }
    }

    /**
     * allows the rebalancing of the min heap by swapping the lesser of i and parent
     * node of i, then setting the value of i to the previously swapped value
     * 
     * @param i the node to be pushed up the heap
     */
    private void pushUp(int i) {
        while (less(i, parent[i])) // while i is less than parent at i
        {
            swap(i, parent[i]); // swap i and parent of i
            i = parent[i]; // i is set to parent of i
        }
    }

    /**
     * from the parent node at index i find the minimum child below it
     * 
     * @param i the parent node
     * @return the minimum child
     */
    private int minChild(int i) {
        int index = -1; // initialize index at -1
        int from = child[i]; // start from node at child of i
        int to = min(currentNumNodes, from + nodeDegree); // go until minimum of currentNumNodes, from + nodeDegree
        for (int j = from; j < to; j++) {
            if (less(j, i)) // if j is less than i
            {
                index = i = j; // set index and j to i
            }
        }
        return index; // return the index of the minium node
    }

    /**
     * swaps the elements in positions map at i with elements in position map at j.
     * Utilizes the inverse map to make the swap.
     * 
     * @param i index to be swapped
     * @param j index to be swapped
     */
    private void swap(int i, int j) {
        positionMap[inverseMap[j]] = i;
        positionMap[inverseMap[i]] = j;
        int tmp = inverseMap[i];
        inverseMap[i] = inverseMap[j];
        inverseMap[j] = tmp;
    }

    /**
     * Tests the key i and key j to see which is lesser in value. Uses Comparable
     * warper to allow for comparison of class objects
     * 
     * @param i key to be compared
     * @param j key to be compared
     * @return true if key i is less than j
     */
    @SuppressWarnings("unchecked")
    private boolean less(int i, int j) {
        return ((Comparable<? super T>) values[inverseMap[i]]).compareTo((T) values[inverseMap[j]]) < 0;
    }

    /**
     * Tests if the value of i and the value of j to which is less. Uses Comparable
     * warper to allow for comparison of class objects
     * 
     * @param obj1 value object to be compared
     * @param obj2 value object to be compared
     * @return true if obj1 is less than obj2
     */
    @SuppressWarnings("unchecked")
    private boolean less(Object obj1, Object obj2) {
        return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
    }

    // --------------------------------helper
    // methods--------------------------------

    /**
     * method used in other method bodies to reduce throws. Test if the heap is
     * empty
     */
    private void isNotEmptyOrThrow() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
    }

    /**
     * combines keyExistsOrThrow and valueNotNullOrThrow methods.
     * 
     * @param key   the key to be checked
     * @param value the value to be checked
     */
    private void keyExistsAndValueNotNullOrThrow(int key, Object value) {
        keyExistsOrThrow(key); // call to keyExistsOrThrow
        valueNotNullOrThrow(value); // cal to valueNotNullOrThrow
    }

    /**
     * checks if the key is within the heap throws a NoSuchElementException if key
     * is not in heap
     * 
     * @param key the key to be checked
     */
    private void keyExistsOrThrow(int key) {
        if (!contains(key)) // if key is not contained in heap
        {
            throw new NoSuchElementException("Index does not exist; received: " + key);
        }
    }

    /**
     * checks if the value is null. If the value equals null it will throw
     * IllegalArgumentException
     * 
     * @param value the value to be checked
     */
    private void valueNotNullOrThrow(Object value) {
        if (value == null)
            throw new IllegalArgumentException("value cannot be null");
    }

    /**
     * checks whether the key is within the bounds of the heap by comparing the key
     * with the maxElement value and 0
     * 
     * @param key the key to be checked
     */
    private void keyInBoundsOrThrow(int key) {
        if (key < 0 || key >= maxElements) // checks if key is in bounds
        {
            throw new IllegalArgumentException("Key index out of bounds; received: " + key);
        }
    }

    // ------------------------------------------------------------------------------

    /**
     * recursively checks if this heap is a min heap.
     */
    public boolean isMinHeap() {
        return isMinHeap(0); // call to isMinHeap method
    }

    /**
     * recursively checks if the heap is a min heap by passing in the int i. i is
     * used to as an index to child array to create a from variable. If i is 0, from
     * should be the root. the variable to is calculated by calling min on the
     * number of nodes and the degree of nodes + the degree of nodes. to is used to
     * create bounds for the for loop, from to to. If i at is ever less than j then
     * we do not have a min heap.
     * 
     * @param i the key to start from
     * @return true if from the i, heap is minimum else false
     */
    private boolean isMinHeap(int i) {
        int from = child[i];
        int to = min(currentNumNodes, from + nodeDegree);
        for (int j = from; j < to; j++) {
            if (!less(i, j)) {
                return false;
            }
            if (!isMinHeap(j)) {
                return false;
            }
        }
        return true;
    }
}