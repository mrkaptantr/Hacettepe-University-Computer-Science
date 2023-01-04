import java.util.ArrayList;
import java.util.List;

public class Examination {
	static int cost = 0;
	static test test;
	static measurement measurements;
	static doctorvisit doctorvisit;
	static imaging imaging;
	
	public static void addoperation(String information) { 
		int index = 0;		
		for(int i=0; i<Admission.admissionIDList.size(); i++){
			if (information.split(" ")[1].compareTo(Admission.admissionIDList.get(i)) == 0) {
				index = i;
			}
		}
		int length = 16 + String.valueOf(index).length();
		String addInfo = information.substring(length);
		
		for (int i=0; i<4; i++) {
			try {
				if(addInfo.split(" ")[i].compareTo("tests") == 0) {
					test = new test();
				}
				if(addInfo.split(" ")[i].compareTo("measurements") == 0) {
					measurements = new measurement();
				}
				if(addInfo.split(" ")[i].compareTo("doctorvisit") == 0) {
					doctorvisit = new doctorvisit();
				}
				if(addInfo.split(" ")[i].compareTo("imaging") == 0) {
					imaging = new imaging();
				}
			} catch (Exception e) {}
		}
		
		int lineNumber = 0;
		for(int i=0; i<Admission.Index; i++){
			if((Admission.admissionIDList.get(index) + "\t" + Admission.patientIDList.get(index)).compareTo(Admission.admissionFile[i]) == 0) {
				lineNumber = i;
			}
		}
		
		if(information.split(" ")[2].compareTo("Inpatient") == 0){
			String[] file = Admission.admissionFile;
			file[Admission.Index] = "Inpatient\t" + information.substring(information.indexOf(" ", 25)+1);
						
			for(int i=file.length-1; i > lineNumber; i--){
			   file[i] = file[i-1];
			}
			file[lineNumber+1] = "Inpatient\t" + information.substring(information.indexOf(" ", 25)+1);
			
			Admission.Index += 1; 
			Admission.admissionFile = file;
		}
		
		if(information.split(" ")[2].compareTo("Outpatient") == 0){	
			String[] file = Admission.admissionFile;
			file[Admission.Index] = "Outpatient\t" + information.substring(information.indexOf(" ", 25)+1);
			
			for(int i=file.length-1; i > lineNumber; i--){
				   file[i] = file[i-1];
				}
				file[lineNumber+1] = "Outpatient\t" + information.substring(information.indexOf(" ", 25)+1);
			
			Admission.Index += 1; 
			Admission.admissionFile = file;
		}
		String ID = information.split(" ")[1];
		String type = information.split(" ")[2];
		FileOperations.output.add(type + " examination added to admission " + ID + "\n");
		Admission.analyzeAdmissionFile(Admission.admissionFile, Admission.Index);
	}
	
	int index;
	String inInfo = "";
	String outInfo = "";
	public void cost() {
		int AdmissionID = Admission.ID;
		int inPatientCost = 0;
		int outPatientCost = 0;
		for(int i=0; i<Admission.admissionIDList.size(); i++) {
			if (Integer.parseInt(Admission.admissionIDList.get(i)) == AdmissionID){
				index = i;
			}
		}
				
		try {
			inInfo = Admission.inPatientList.get(index);
			outInfo = Admission.outPatientList.get(index);
			if(inInfo.compareTo("") == 0) {
			} else {
				inPatientCost += 10;
			}
			
			for(int i=0; i<3; i++) {
				if(inInfo.split(" ")[i].compareTo("doctorvisit") == 0){
					inPatientCost += 15;
				}
				if(inInfo.split(" ")[i].compareTo("imaging") == 0){
					inPatientCost += 10;
				}
				if(inInfo.split(" ")[i].compareTo("tests") == 0){
					inPatientCost += 7;
				}
				if(inInfo.split(" ")[i].compareTo("measurements") == 0){
					inPatientCost += 5;
				}
			}	
		} catch (Exception e) {
		}
		
		try {
			if(outInfo.compareTo("") == 0) {
			} else {
				outPatientCost += 15;
			}
			for(int i=0; i<3; i++) {
				if(outInfo.split(" ")[i].compareTo("doctorvisit") == 0){
					outPatientCost += 15;
				}
				if(outInfo.split(" ")[i].compareTo("imaging") == 0){
					outPatientCost += 10;
				}
				if(outInfo.split(" ")[i].compareTo("tests") == 0){
					outPatientCost += 7;
				}
				if(outInfo.split(" ")[i].compareTo("measurements") == 0){
					outPatientCost += 5;
				}
			}	
		} catch (Exception e) {
		}
		
		FileOperations.output.add("Totalcost for admission " + AdmissionID + "\n");
		FileOperations.output.add("\t" + "Inpatient " + inInfo + " " + inPatientCost + "$\n"); 
		FileOperations.output.add("\t" + "Outpatient " + outInfo + " " + outPatientCost + "$\n" ); 
		FileOperations.output.add("\t" + "Total: " + (inPatientCost + outPatientCost) + "$\n"); 
	}
	
	public void printoperations() {
		List<String> checkList = new ArrayList<>();
		try {
			checkList.add(Admission.info.substring(Admission.info.indexOf(" ", 25)+1).split(" ")[0]);
			checkList.add(Admission.info.substring(Admission.info.indexOf(" ", 25)+1).split(" ")[1]);
			checkList.add(Admission.info.substring(Admission.info.indexOf(" ", 25)+1).split(" ")[2]);
		} catch (Exception e) {
		}
		System.out.println(" ");
		System.out.println("Function Of examination.printoperations() Called!");
		for(int i=0; i<checkList.size(); i++){
			if(checkList.get(i).compareTo("tests") == 0){
				System.out.println(test);
			}
			if(checkList.get(i).compareTo("doctorvisit") == 0){
				System.out.println(doctorvisit);
			}
			if(checkList.get(i).compareTo("measurements") == 0){
				System.out.println(measurements);
			}
			if(checkList.get(i).compareTo("imaging") == 0){
				System.out.println(imaging);
			}
		}
		System.out.println(" ");
	}
	
	public static class imaging extends Examination{
		public String toString() {
			return "Imaging operation added!";
		}
	}
	
	public static class doctorvisit extends Examination{
		public String toString() {
			return "Doctor visit operation added!";
		}
	}
	
	public static class test extends Examination{
		public String toString() {
			return "Test operation added!";
		}
	}
	
	public static class measurement extends Examination{
		public String toString() {
			return "Measurement operation added!";
		}
	}
}