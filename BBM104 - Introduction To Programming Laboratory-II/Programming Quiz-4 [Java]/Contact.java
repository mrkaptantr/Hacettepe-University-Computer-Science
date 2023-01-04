import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class Contact implements Comparable<Contact>{
	
	private String socialSecurityNumber;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	
	public static String getSocialSecurityNumber(Contact contact) {return contact.socialSecurityNumber;}
	public static String getFirstName(Contact contact) {return contact.firstName;}
	public static String getLastName(Contact contact) {return contact.lastName;}
	public static String getPhoneNumber(Contact contact) {return contact.phoneNumber;}
	
	public Contact(String pN, String fN, String lN, String sSN) {
		this.socialSecurityNumber = sSN;
		this.firstName = fN;
		this.lastName = lN;
		this.phoneNumber = pN;
	}
	
	public static void StoreData(List<String> data, int index) {
		List<String> mapKeys = new ArrayList<String>();
		HashSet<Contact> dataHashSet = new HashSet<Contact>(); 
		HashMap<String, Contact> dataHashMap = new HashMap<String, Contact>();
		TreeSet<Contact> dataTreeSet = new TreeSet<Contact>(new PhoneNumberComparator());
		TreeSet<Contact> lastNameDataTreeSet = new TreeSet<Contact>(new LastNameComparator());
		for (int i=0; i<index; i++) {
			Contact contact = new Contact(data.get(i).split(" ")[0], data.get(i).split(" ")[1], data.get(i).split(" ")[2],data.get(i).split(" ")[3]);
			listOfContacts.add(contact);		
			dataTreeSet.add(contact);
			lastNameDataTreeSet.add(contact);
			if (!(dataHashMap.containsKey(contact.phoneNumber))) {
				dataHashSet.add(contact);
				dataHashMap.put(contact.phoneNumber, contact);
				mapKeys.add(contact.phoneNumber);
			}
		}		
		
		FileOperations.writeOutputWithList("contactsArrayList", listOfContacts);
		Collections.sort(listOfContacts, new LastNameComparator());
		FileOperations.writeOutputWithList("contactsArrayListOrderedByLastName", listOfContacts);
		FileOperations.writeOutputWithTree("contactsTreeSet", dataTreeSet);
		FileOperations.writeOutputWithTree("contactsTreeSetOrderedByLastName", lastNameDataTreeSet);
		FileOperations.writeOutputWithMap("contactsHashMap", dataHashMap, mapKeys);
		FileOperations.writeOutputWithHash("contactsHashSet", dataHashSet);
		
	}	
}