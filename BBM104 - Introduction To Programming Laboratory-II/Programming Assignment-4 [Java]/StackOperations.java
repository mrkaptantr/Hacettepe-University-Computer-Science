import java.util.Random;
import java.util.HashSet;

public class StackOperations {
	protected static int stackSize;
	private static Stack<Integer> stack = new Stack<Integer>();
	public static Stack<Integer> getStack() {return stack;}
	public static void setStack(Stack<Integer> updatedStack) {stack = updatedStack;}
	
	public static void removeGreater(int i) {
		// Remove values, which are greater than i.
		for (int j=stackSize-1; -1<j; j--) {
			if(stack.get(j) > i) {
				stack.remove(j);
				stackSize -= 1;
			}
		}
		Object[] vals = stack.toArray();
		String info = "";
        for (Object obj : vals) {
        	info += (obj + " ");
        }
		FileOperations.stackOut.add("After removeGreater " + i);
        FileOperations.stackOut.add(info);
	}
	
	static int control = 0;
	static int distance = 0;
	public static void calculateDistance(int Size, int Index){	
		control += 1;
		for (int i=0; i<Size; i++) {
			if (Size != control) {
				for (int j = Index+1; j<Size; j++) {
					distance += Math.abs(stack.get(i) - stack.get(j));
					i++;
				}
				Index += 1;
				calculateDistance(stackSize, Index);
			} else {
				break;
			}
		}
	}
	
	public static void addOrRemove(int i) {	
		/* In this operation, a negative or positive value is read from the input file. If the number is
		negative, remove elements as the number of times. If the number is positive, add new random
		elements as the number of times (between 0-50).*/
		if(i > 0) {
			int randomInteger = 0;
			Random random = new Random();
			for(int q=0; q<i; q++) {
				randomInteger = random.nextInt(50);
				stack.add(0, randomInteger);
				stackSize += 1;
			}
		}
		if(i < 0) {
			for(int q=0; q<(-i); q++) {
				stack.remove(stackSize);
				stackSize -= 1;
			}
		}	
		Object[] vals = stack.toArray();
		String info = "";
        for (Object obj : vals) {
        	info += (obj + " ");
        }
		FileOperations.stackOut.add("After addOrRemove " + i);
        FileOperations.stackOut.add(info);
	}
	
	public static void reverse(int i){		
		// This method reverses given integer stack.
		Stack<Integer> reversedStack = new Stack<Integer>();	
		for(int m=i-1; -1<m; m--) {
			reversedStack.push(stack.get(m));
		}	
		for(int j=i; j<stackSize; j++) {
			reversedStack.push(stack.get(j));
		}	
		setStack(reversedStack);
		Object[] vals = stack.toArray();
		String info = "";
        for (Object obj : vals) {
        	info += (obj + " ");
        }
		FileOperations.stackOut.add("After reverse " + i);
        FileOperations.stackOut.add(info);
	}
	
	public static void sortElements(){
		// Stack sorter method.
		Stack<Integer> newStack = new Stack<Integer>();
		for (int i=0; i<stackSize; i++){
			newStack.push(stack.get(i));
		}
		
		Stack<Integer> newsStack = sortStack(newStack);
		Stack<Integer> reversedStack = new Stack<Integer>();
		
		for(int m=stackSize-1; 0<=m; m--) {
			reversedStack.push(newsStack.get(m));
		}		
		setStack(reversedStack); 
		
		Object[] vals = stack.toArray();
		String info = "";
        for (Object obj : vals) {
        	info += (obj + " ");
        }
		FileOperations.stackOut.add("After sortElements:");
        FileOperations.stackOut.add(info);
	}
	
	public static Stack<Integer> sortStack(Stack<Integer> stack){	
		// A simple integer stack sorter method.
		if (stack == null || stack.isEmpty()) return stack;
		Stack<Integer> newStack = new Stack<Integer>();
		newStack.push(stack.pop());
		
		while(!stack.isEmpty()) {
			int temp = stack.pop();
			while (!newStack.isEmpty() && temp > newStack.peek()) {
				stack.push(newStack.pop());
			}
			newStack.push(temp);
		}
		return newStack;
	}
	
	public static void distinctElements(){
	    // This method finds number of unique elements in stack.

	    HashSet<Integer> set = new HashSet<Integer>();  
	    	    
	    for(int i=0; i<stackSize; i++) {
			set.add(stack.get(i));
	    }

	    /*int res = 1; 
	    for (int i = 0; i < stackSize; i++){ 
	        int j = 0; 
	        for (j = 0; j < i; j++) 
	            if (stack.get(i) == stack.get(j)) 
	                break; 
	        if (i == j) 
	            res++; 
	    } */
	    FileOperations.stackOut.add("After distinctElements:");
	    FileOperations.stackOut.add("Total distinct element=" + set.size());
	} 
}
