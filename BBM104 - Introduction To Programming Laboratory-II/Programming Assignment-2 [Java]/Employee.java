public class Employee extends Personel {

	private static int severancePay;
	private static int W1, W2, W3, W4;
	private static int salary;
	
	static int getSalary(int index, char firstLetter) {
		int severancePay = (int) (20 * 0.8 * (2020 - Integer.parseInt(FileOperations.getYearOfStart().get(index))));
		
		// Calculating salary of part time employees.
		if (String.valueOf(firstLetter).compareTo("P") == 0) {
			W1 = PartTime.getSalary(Integer.parseInt(FileOperations.getWeek1().get(index)));
			W2 = PartTime.getSalary(Integer.parseInt(FileOperations.getWeek2().get(index)));
			W3 = PartTime.getSalary(Integer.parseInt(FileOperations.getWeek3().get(index)));
			W4 = PartTime.getSalary(Integer.parseInt(FileOperations.getWeek4().get(index)));
			salary = W1 + W2 + W3 + W4 + severancePay;
		}
		
		// Calculating salary of workers, which are full time employees.
		if (String.valueOf(firstLetter).compareTo("W") == 0) {
			W1 = FullTime.Worker.getOverWorkSalary(Integer.parseInt(FileOperations.getWeek1().get(index)));
			W2 = FullTime.Worker.getOverWorkSalary(Integer.parseInt(FileOperations.getWeek2().get(index)));
			W3 = FullTime.Worker.getOverWorkSalary(Integer.parseInt(FileOperations.getWeek3().get(index)));
			W4 = FullTime.Worker.getOverWorkSalary(Integer.parseInt(FileOperations.getWeek4().get(index)));
			salary = W1 + W2 + W3 + W4 + FullTime.Worker.getDaysPayment() + severancePay;
			
		// Calculating salary of chiefs, which are full time employees.	
		}
		if (String.valueOf(firstLetter).compareTo("C") == 0) {
			W1 = FullTime.Chief.getOverWorkSalary(Integer.parseInt(FileOperations.getWeek1().get(index)));
			W2 = FullTime.Chief.getOverWorkSalary(Integer.parseInt(FileOperations.getWeek2().get(index)));
			W3 = FullTime.Chief.getOverWorkSalary(Integer.parseInt(FileOperations.getWeek3().get(index)));
			W4 = FullTime.Chief.getOverWorkSalary(Integer.parseInt(FileOperations.getWeek4().get(index)));
			salary = W1 + W2 + W3 + W4 + FullTime.Chief.getDaysPayment() + severancePay;
		}
		return salary;
	}
	
	static class PartTime extends Employee{
		
		// This method returns salary (without severance pay) of part time employees.
		static int getSalary(int hourOfWork) {
			if (hourOfWork < 10) {
				return 0;
			}
			if ((10 <= hourOfWork) & (hourOfWork <= 20)) {
				return (hourOfWork * 18);
			}
			if (20 < hourOfWork) {
				return 360;
			}
			return 0;
		}	
	}
	
	static class FullTime extends Employee{
		protected static int dayOfWork = 20;
		protected static int overWorkSalary;	
		
		static class Worker extends FullTime{
			// This method returns over work salary of full time workers.
			static int getOverWorkSalary(int hours) {

				overWorkSalary = 0;
				if (hours < 40) {
					overWorkSalary = 0;
				}
				if ((40 <= hours) & (hours <= 50)) {	
					overWorkSalary = ((hours - 40) * 11);
				}
				if (50 < hours) {
					overWorkSalary = 110;
				}
				return overWorkSalary;
			}
			static int getDaysPayment() {
				// Returns payment of every one of days.
				return (dayOfWork * 105);
			}			
		}
		static class Chief extends FullTime{
			// This method returns over work salary of full time chiefs.
			static int getOverWorkSalary(int hours) {
				overWorkSalary = 0;
				if (hours < 40) {
					overWorkSalary = 0;
				}
				if ((40 <= hours) & (hours <= 48)) {	
					overWorkSalary = ((hours - 40) * 15);
				}
				if (48 < hours) {
					overWorkSalary = 120;
				}
				return overWorkSalary;
			}
			static int getDaysPayment() {
				// Returns payment of every one of days.
				return (dayOfWork * 125);
			}			
		}
	}
}