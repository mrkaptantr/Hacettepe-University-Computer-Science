import java.io.*;   
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFiles{

	public static void readLineByLine(String fileName, String[] arguments){  
		// This method reads files, line by line with Scanner object.
		File file = new File(fileName);
		String[] data = new String[1000]; 
		int index = 0; // Index means, line number of txt file.
		try{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) { 
				// Program continues until file ends.
				String line = scan.nextLine();
				data[index] = line;
				index++;}
			scan.close();
			methodFinder(fileName, data, index, arguments);
			// We need to regroup data but there are 2 type of data. Because of that, we need to find method to use.
			}
		catch (FileNotFoundException e) {
			System.err.println("An Error Occured While Reading File! Please Try Again!");
		}
	}

	private static void methodFinder (String fileName, String[] data, int index, String[] arguments){
		// With methodFinder method program continues correct method. There are 2 datas and we need different methods.		

		if (fileName.compareTo(arguments[1]) == 0){
			FileOperations.priceListOperations(data, index);
		}
		if (fileName.compareTo(arguments[0]) == 0){
			FileOperations.shoppingListOperations(data, index);
		}
	}
}