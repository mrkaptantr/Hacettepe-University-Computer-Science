public class Main {
	
	public static void main(String[] args) {
	FileOperations.readLineByLine("patient.txt", args);
	FileOperations.readLineByLine("admission.txt", args);
	FileOperations.readLineByLine(args[0], args);
	}
}
