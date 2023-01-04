import java.util.ArrayList;
import java.util.List;

public class Sports {
		
	public static void startLeague() {
		// We call special functions for every sport. 
		Basketball.calculateResults(FileOperations.getType());
		Handball.calculateResults(FileOperations.getType());
		IceHockey.calculateResults(FileOperations.getType());
		Volleyball.calculateResults(FileOperations.getType());
	}
	
	public static <T> ArrayList<T> removeDuplicates(List<String> list) { 
		// This method removes duplicated elements in a array list.
        ArrayList<T> newList = new ArrayList<T>(); 
        for (String element : list) { 
            if (!newList.contains(element)) { 
                newList.add((T) element); 
            } 
        } 
        return (ArrayList<T>) newList; 
    } 
}
