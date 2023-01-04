import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOperations {
	
	// Lists of results of analyzing files.
	static List<String> typeOfSport = new ArrayList<>();
	static List<String> firstClub = new ArrayList<>();
	static List<String> secondClub = new ArrayList<>();
	static List<String> resultOfMatch = new ArrayList<>();
	
	public static void readLineByLine(String fileName){  
		// This method reads files, line by line with Scanner object.
		File file = new File(fileName);
		String[] data = new String[1000]; 
		int index = 0; // Index means, number of lines in txt file.
		
		try{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) { 
				// Program continues until file ends.
				String line = scan.nextLine();
				data[index] = line;
				index++;}
			scan.close();
			analyzeFile(data, index);
			// We need to analyze and use our data for our goals.
			}
		catch (FileNotFoundException e) {
			System.err.println("An Error Occured While Reading File! Please Try Again!");
		}
	}
	
	public static void analyzeFile(String[] data, int index){
		
		for(int i=0; i<index; i++) {
			typeOfSport.add(data[i].split("\t")[0]);
			firstClub.add(data[i].split("\t")[1]);
			secondClub.add(data[i].split("\t")[2]);
			resultOfMatch.add(data[i].split("\t")[3]);
		}
		// Program continues with Sports class.
		Sports.startLeague();
	}
	
	// Get methods of analyzed files
	public static List<String> getType(){
		return typeOfSport;
	}

	public static List<String> getFirstClub(){
		return firstClub;
	}

	public static List<String> getSecondClub(){
		return secondClub;
	}

	public static List<String> getResult(){
		return resultOfMatch;
	}
	
	// This method creates and writes output files.
	public static void writeOutput(String fileName, List<String> lines){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
			for(int i=lines.size()-1; i>-1; i--) {
			writer.write(lines.get(i));}
		} catch (Exception e) {
			System.out.println("An Error Occured While Writing Output File Called: " + fileName + ".txt");
		} 
	}
}