package FileCipherv2;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;



public class FileOperations {
	static List<String> output = new ArrayList<>();
	static List<String> input_data = new ArrayList<>();

	static List<String> input_data_helper = new ArrayList<>();
	static List<String> key_data = new ArrayList<>();
	static List<byte[]> output_data = new ArrayList<>();
	static String currPath = "";

	public static void readLineByLine(String fileName, String[] args){
		
		// This method reads files, line by line with Scanner object.
		File file = new File(currPath + fileName);
		try {
			Scanner scan;
			if (fileName.compareTo(args[8]) == 0) {
				scan = new Scanner(file, "UTF-8");
			}
			else if (args[1].compareTo("-e") == 0){
				scan = new Scanner(file, "UTF-8");
			}
			else {
				scan = new Scanner(file, "ISO_8859_1");
			}

			while(scan.hasNextLine()) {
				// Program continues until file ends.
				String line = scan.nextLine();
				if(fileName.compareTo(args[3]) == 0) {
					input_data.add(line);
				} else {
					key_data.add(line);
				}
			}
			if(fileName.compareTo(args[3]) == 0) { methodFinder(fileName, args); }
			scan.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// This method selects program will continue with which method.
	public static void methodFinder(String fileName, String[] args) {		
		// Splitting input file and generating keys
		String IV = key_data.get(0).split("-")[0].strip();
		String Key = key_data.get(0).split("-")[1].strip();
		String Nonce = key_data.get(0).split("-")[2].strip();

		// Checking whether 'run.log' file exits
		// If file already exists, it will do nothing 
		File logFile = new File("run.log");
		try {
			logFile.createNewFile();
			FileOutputStream oFile = new FileOutputStream(logFile, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Starting timer
		long start = System.currentTimeMillis();
						
		// Calling encoder and decoder operations
		if (args[1].compareTo("-e") == 0){
			input_data_helper.add("");
			for(int i = 0 ; i < input_data.size() ; i++){
				String setHelper= "";
				if(i == 0){
					setHelper = input_data_helper.get(0) + input_data.get(i);
				}
				else{
					setHelper =  input_data_helper.get(0) + "\n" + input_data.get(i);
				}
				input_data_helper.set(0, setHelper);
			}

			int sizeHelper = input_data_helper.get(0).length()%8;
			if(sizeHelper != 0) {
				while (sizeHelper != 8) {

					input_data_helper.set(0, input_data_helper.get(0) + " ");
					sizeHelper++;
				}
			}
			output_data = ECBModes.encrypt(input_data_helper, args[6], args[7], IV, Key, Nonce);
		}
		else if (args[1].compareTo("-d") == 0){
			input_data_helper.add("");
			for(int i = 0 ; i < input_data.size() ; i++){
				String setHelper= "";
				if(i == 0){
					setHelper = input_data_helper.get(0) + input_data.get(i);
				}
				else{
					setHelper =  input_data_helper.get(0) + "\n" + input_data.get(i);
				}
				input_data_helper.set(0, setHelper);
			}
			output_data = ECBModes.decrypt(input_data_helper, args[6], args[7], IV, Key, Nonce);
		}
		
		// Calculating elapsed time
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;

		// Updating log file
		String encordec = "enc";
		if (args[1].compareTo("-d") == 0){ encordec = "dec"; }
		String log = args[3] + " " + args[5] + " " + encordec + " " + args[6] + " " + args[7] + " " + elapsedTime + "\n";
		Logger logger = Logger.getLogger("MyLog");
		logger.setUseParentHandlers(false);
		FileHandler fh;

		try {
			fh = new FileHandler(currPath + "run.log", true);
			logger.addHandler(fh);
			fh.setFormatter(new MyCustomFormatter());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info(log);
				
		// Writing output
		if (args[1].compareTo("-e") == 0){
			writeOutput(args[5], output_data);
		}
		else if (args[1].compareTo("-d") == 0){
			writeOutputUTF(args[5], output_data);
		}
	}

	// Overriding formatter in order to prevent logging unnecessary stuff
	// (In PDF, required log format doesn't include java file names and dates
	private static class MyCustomFormatter extends Formatter {
		@Override
		public String format(LogRecord record) {
			StringBuffer sb = new StringBuffer();
			sb.append(record.getMessage());
			return sb.toString();
		}
	}
	
	// .txt File Writer Method
	public static void writeOutput(String fileName, List<byte[]> lines){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "ISO_8859_1"))) {
			for(int i=0; i<lines.size(); i++) {
				
				System.out.println("Encrypted Bytes: " + Arrays.toString(lines.get(i)));

				String s = new String (lines.get(i), StandardCharsets.ISO_8859_1);
				writer.write(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void writeOutputUTF(String fileName, List<byte[]> lines){

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
			for(int i=0; i<lines.size(); i++) {
				
				System.out.println("Decrypted Bytes: " + Arrays.toString(lines.get(i)));
				
				String s = new String (lines.get(i), StandardCharsets.UTF_8);
				
				writer.write(s);
					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
