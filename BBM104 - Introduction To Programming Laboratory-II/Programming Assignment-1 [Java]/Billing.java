import java.util.List;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class Billing {
	
	public static void preparingBill (){
				
		List<String> customer = FileOperations.getCustomer();
		List<String> type = FileOperations.getTypes();
		List<String> date = FileOperations.getDate();
		List<String> products = FileOperations.getProducts();
		List<String> quantities = FileOperations.getQuantities();
		List<Integer> numberOfType = FileOperations.getNumOfType();
	
		int productIndex = 0; // We use it for finding products with numberOfType's items.	
		int quantityIndex = 0; // We use it for reaching correct index of quantities array list.
		int customerCount = customer.size(); // Number of customers.
		double cost = 0;
		List<Double> totalForProduct = new ArrayList<Double>(); 
		// List of products' costs will be calculated with quantities.
		
		// This part will print the billing information for every customer.
		for(int i=0; i<customerCount; i++) {
			System.out.println("---" + customer.get(i) + "---");
			List<String> buyed = products.subList(productIndex, productIndex + numberOfType.get(i));
			for(int j=0; j<buyed.size(); j++) {
		        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		        try {
	            Date now = formatter.parse(date.get(i));
	            cost += getCost(now, buyed.get(j), type.get(i));
	            double costPerUnit = (cost * Integer.parseInt(quantities.get(quantityIndex)));
	            System.out.println(buyed.get(j) + "\t" + cost + "\t" + quantities.get(quantityIndex) + "\t" + Double.toString(costPerUnit));
		        totalForProduct.add(cost * Integer.parseInt(quantities.get(quantityIndex)));
		        quantityIndex += 1;
		        cost = 0;
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
			}
			double sum = 0;
			for(int k = 0; k < totalForProduct.size(); k++)
			    sum += totalForProduct.get(k);
			totalForProduct.clear();
			System.out.println("Total" + "\t" + sum);
			productIndex += numberOfType.get(i);
			}
		}
	
		public static double getCost (Date now, String productBought, String checkType) {
			// With this method, we learn cost of products. 
			List<String> productName = FileOperations.getName();
			List<String> memberShipType = FileOperations.getType();
			List<String> startDate = FileOperations.getStartDate();
			List<String> endDate = FileOperations.getEndDate();
			List<String> price = FileOperations.getPrice();
			Set<Integer> possibilities = new HashSet<Integer>();
			Set<Integer> possibilities2 = new HashSet<Integer>();
			Set<Integer> possibilities3 = new HashSet<Integer>();
			
			for(int i=0; i<productName.size() ; i++) {
					if (productBought.compareTo(productName.get(i)) == 0){
					possibilities.add(i);				
				}
			}
			
			for(int i=0; i<memberShipType.size(); i++) {
				if (checkType.compareTo(memberShipType.get(i)) == 0) {
					possibilities2.add(i);
				}
			}

			for(int i=0; i<startDate.size(); i++) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
				try {
				Date start = formatter.parse(endDate.get(i));
				Date end = formatter.parse(startDate.get(i));
				if (now.compareTo(end) >= 0 && now.compareTo(start) <= 0) {
					possibilities3.add(i);}
				} catch (ParseException e) {
		            e.printStackTrace();
		        }
			}

			// With intersection of sets, we learn real cost value of product.
			possibilities.retainAll(possibilities2);
			possibilities.retainAll(possibilities3);
			String Cost = price.get((int)(possibilities.toArray()[0]));
			Double cost = 5.0;
			return Double.parseDouble(Cost);
	}	
}
