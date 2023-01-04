public class Academician extends Personel{

	private static int baseSalary = 2600;
	private static int ssBenefits;
	private static int W1, W2, W3, W4;
	
	static int getSalary(int index, char firstLetter) {
		// Calculating salary of faculty members.
		if (String.valueOf(firstLetter).compareTo("F") == 0) {
			ssBenefits = (int)(1.35 * baseSalary);
			W1 = FacultyMembers.getAddCourseFee(Integer.parseInt(FileOperations.getWeek1().get(index)));
			W2 = FacultyMembers.getAddCourseFee(Integer.parseInt(FileOperations.getWeek2().get(index)));
			W3 = FacultyMembers.getAddCourseFee(Integer.parseInt(FileOperations.getWeek3().get(index)));
			W4 = FacultyMembers.getAddCourseFee(Integer.parseInt(FileOperations.getWeek4().get(index)));
		}
		
		// Calculating salary of research asistans.
		if (String.valueOf(firstLetter).compareTo("R") == 0) {
			ssBenefits = (int)(1.05 * baseSalary);
		}	
		
		int severancePay = (int) (20 * 0.8 * (2020 - Integer.parseInt(FileOperations.getYearOfStart().get(index))));
		int salary = (int)(baseSalary + ssBenefits + severancePay + W1 + W2 + W3 + W4);
		return salary;
	}
	
	static class FacultyMembers extends Academician{
		// This method returns Academician's add course fees.
		static int getAddCourseFee(int hours) {
			int addCourseFee = 0;
			if (hours < 40) {
				addCourseFee = 0;
			}
			if ((40 <= hours) & (hours <= 48)) {	
				addCourseFee = ((hours - 40) * 20);
			}
			if (48 < hours) {
				addCourseFee = 160;
			}
			return addCourseFee;
		}
	}
	
	static class ResearchAsistant extends Academician{
	}
}