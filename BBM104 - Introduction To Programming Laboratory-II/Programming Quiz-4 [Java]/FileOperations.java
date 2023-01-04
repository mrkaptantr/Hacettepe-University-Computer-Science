import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class FileOperations {
	public static void readLineByLine(String fileName){  
		// This method reads files, line by line with Scanner object.
		File file = new File(fileName);
		List<String> data = new ArrayList();
		int index = 0; // Index means, number of lines in txt file.
		try{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) { 
				// Program continues until file ends.
				String line = scan.nextLine();
				data.add(line);

				index++;}
			scan.close();
			Contact.StoreData(data, index);
			// We need to analyze and use our data for our goals.
			}
		catch (FileNotFoundException e) {
			System.err.println("An Error Occured While Reading File! Please Try Again!");
		}
	}
	
	public static void writeOutputWithList(String fileName, List<Contact> lines){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
			for(int i=0; i<lines.size(); i++) {
				writer.write((String)Contact.getPhoneNumber(lines.get(i)) + " " + (String)Contact.getFirstName(lines.get(i)) + " " + (String)Contact.getLastName(lines.get(i)) + " " + (String)Contact.getSocialSecurityNumber(lines.get(i)) + "\n");}
		} catch (Exception e) { 
		}
	}
	
	public static void writeOutputWithMap(String fileName, HashMap<String, Contact> dataHashMap, List<String> keys) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
			for(int i=0; i<keys.size(); i++) {
				writer.write((String)Contact.getPhoneNumber(dataHashMap.get(keys.get(i))) + " " + (String)Contact.getFirstName(dataHashMap.get(keys.get(i))) + " " + (String)Contact.getLastName(dataHashMap.get(keys.get(i))) + " " + (String)Contact.getSocialSecurityNumber(dataHashMap.get(keys.get(i))) + "\n");}
		} catch (Exception e) { 
		}
	}
	
	public static void writeOutputWithHash(String fileName, HashSet<Contact> lines){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
			for (Contact contact : lines) {
				writer.write((String)Contact.getPhoneNumber(contact) + " " + (String)Contact.getFirstName(contact) + " " + (String)Contact.getLastName(contact) + " " + (String)Contact.getSocialSecurityNumber(contact) + "\n");
			}
			} catch (Exception e) { 
		}
	}
	
	public static void writeOutputWithTree(String fileName, Set<Contact> lines){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))){
			for (Object object : lines) {
				writer.write((String)Contact.getPhoneNumber((Contact) object) + " " + (String)Contact.getFirstName((Contact) object) + " " + (String)Contact.getLastName((Contact) object) + " " + (String)Contact.getSocialSecurityNumber((Contact) object)  + "\n");} 
		} catch (Exception e) { 
		}
	}
}
