import java.util.Comparator;

public class PhoneNumberComparator implements Comparator<Contact>{

	public int compare(Contact e1, Contact e2) {
	    return(Contact.getPhoneNumber(e1).compareTo(Contact.getPhoneNumber(e2)));
	}
}
