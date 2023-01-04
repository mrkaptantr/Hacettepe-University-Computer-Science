import java.util.Comparator;

public class LastNameComparator implements Comparator<Contact>{
	public int compare(Contact a, Contact b) { 
		return Contact.getLastName(a).compareTo(Contact.getLastName(b));
	} 
} 