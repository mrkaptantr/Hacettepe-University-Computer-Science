import java.util.Random;
import java.util.HashSet;

public class QueueOperations {
	protected static int queueSize;
	static Queue<Integer> queue = new Queue<Integer>();
	public static Queue<Integer> getQueue() {return queue;}
	public static void setQueue(Queue<Integer> updatedQueue) {queue = updatedQueue;}
	
	public static void removeGreater(int i){
		// Remove values, which are greater than i.

		Stack<Integer> stack = new Stack<Integer>();
		Queue<Integer> copy1 = new Queue<Integer>();
		Queue<Integer> copy2 = new Queue<Integer>();		

		for (int k=0; k<queueSize; k++) {
			Integer item = queue.peek();
			stack.push(item);
			queue.dequeue();
		}	

		for (int j=queueSize-1; -1<j; j--) {
			if(stack.get(j) > i) {
				stack.remove(j);
				queueSize -= 1;
			}
		}
		
		for (int a=0; a<queueSize; a++){
			copy1.enqueue(stack.get(a));	
			copy2.enqueue(stack.get(a));
		}
			
		setQueue(copy1);		
	
		String info = "";
		for(int m=0; m<queueSize; m++) {
			Integer item = copy2.peek();
			copy2.dequeue();
			info += (item + " ");
		}		
		FileOperations.queueOut.add("After removeGreater " + i);
        FileOperations.queueOut.add(info);  
        
	}
	
	static int control = 0;
	static int distance = 0;
	static int q = 0;
	static Stack<Integer> stack = new Stack<Integer>();
	public static void calculateDistance(int Size, int Index) {	
		if (q == 0){
			for(int a=0; a<queueSize; a++){
				stack.push(queue.peek());
				queue.dequeue();
			}
		}
		q += 1;
		control += 1;
		for (int i=0; i<Size; i++) {
			if (Size != control) {
				for (int j = Index+1; j<Size; j++) {
					distance += Math.abs(stack.get(i) - stack.get(j));
					i++;
				}
				Index += 1;
				calculateDistance(queueSize, Index);
			} else {
				break;
			}
		}

		Queue<Integer> queue2 = new Queue<Integer>();
		for (int a=0; a<queueSize; a++){
			queue2.enqueue(stack.get(a));
		}
		setQueue(queue2);
	}
	
	public static void addOrRemove(int i){
		/* In this operation, a negative or positive value is read from the input file. If the number is
		negative, remove elements as the number of times. If the number is positive, add new random
		elements as the number of times (between 0-50).*/
		if(i > 0) {
			Stack<Integer> stack = new Stack<Integer>();
			for (int m=0; m<queueSize; m++){
				stack.push(queue.peek());
				queue.dequeue();
			}

			int randomInteger = 0;
			Random random = new Random();
			for(int q=0; q<i; q++) {
				randomInteger = random.nextInt(50);
				stack.add(0, randomInteger);
				queueSize += 1;
			}

			for (int z=0; z<queueSize; z++){
				queue.enqueue(stack.get(z));
			}
		}
		if(i < 0) {
			for(int q=0; q<(-i); q++) {
				queue.dequeue();
				queueSize -= 1;
			}
		}	
		
		String info = "";
		Queue<Integer> copy1 = new Queue<Integer>();
		for(int m=0; m<queueSize; m++) {
			Integer item = queue.peek();
			copy1.enqueue(item);
			queue.dequeue();
			info += (item + " ");
		}		
		setQueue(copy1);		
		FileOperations.queueOut.add("After addOrRemove " + i);
        FileOperations.queueOut.add(info);
	}
	
	public static void reverse(int i){
		// This method reverses given integer queue.
		Queue<Integer> reversedQueue = new Queue<Integer>();
		Queue<Integer> copy1 = new Queue<Integer>();
		Queue<Integer> copy2 = new Queue<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		Stack<Integer> reversedStack = new Stack<Integer>();
		
		for(int m=0; m<queueSize; m++) {
			Integer item = queue.peek();
			copy1.enqueue(item);
			copy2.enqueue(item);
			queue.dequeue();
		}
		
		int reversedSize = 0;
		for(int m=0; m<i; m++) {
			Integer item = copy1.peek();
			stack.add(item);
			reversedSize += 1;
			copy1.dequeue();
		}	
		
				
		for(int j=reversedSize-1; 0<=j; j--) {
			reversedStack.add(stack.get(j));
		}	
				
		for(int m=0; m<reversedSize; m++) {
			reversedQueue.enqueue(reversedStack.get(m));
			copy2.dequeue();
		}
	
		for(int j=reversedSize; j<queueSize; j++) {
			Integer item = copy2.peek();
			reversedQueue.enqueue(item);
			copy2.dequeue();
		}	
		setQueue(reversedQueue);
		
		String info = "";
		Queue<Integer> copy3 = new Queue<Integer>();
		for(int m=0; m<queueSize; m++) {
			Integer item = queue.peek();
			copy3.enqueue(item);
			queue.dequeue();
			info += (item + " ");
		}		
		setQueue(copy3);
		FileOperations.queueOut.add("After reverse " + i);
        FileOperations.queueOut.add(info);
	}
	
	public static void sortElements(){
		// Integer queue sorter method
		Queue<Integer> q1 = queue;
		Queue<Integer> q2 = new Queue<Integer>();
	    int min=Integer.MAX_VALUE;
	    while (!q1.isEmpty())
	    {
	        q1.enqueue(-1);
	        while(q1.peek() != -1)
	        {
	            if (q1.peek() < min)
	            {
	                min=q1.peek();
	            }
	            q1.enqueue(q1.dequeue());

	        }
	        q1.dequeue(); //removing the -1
	        while (q1.peek() != min)
	        {
	            q1.enqueue(q1.dequeue());
	        }
	        min = Integer.MAX_VALUE;
	        q2.enqueue(q1.dequeue()); //inserting the minimum to the second queue.
	    }
	    setQueue(q2);
	    String info = "";
	    Queue<Integer> copy3 = new Queue<Integer>();
	    for(int m=0; m<queueSize; m++) {
		Integer item = queue.peek();
		copy3.enqueue(item);
		queue.dequeue();
		info += (item + " ");
	}
	setQueue(copy3);
	FileOperations.queueOut.add("After sortElements:");
        FileOperations.queueOut.add(info);
	}
	
	public static void distinctElements(){
		// This method finds unique elements in queue.
	    HashSet<Integer> set = new HashSet<Integer>();  
		Queue<Integer> copy1 = new Queue<Integer>();
	    
	    for(int i=0; i<queueSize; i++) {
	    	Integer item = queue.peek();
			copy1.enqueue(item);
			set.add(item);
			queue.dequeue();
	    }
	    setQueue(copy1);
		FileOperations.queueOut.add("After distinctElements:");
	    FileOperations.queueOut.add("Total distinct element=" + set.size());
	}	
}