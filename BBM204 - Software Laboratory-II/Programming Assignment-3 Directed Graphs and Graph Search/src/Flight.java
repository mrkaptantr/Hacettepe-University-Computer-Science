import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Flight {

	private String flightID;
	private String dept;
	private String arr;
	private String deptDate;
	private String duration;
	private String price;
	private String arrDate;
		
	public String getflightID() { return this.flightID; }
	public String getdept() { return this.dept; }
	public String getarr() { return this.arr; }
	public String getdeptDate() { return this.deptDate; }
	public String getduration() { return this.duration; }
	public String getprice() { return this.price; }
	
	// Arrive date calculator
	public Date getarrDate() throws ParseException { 
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date duration = parser.parse(this.duration);
		Date start = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(this.getdeptDate());
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);		
		cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(this.duration.substring(0,2)));
		cal.add(Calendar.MINUTE, Integer.parseInt(this.duration.substring(3,5)));
		arrDate = String.valueOf(cal.getTime());
		return cal.getTime();
	}

	public Flight(String flightID, String dept, String arr, String deptDate, String duration, String price) {
		this.flightID = flightID;
		this.dept = dept;
		this.arr = arr;
		this.deptDate = deptDate;
		this.duration = duration;
		this.price = price;	
	}
	
	// Clone generator (Required for some methods in airport system.java)
	public Flight (Flight x) {
		this.flightID = flightID;
		this.dept = dept;
		this.arr = arr;
		this.deptDate = deptDate;
		this.duration = duration;
		this.price = price;			
	}
}
