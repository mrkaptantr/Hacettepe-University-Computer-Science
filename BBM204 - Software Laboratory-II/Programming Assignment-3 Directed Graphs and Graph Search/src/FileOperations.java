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
	public static void readLineByLine(String fileName, String[] args){
		// This method reads files, line by line with Scanner object.
		File file = new File(fileName);
		List<String> data = new ArrayList<String>();
		try{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) { 
				// Program continues until file ends.
				String line = scan.nextLine();
				data.add(line);
				}
			scan.close();
			methodFinder(data, fileName, args);
		}
		catch (FileNotFoundException e) {
			System.err.println("An Error Occured While Reading File! Please Try Again!");
		}
	}
	
	public static List<Airport> airports = new ArrayList<Airport>();
	public static List<Flight> flights = new ArrayList<Flight>();
	public static void methodFinder(List<String> data, String fileName, String[] args) {
		// Analyze stack and queue files.
		if(fileName.compareTo(args[0]) == 0) {
			for (int i=0; i<data.size(); i++) {
				String cityName = data.get(i).split("\t")[0];
				for (int j=1; j<data.get(i).split("\t").length; j++) {
					String alias = data.get(i).split("\t")[j];
					Airport airport = new Airport(cityName, alias);
					airports.add(airport);
				}
			}
		}
		else if(fileName.compareTo(args[1]) == 0) {
			for (int i=0; i<data.size(); i++) {
				String flightID = data.get(i).split("\t")[0];
				String dept = data.get(i).split("\t")[1].split("->")[0];
				String arr = data.get(i).split("\t")[1].split("->")[1];
				String deptDate = data.get(i).split("\t")[2];
				String duration = data.get(i).split("\t")[3];
				String price = data.get(i).split("\t")[4];
				Flight flight = new Flight(flightID, dept, arr, deptDate, duration, price);
				flights.add(flight);
			}
		}
		else if(fileName.compareTo(args[2]) == 0) {
			AirportSystem as = new AirportSystem(airports, flights, data);
			as.generateGraph();
			as.executeCommands();
		}
	}
	
	// Simple output writer function
	public static void writeOutputFile(String fileName, List<String> lines){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
			for(int i=0; i<lines.size(); i++) {
				writer.write(lines.get(i) + "\n");
			}
		} catch (Exception e) {
			System.out.println("An Error Occured While Writing Output File Called: " + fileName + ".txt");
		}
	}
}
