import java.util.ArrayList;
import java.util.Date;
import java.util.List; 

public class FileOperations {
	// Lists of shopping list operations 
	static List<String> nameAndSurname = new ArrayList<>();
	static List<String> membershipType = new ArrayList<>();
	static List<String> shoppingDate = new ArrayList<>();
	static List<String> productNames = new ArrayList<>();
	static List<String> quantities = new ArrayList<>();
	static List<Integer> numberOfBuyType = new ArrayList<>();
	
	// Lists of price list operations 
	static List<String> productName = new ArrayList<>();
	static List<String> memberShipType = new ArrayList<>();
	static List<String> startDate = new ArrayList<>();
	static List<String> endDate = new ArrayList<>();
	static List<String> price = new ArrayList<>();
			
	public static void shoppingListOperations(String[] data, int Index){ 

		// With this method, we convert and save every type of shopping list values in array lists for every customer.		
		int lengthOfLine = 0;
		
		for(int i=0; i<Index; i++) {
			lengthOfLine = data[i].split("\t").length;
			nameAndSurname.add(data[i].split("\t")[0]);
			membershipType.add(data[i].split("\t")[1]);
			shoppingDate.add(data[i].split("\t")[2]);
			for(int j=3; j<lengthOfLine ; j = j+2) {
				productNames.add(data[i].split("\t")[j]);}
			for(int j=4; j<lengthOfLine ; j = j+2) {
				quantities.add(data[i].split("\t")[j]);	
			}
			numberOfBuyType.add((lengthOfLine-3) / 2);
		}
		if (nameAndSurname.size() > 10){
			// We're checking rule of "shopping list contains maximum 10 users".
			System.out.println("Shopping list can contain maximum 10 users!");
			System.exit(0);
		}
		Billing.preparingBill();
	}
	
	public static void priceListOperations(String[] data, int Index){ 
		
		// With this method, we convert and save every type of price list values in array lists for every customer.
		for(int m=0; m<Index; m++) {			
			productName.add(data[m].split("\t")[0]);
			memberShipType.add(data[m].split("\t")[1]);
			startDate.add(data[m].split("\t")[2]);
			endDate.add(data[m].split("\t")[3]);
			price.add(data[m].split("\t")[4]); 		
		}
	}

	// Get methods of shopping list operations
	public static List<String> getCustomer(){
		return nameAndSurname;
	}
	public static List<String> getTypes(){
		return membershipType;
	}
	public static List<String> getDate(){
		return shoppingDate;
	}
	public static List<String> getProducts(){
		return productNames;
	}
	public static List<String> getQuantities(){
		return quantities;
	}
	public static List<Integer> getNumOfType(){
		return numberOfBuyType;
	}
	
	// Get methods of price list operations
	public static List<String> getName(){
		return productName;
	}
	public static List<String> getType(){
		return memberShipType;
	}
	public static List<String> getStartDate(){
		return startDate;
	}
	public static List<String> getEndDate(){
		return endDate;
	}
	public static List<String> getPrice(){
		return price;
	}
}