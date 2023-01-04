import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileOperations {
	
	public static List<String> stackOut = new ArrayList<String>();
	public static List<String> queueOut = new ArrayList<String>();
	
	public static void readLineByLine(String fileName, String[] args){  
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
			methodFinder(data, fileName, args);
		}
		catch (FileNotFoundException e) {
			System.err.println("An Error Occured While Reading File! Please Try Again!");
		}
	}

	public static void methodFinder(List<String> data, String fileName, String[] args) {
		// Analyze stack and queue files.
		if(fileName.compareTo("stack.txt") == 0) {
			Stack<Integer> stack = StackOperations.getStack();
			String[] datas = data.get(0).split(" ");
			for(int i=0; i<datas.length; i++) {
				StackOperations.stackSize += 1;
				stack.push(Integer.parseInt(datas[i]));
			}
			StackOperations.setStack(stack);
			FileOperations.readLineByLine(args[0], args);
		}
		if(fileName.compareTo("queue.txt") == 0) {
			Queue<Integer> queue = QueueOperations.getQueue();
			String[] datass = data.get(0).split(" ");
			for(int i=0; i<datass.length; i++) {
				QueueOperations.queueSize += 1; 
				queue.enqueue(Integer.parseInt(datass[i]));
			}
			QueueOperations.setQueue(queue);
			FileOperations.readLineByLine("stack.txt", args);
		}
		if(fileName.compareTo(args[0]) == 0) {
			// Analyze and find correct methods for lines of command.txt.
			for (int i=0; i<data.size(); i++) {
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("S removeGreater") == 0) {
					StackOperations.removeGreater(Integer.parseInt(data.get(i).split(" ")[2]));
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("Q removeGreater") == 0) {
					QueueOperations.removeGreater(Integer.parseInt(data.get(i).split(" ")[2]));
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("S calculateDistance") == 0) {
					StackOperations.control = 0;
					StackOperations.distance = 0;
					StackOperations.calculateDistance(StackOperations.stackSize, 0);
					FileOperations.stackOut.add("After calculateDistance:");
			      		FileOperations.stackOut.add("Total distance=" + StackOperations.distance);
					StackOperations.distance = 0;
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("Q calculateDistance") == 0) {
					QueueOperations.q = 0;
					QueueOperations.control = 0;
					QueueOperations.stack = new Stack<Integer>();
					QueueOperations.distance = 0;
					QueueOperations.calculateDistance(QueueOperations.queueSize, 0);
					FileOperations.queueOut.add("After calculateDistance:");
			        FileOperations.queueOut.add("Total distance=" + QueueOperations.distance);
					QueueOperations.distance = 0;
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("S addOrRemove") == 0) {
					StackOperations.addOrRemove(Integer.parseInt(data.get(i).split(" ")[2]));
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("Q addOrRemove") == 0) {
					QueueOperations.addOrRemove(Integer.parseInt(data.get(i).split(" ")[2]));
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("S reverse") == 0) {
					StackOperations.reverse(Integer.parseInt(data.get(i).split(" ")[2]));
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("Q reverse") == 0) {
					QueueOperations.reverse(Integer.parseInt(data.get(i).split(" ")[2]));
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("S sortElements") == 0) {
					StackOperations.sortElements();
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("Q sortElements") == 0) {
					QueueOperations.sortElements();
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("S distinctElements") == 0) {
					StackOperations.distinctElements();
				}
				if ((data.get(i).split(" ")[0] + " " + data.get(i).split(" ")[1]).compareTo("Q distinctElements") == 0) {
					QueueOperations.distinctElements();
				}
			}
			writeOutputMode2("stack", stringBuilder1(StackOperations.getStack()));
			writeOutputMode2("queue", stringBuilder2(QueueOperations.getQueue()));
		}
		writeOutputMode1("stackOut", stackOut);
		writeOutputMode1("queueOut", queueOut);
	}
	
	public static String stringBuilder1(Stack stack) {
		// Convert stack to string
		String stackString = "";
		for(int i=0; i<StackOperations.stackSize; i++){
			stackString += (stack.get(i) + " ");
		}
		return stackString;
	}
	
	public static String stringBuilder2(Queue queue) {
		// Convert queue to string
		String queueString = "";
		for(Object element : queue) {
			queueString += (element + " ");
		}
		return queueString;
	}
	
	public static void writeOutputMode1(String fileName, List<String> lines){
		// Output writer-1
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
			for(int i=0; i<lines.size(); i++) {
				writer.write(lines.get(i) + "\n");
			}
		} catch (Exception e) { 
		}
	}
	
	public static void writeOutputMode2(String fileName, String lines){
		// Output writer-2
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt"), "utf-8"))) {
			writer.write(lines + "\n");
		} catch (Exception e) { 
		}
	}
}