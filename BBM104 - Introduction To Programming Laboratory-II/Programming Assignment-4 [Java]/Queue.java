import java.util.Iterator;
import java.util.NoSuchElementException;
	
public class Queue<Item> implements Iterable<Item>{
	
    // Variables and types
    private int n;        
    private Node first;    
    private Node last;     
    public int size() {return n;}
    public int length() {return n;}

    // A simple Node.
    private class Node {
        private Item item;   
        private Node next; 
    }

    // Simple queue constructor.
    public Queue() {
        first = null;
        last = null;
        n = 0;
    }

    // If queue is empty, it returns true.
    public boolean isEmpty() {
        return first == null;
    }
    
    // If queue is full, it returns true.
    public boolean isFull() {
    	if (size() == 100){
    		return true;
    	}
    	// Else return to false.
    	return false;
    }

    // Returns first item of queue.
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue Is Underflow!");
        return first.item;
    }

    // Adding an element to queue function.
    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        n++;
    }

    // Removing the last item of queue function.
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
}