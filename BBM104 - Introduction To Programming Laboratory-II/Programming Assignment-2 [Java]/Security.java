public class Security extends Personel{
	private static int hourOfWork;
	private static int severancePay;
	private static int W1, W2, W3, W4;
	private static int transMoney = 5;
	private static int foodMoney = 10;

	// Returns payment of every one of days.
	static int getSalary(int index) {
		W1 = getPayment(Integer.parseInt(FileOperations.getWeek1().get(index)));
		W2 = getPayment(Integer.parseInt(FileOperations.getWeek2().get(index)));
		W3 = getPayment(Integer.parseInt(FileOperations.getWeek3().get(index)));
		W4 = getPayment(Integer.parseInt(FileOperations.getWeek4().get(index)));			
		severancePay = (int) (20 * 0.8 * (2020 - Integer.parseInt(FileOperations.getYearOfStart().get(index))));
		int salary = (severancePay + W1 + W2 + W3 + W4);
		return salary;
	}

	static int getPayment(int hourOfWork) {
		// Returns payment of every one of weeks.
		if (hourOfWork < 30) {
			return 0;
		}
		if ((30 <= hourOfWork) & (hourOfWork <= 54)) {
			return ((hourOfWork * 10) + 6 * (transMoney + foodMoney));
		}
		if (54 < hourOfWork) {
			return (hourOfWork * 10) + 6 * (transMoney + foodMoney);
		}
		return 0;
	}
}
