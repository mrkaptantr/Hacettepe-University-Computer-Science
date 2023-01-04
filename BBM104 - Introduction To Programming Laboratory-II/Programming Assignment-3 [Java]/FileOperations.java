import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOperations{
	
	static List<String> output = new ArrayList<>();

	public static void readLineByLine(String fileName, String[] args){  
		// This method reads files, line by line with Scanner object.
		File file = new File(fileName);
		String[] data = new String[1500]; 
		int index = 0; // Index means, number of lines in txt file.
		
		try{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) { 
				// Program continues until file ends.
				String line = scan.nextLine();
				data[index] = line;
				index++;}
			scan.close();
			methodFinder(fileName, data, index, args);
			// We need to analyze and use our data for our goals.
		}
		catch (FileNotFoundException e) {
			System.err.println("An Error Occured While Reading File! Please Try Again!");
		}
	}
	
	// This method selects program will continue with which method.
	public static void methodFinder(String fileName, String[] data, int index, String[] args) {
		if (fileName.compareTo("patient.txt") == 0){
			Patient.analyzePatientFile(data, index);
		}
		if (fileName.compareTo("admission.txt") == 0){
			Admission.analyzeAdmissionFile(data, index);
		}
		if (fileName.compareTo(args[0]) == 0){
			analyzeInputFile(data, index);
		}
	}

	// Deciding what will program do next...
	public static void analyzeInputFile(String[] data, int index){	
		for(int i=0; i<index; i++) {
			if ("AddPatient".compareTo(data[i].split(" ")[0]) == 0){
				Patient.newPatient(data[i].substring(11));
			}
			if ("RemovePatient".compareTo(data[i].split(" ")[0]) == 0){
				Patient.removePatient(Integer.parseInt(data[i].split(" ")[1]));
			}
			if ("CreateAdmission".compareTo(data[i].split(" ")[0]) == 0){
				Admission.createAdmission(Integer.parseInt(data[i].split(" ")[1]),Integer.parseInt(data[i].split(" ")[2]));
			}
			if ("AddExamination".compareTo(data[i].split(" ")[0]) == 0){
				Admission.addExamination(data[i]);
			}
			if ("TotalCost".compareTo(data[i].split(" ")[0]) == 0){
				Admission.totalCost(Integer.parseInt(data[i].split(" ")[1]));
			}
			if ("ListPatients".compareTo(data[i].split(" ")[0]) == 0){
				Patient.listPatients();
			}
		}
		String[] AdmissionOut = Admission.getAdmissionFile();
		for(int i=0; i<1000; i++) {
			AdmissionOut[i] = AdmissionOut[i] + "\n";
		}
		List<String> AdmissionOutput = new ArrayList<>();
		for(String item: AdmissionOut) {
			AdmissionOutput.add(item);	
		}
		AdmissionOutput = AdmissionOutput.subList(0, Admission.Index);	
		writeOutput("output", output);
		writeOutput("patient", Patient.getPatientOutput());
		writeOutput("admission", AdmissionOutput);
	}
	
	// .txt File Writer Method
	public static void writeOutput(String fileName, List<String> lines){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
			for(int i=0; i<lines.size(); i++) {
			writer.write(lines.get(i));}
		} catch (Exception e) {
		} 
	}	
		
	public static String[] bubbleSort(int size, String[] data){
		for(int j=0; j<size; j++) {
			for (int i=j+1; i<size; i++) {	
				if(Integer.parseInt(data[i].split("\t")[0]) < Integer.parseInt((data[j].split("\t")[0]))) {
					String temp = data[j];
					data[j] = data[i];
					data[i] = temp;
				}
			}
		}
		return data;	
	}
}