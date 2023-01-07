package FileCipherv2;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileCipher {
	
	public static void main(String[] args) {
				
		// Retrieving Command Line Arguments
		String commandLineArguments = "FileCipher" +" "+args[0] + " " +args[1]+" " +args[2]+" "+ args[3]+" "+args[4]+" "+args[5]+" "+args[6]+" "+args[7];
		//String commandLineArguments = "FileCipher −d −i encrypted.txt −o decrypted.txt DES CBC key_example1.txt";
		// Storing Command Line Arguments
		String[] arguments = new String[commandLineArguments.split(" ").length];
		for(int i=0; i<commandLineArguments.split(" ").length; i++) {
			arguments[i] = commandLineArguments.split(" ")[i];
		}
		
		// Reading Key File Content
		FileOperations.readLineByLine(commandLineArguments.split(" ")[8], arguments);
		
		// Reading Input File Content
		FileOperations.readLineByLine(commandLineArguments.split(" ")[3], arguments);
		
		// After Reading Input File Content, Program Will Continue Execution Properly
	}
}
