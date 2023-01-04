import java.util.EmptyStackException;
import java.util.Vector;

public class Stack<E> extends Vector<E> {
	
	    public Stack() {
	    	// Simple Constructor
	    }

	    public boolean isFull() {
	    	// If stack has 10 elements, it means our stack is full!
	    	if (elementCount == 10) {
	    		return true;
	    	}
	    	return false;
	    }
	    
	    public boolean isEmpty() {
	    	// If stack has less than 10 elements, that means we can add new elements!
	    	if (elementCount < 10) {
	    		return true;
	    	}
	    	return false;
	    }
	    
	    public E push(E object) {
	    	// If stack is not full, our object adds to stack.
	        if (isEmpty()) {
		    	addElement(object);
		        return object;
	        }
	        // 
	        else if (isFull()) {
	        	// If stack is full, we don't add that value and print a info message.
	        	System.out.println("Stack can't store more than 10 variables! So we're passing that value!");
	        }
	        return null;
	    }
	    
	    @SuppressWarnings("unchecked")
	    public synchronized E Pop() {
	    	// Classic copy and remove last element method.
	        if (elementCount == 0) {
	            throw new EmptyStackException();
	        }
	        final int index = --elementCount;
	        final E obj = (E) elementData[index];
	        elementData[index] = null;
	        modCount++;
	        return obj;
	    }
	    
	    @SuppressWarnings("unchecked")
	    public synchronized E Top() {
	    	// Classic copy first element method.
	        try {
	            return (E) elementData[elementCount - 1];
	        } catch (IndexOutOfBoundsException e) {
	            throw new EmptyStackException();
	        }
	    }
	}