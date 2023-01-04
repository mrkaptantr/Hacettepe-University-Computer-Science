import java.util.ArrayList;
import java.util.List;

public class Personel {
	
	public static void getSalary() {
		// Calls correct method for every one of personels.
		List<Integer> salaries = new ArrayList<>();
		for(int index=0; index<FileOperations.getNameAndSurname().size(); index++) {
			char firstLetter = FileOperations.getRegisterNumber().get(index).charAt(0);
			if (String.valueOf(firstLetter).compareTo("F") == 0) {
				salaries.add(Academician.getSalary(index, firstLetter));
			}	
			if (String.valueOf(firstLetter).compareTo("R") == 0) {
				salaries.add(Academician.getSalary(index, firstLetter));
			}	
			if (String.valueOf(firstLetter).compareTo("O") == 0) {
				salaries.add(Officer.getSalary(index));
			}	
			if (String.valueOf(firstLetter).compareTo("P") == 0) {
				salaries.add(Employee.getSalary(index, firstLetter));
			}	
			if (String.valueOf(firstLetter).compareTo("W") == 0) {
				salaries.add(Employee.getSalary(index, firstLetter));
			}	
			if (String.valueOf(firstLetter).compareTo("C") == 0) {
				salaries.add(Employee.getSalary(index, firstLetter));
			}	
			if (String.valueOf(firstLetter).compareTo("S") == 0) {
				salaries.add(Security.getSalary(index));
			}	
		}		
		prepareOutput(salaries);
	}
	
	public static void prepareOutput(List<Integer> salaries) {
		// This method prepares output for every one of personels.
		for(int i=0; i<FileOperations.getNameAndSurname().size(); i++) {
			List<String> lines = new ArrayList<>();
			lines.add("Name : " + FileOperations.getNameAndSurname().get(i).split(" ")[0] + "\n");
			lines.add("Surname : " + FileOperations.getNameAndSurname().get(i).split(" ")[1] + "\n");
			lines.add("Registiration Number : " + FileOperations.getRegisterNumber().get(i) + "\n");
			lines.add("Position : " + FileOperations.getPosition().get(i) + "\n");
			lines.add("Year Of Start : " + FileOperations.getYearOfStart().get(i) + "\n");
			lines.add("Total Salary : " + Integer.toString(salaries.get(i)) + ".00 TL" + "\n");
			FileOperations.writeOutput(FileOperations.getRegisterNumber().get(i), lines);
		}
	}	
}