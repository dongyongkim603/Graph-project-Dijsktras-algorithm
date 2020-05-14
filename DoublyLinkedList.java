/**
 * An implementation of the DoublyLinkedList data structure. The structure uses
 * generic type data to allow for more adaptable usage.
 * 
 * @author John
 * @param <E> a generic data type
 */
public class DoublyLinkedList<E> {

    // -------------------nested node class-----------------------------------------

    public static class Node<E> {
        // isntance variables
        public E element; // reference to the element stored at this node
        public Node<E> prev; // reference to the previous node in the list
        public Node<E> next; // reference to the next node in the list

        /**
         * The constructor for the DoublyLinkedList.
         * 
         * @param e the Object to set as the element
         * @param p the DoublyLinkedList that is previous in the list
         * @param n the DoublyLinkedList that is next in the list
         */
        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        // acess methods

        /**
         * @return the element in the node
         */
        public E getElement() {
            return element;
        }

        /**
         * @return a reference to the previous node
         */
        public Node<E> getPrev() {
            return prev;
        }

        /**
         * @return a reference to the next node
         */
        public Node<E> getNext() {
            return next;
        }

        /**
         * sets the next node reference of node, to new passed in node n
         * 
         * @param n the new node to replace next
         */
        public void setNext(Node<E> n) {
            next = n;
        }

        /**
         * sets the prev node reference of node, to new passed in node n
         * 
         * @param p
         */
        public void setPrev(Node<E> p) {
            prev = p;
        }
    }
    // --------------end of nested
    // class---------------------------------------------

    // instance variables
    private Node<E> head; // header sentinal
    private Node<E> tail; // trailer sentinal
    private int size = 0; // number of element

    /**
     * The default constructor
     */
    public DoublyLinkedList() {
        head = new Node<>(null, null, null); // create header
        tail = new Node<>(null, head, null); // trailer is preceded by header
        head.setNext(tail); // header is followed by trailer
    }

    /**
     * returns number of elements in list
     * 
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * tests whether list is empty
     * 
     * @return true if no nodes are in list
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * returns but does not remove the first element in the list
     * 
     * @return E of first Node
     */
    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.getNext().getElement(); // first element is beyond header
    }

    /**
     * returns but does not remove the last element of the list
     * 
     * @return E of last node
     */
    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.getPrev().getElement(); // last element is before trailer
    }

    /**
     * returns the a reference to the node head node or the node after header
     * sentinal node
     * 
     * @return the reference to head node
     */
    public Node getHead() {
        return head.getNext();
    }

    /**
     * sets the head node to the new passed in node
     * 
     * @param head the new head node
     */
    public void setHead(Node head) {
        this.head = head;
    }

    /**
     * returns but does not remove, a reference to the tail node or node before
     * trailer sentinal node
     * 
     * @return the tail node reference
     */
    public Node getTail() {
        return tail.getPrev();
    }

    /**
     * set the tail node to the new passed in tail node
     * 
     * @param tail the new tail node
     */
    public void setTail(Node tail) {
        this.tail = tail;
    }

    /**
     * @return the size of DLL
     */
    public int getSize() {
        return size;
    }

    /**
     * sets the size of the DLL
     * 
     * @param size
     */
    public void setSize(int size) {
        this.size = size;

    }

    // ---------------public update
    // methods------------------------------------------

    /**
     * Adds element e to the front of the list
     * 
     * @param e where e is real data
     */
    public void addFirst(E e) {
        addBetween(e, head, head.getNext());
    }

    /**
     * Adds element e to the end of the list
     * 
     * @param e where e is real data
     */
    public void addLast(E e) {
        addBetween(e, tail.getPrev(), tail);
    }

    /**
     * removes and returns last element in the list
     * 
     * @return e of last node
     */
    public E removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            return remove(tail.getPrev());
        }
    }

    /**
     * removes and returns first element in the list
     * 
     * @return e of last node
     */
    public E removeFirst() {
        // special case if list is empty
        if (isEmpty()) {
            return null;
        }
        // first element is beyond header
        return remove(head.getNext());
    }

    /**
     * returns but does not remove the element at node index n by walking through
     * from head node by the number n, to the target node
     * 
     * @param n the index of the target node
     * @return a generic of the value of node n
     */
    public E elementAtN(int n) {
        if (n > size - 1 && size > 0) // n is outside of list size
        {
            return null;
        } else if (n < 0) {
            return null; // n is a negitive or improssible request
        } else if (size == 0) // list is empty
        {
            return null;
        } else if (n == size - 1 && size > 0) // list is not empty request to remove last item
        {
            return tail.getPrev().getElement();
        } else if (n == 0 && size > 0) // list is not empty request to remove first item
        {
            return head.getNext().getElement();
        } else // all other requests for non-empty 2 < list with n != to first or last item
        {
            Node<E> current = head; // element to be removed
            for (int i = 0; i < n; i++) // for n steps walk
            {
                current = current.getNext(); // set current node to the next node of current
            }
            E answer = current.getElement(); // create generic E element anwser of element at current
            return answer; // return the answer
        }
    }

    /**
     * removes the node at n and returns the element
     * 
     * @param n the index of node to be removed
     * @return the element of removed node
     */
    public E removeAtN(int n) {
        if (n > size - 1 && size > 0) // n is outside of list size
        {
            return null;
        } else if (n < 0) {
            return null; // n is a negitive or improssible request
        } else if (size == 0) // list is empty
        {
            return null;
        } else if (n == size - 1 && size > 0) // list is not empty request to remove last item
        {
            return removeLast();
        } else if (n == 0 && size > 0) // list is not empty request to remove first item
        {
            return removeFirst();
        } else // all other requests for non-empty 2 < list with n != to first or last item
        {
            Node<E> current = head; // element to be removed
            Node<E> tailUpdate = head; // will be new tail node
            for (int i = 0; i < n; i++) // for n steps walk
            {
                current = current.next; // curent node is set to next of current node
            }
            for (int i = 0; i < size - 1; i++) // for 1 less than size walk tailUpdate to node
            {
                tailUpdate = tailUpdate.next; // tailUpdate is set to next of tailUpdate
            }
            Node<E> current1 = current.next; // create new current1 node equal to next of current node
            E answer = current.getElement(); // create generic E element set to the element of current node
            current.element = current1.element; // current node element is set to to current1 element
            current.next = current1.next; // current node next is set to current1 next
            tail.element = tailUpdate.element; // tail element is set to tailUpdate element
            tail.next = tailUpdate.next; // tail next is set to tailUpdate next
            size--; // decrement size
            return answer; // return the answer
        }
    }

    /**
     * concatList concatenates two doublyLinkedLists by creating a new node newest.
     * newest takes the E data from the last element of list L while newest next
     * refers to M head node. L tail node is removed so that newest prev may point
     * to correct element. Size of new list is size L + size M
     * 
     * @param L list for list M to be appended to
     * @param M list to be appended
     */
    public void concatLits(DoublyLinkedList L, DoublyLinkedList M) {
        // new middle node with data of last L node
        Node<E> newest = new Node<>(L.tail.getPrev().getElement(), L.tail.getPrev(), M.head.getNext());
        L.removeLast(); // removed last L node to prevent duplicate nodes
        L.tail.getPrev().setNext(newest); // set L's previous to trailer node's next to refer to newest
        L.tail = M.tail; // set new concatinated list L's tail node to M's tail node
        L.size = L.size + M.size; // add size of nodes together to get new node size
    }

    /**
     * This method returns the entire list in a String form
     * 
     * @return the String form of the list
     */
    @Override
    public String toString() {

        String out = "The DLList contains: \n";
        Node<E> ref = head;

        if (head == null) {
            return out + "0 nodes.";
        } else {
            out += "head -->\t";
        }

        while (ref != tail) {
            out += ref.element + "\t<-->\t";
            ref = ref.next;
        }

        out += ref.element + "\t<-- tail";

        return out;
    }

    // --------------------private update
    // methods------------------------------------

    /**
     * Adds element e to the linked list in between the given nodes
     * 
     * @param e           the real data to be added
     * @param predecessor the tail node reference
     * @param successor   the head node reference
     */
    private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
        Node<E> newest = new Node<>(e, predecessor, successor); // create and link new node
        predecessor.setNext(newest); // the predecessor node next is set to newest
        successor.setPrev(newest); // teh successor nodes prev is set to newest
        size++; // increment size of DLL
    }

    /**
     * Removes and returns the given element from the node in the list
     * 
     * @param node the node to be removed
     * @return the data E of the node to be removed
     */
    private E remove(Node<E> node) {
        Node<E> predecessor = node.getPrev(); // create a predecessor node set to node prev
        Node<E> successor = node.getNext(); // create successor node set to node next
        predecessor.setNext(successor); // set predecessor next to successor
        successor.setPrev(predecessor); // set successor node prev to predecessor
        size--; // decrement size
        return node.getElement(); // return the removed node element
    }
}
