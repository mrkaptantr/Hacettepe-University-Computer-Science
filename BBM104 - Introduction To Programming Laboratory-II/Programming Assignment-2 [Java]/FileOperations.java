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
	
	public static void readLineByLine(String fileName, String[] arguments){  
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
			methodFinder(fileName, data, index, arguments);
			// We need to analyze and use our data for our goals.
			}
		catch (FileNotFoundException e) {
			System.err.println("An Error Occured While Reading File! Please Try Again!");
		}
	}
	
	// This method selects program will continue with which method.
	public static void methodFinder(String fileName, String[] data, int index, String[] arguments) {
		if (fileName.compareTo(arguments[1]) == 0){
			analyzeMonitoringFile(data, index);
		}
		if (fileName.compareTo(arguments[0]) == 0){
			analyzePersonelFile(data, index);
		}
	}
	
	// Analyzing and regroupping personel file 
	private static List<String> nameAndSurname = new ArrayList<>();
	private static List<String> registerNumber = new ArrayList<>();
	private static List<String> position = new ArrayList<>();
	private static List<String> yearOfStart = new ArrayList<>();
	
	public static void analyzePersonelFile(String[] data, int index){	
		for(int i=0; i<index; i++) {
			nameAndSurname.add(data[i].split("\t")[0]);
			registerNumber.add(data[i].split("\t")[1]);
			position.add(data[i].split("\t")[2]);
			yearOfStart.add(data[i].split("\t")[3]);
			}
		}
	
	// Analyzing and regroupping monitoring file
	private static List<String> registerNumber2 = new ArrayList<>();
	private static List<String> week1 = new ArrayList<>();
	private static List<String> week2 = new ArrayList<>();
	private static List<String> week3 = new ArrayList<>();
	private static List<String> week4 = new ArrayList<>();
	
	public static void analyzeMonitoringFile(String[] data, int index){
		for(int i=0; i<index; i++) {
			registerNumber2.add(data[i].split("\t")[0]);
			week1.add(data[i].split("\t")[1]);
			week2.add(data[i].split("\t")[2]);
			week3.add(data[i].split("\t")[3]);
			week4.add(data[i].split("\t")[4]);
			}
		Personel.getSalary();
	}
	
	// Getter methods.
	public static List<String> getNameAndSurname(){
		return nameAndSurname;
	}
	
	public static List<String> getRegisterNumber(){
		return registerNumber;
	}	
	
	public static List<String> getPosition(){
		return position;
	}	
	
	public static List<String> getYearOfStart(){
		return yearOfStart;
	}	
	
	public static List<String> getWeek1(){
		return week1;
	}
	
	public static List<String> getWeek2(){
		return week2;
	}	
	
	public static List<String> getWeek3(){
		return week3;
	}	
	
	public static List<String> getWeek4(){
		return week4;
	}	
	
	// This method creates and writes output files.
		public static void writeOutput(String fileName, List<String> lines){
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
				for(int i=0; i<lines.size(); i++) {
				writer.write(lines.get(i));}
			} catch (Exception e) {
				System.out.println("An Error Occured While Writing Output File Called: " + fileName + ".txt");
			} 
		}
	}