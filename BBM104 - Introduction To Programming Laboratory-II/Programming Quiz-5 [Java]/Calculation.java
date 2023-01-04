public class Calculation {
	
	public static Stack<Integer> output = new Stack<Integer>();
	
	public static void CalculateOctal(Stack<Integer> data, int index) {
		for(int i=0; i<index; i++) {
			int infoSize = 0;
			Stack<Integer> info = new Stack<Integer>();
			int mainNumber = data.Pop();
			if (mainNumber == 0) {
		            info.push(0);
		            infoSize += 1;
		    }
		    while (mainNumber > 0) {
		    	int digit = mainNumber % 8;
		        info.push(digit);
		        infoSize += 1;
		        mainNumber = mainNumber / 8;
		    }			
		    output.add(0);
			for(int j=0; j<infoSize; j++) {
				int datas = info.Pop();
				output.add(Integer.parseInt(String.valueOf(output.Pop()) + String.valueOf(datas)));
			}
		}
		FileOperations.writeOutputWithStack("octal", output);		
	}
}