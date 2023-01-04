import java.util.EmptyStackException;
import java.util.Vector;

public class Stack<E> extends Vector<E>{
	
	/****************************************************************
	 *    Our stack class is extending Vector<E> class. Because     *
	 *     of that my stack class has AUTOMATİCALLY OVERRIDED       *
	 *  functions as stack.get(index) and stack.remove(index) like  *
	 *                      java.util.stack.                        *
	 ****************************************************************/

	 public Stack() {
	    	// Simple Constructor
	 }

	 public boolean isFull() {
	    // If stack has 100 elements, it means our stack is full!
	    if (elementCount == 100) {
	    	return true;
	    }
	    return false;
	 }
	    
	    public boolean empty() {
	        return isEmpty();
	    }
	  
	 public E push(E object) {
	    // If stack is not full, our object adds to stack.
		addElement(object);
		return object;
	 }
	       
	 @SuppressWarnings("unchecked")
	 public synchronized E peek() {
		// Returns last element of stack.
		try {
			return (E) elementData[elementCount - 1];
	    } catch (IndexOutOfBoundsException e) {
	        throw new EmptyStackException();
	    }
	 }

	 @SuppressWarnings("unchecked")
	 public synchronized E pop() {
		 // Classic pop function of stacks. 
		 if (elementCount == 0) {
			 throw new EmptyStackException();
	     }
	     final int index = --elementCount;
	     final E obj = (E) elementData[index];
	     elementData[index] = null;
	     modCount++;
	     return obj;
	   	 }
	}