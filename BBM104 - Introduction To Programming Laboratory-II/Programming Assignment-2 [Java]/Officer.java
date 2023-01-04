public class Officer extends Personel {
	
	private static int baseSalary = 2600;
	private static int ssBenefits = (int)(baseSalary * (0.65));
	private static int W1, W2, W3, W4;
	private static int severancePay;
	private static int overWorkSalary;

	static int getSalary(int index) {
		// Returns total salary of officers.
		W1 = getOverWorkSalary(Integer.parseInt(FileOperations.getWeek1().get(index)));
		W2 = getOverWorkSalary(Integer.parseInt(FileOperations.getWeek2().get(index)));
		W3 = getOverWorkSalary(Integer.parseInt(FileOperations.getWeek3().get(index)));
		W4 = getOverWorkSalary(Integer.parseInt(FileOperations.getWeek4().get(index)));		
		
		severancePay = (int) (20 * 0.8 * (2020 - Integer.parseInt(FileOperations.getYearOfStart().get(index))));
		int salary = baseSalary + ssBenefits + severancePay + W1 + W2 + W3 + W4;		
		return salary;
	}
	
	static int getOverWorkSalary(int hours) {
		// This method returns over work salary of officers.
		overWorkSalary = 0;
		if (hours < 40) {
			overWorkSalary = 0;
		}
		if ((40 <= hours) & (hours <= 50)) {	
			overWorkSalary = ((hours - 40) * 20);
		}
		if (50 < hours) {
			overWorkSalary = 200;
		}
		return overWorkSalary;
	}
}