import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class FileOperations {
	
	public static Stack<Integer> stack = new Stack<Integer>();
	
	public static int index = 0;
	public static void readLineByLine(String fileName){  
		File file = new File(fileName);
		try{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) { 
				String line = scan.nextLine();
				stack.push(Integer.parseInt(line));
				if (index == 10) {
				} else {
				index++;}}
			scan.close();
			Calculation.CalculateOctal(stack, index);
			}
		catch (FileNotFoundException e) {
			System.err.println("An Error Occured While Reading File! Please Try Again!");
		}
	}
	
	public static void writeOutputWithStack(String fileName, Stack<Integer> lines){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
			for(int i=0; i<index; i++) {
				writer.write(lines.Pop() +  "\n");}
		} catch (Exception e) { 
		}
		System.out.println("Output created successfully!");
	}
}
