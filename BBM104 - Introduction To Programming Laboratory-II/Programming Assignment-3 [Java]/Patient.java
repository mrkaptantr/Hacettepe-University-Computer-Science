import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Patient implements DAO{
	
	@Override
	public Object getByID(int ID) {
		for(int i=0; i<patientID.size(); i++){
			if(Integer.parseInt(patientID.get(i)) == (ID)){
				return patientFile[i];
			}
		}
		return null;
	}

	@Override
	public Object deleteByID(int ID) {
		for(int i=0; i<patientID.size(); i++){
			if(Integer.parseInt(patientID.get(i)) == (ID)){
				patientID.remove(i);
			}
		}
		return patientID;
	}

	@Override
	public void add(Object object) {
		newPatient((String) object);
	}

	public static List<?> getAll(String requiredList) {
		if(requiredList.compareTo("getPatientID") == 0){
			return patientID;
		}
		if(requiredList.compareTo("getPatient") == 0){
			return nameAndSurname;
		}
		if(requiredList.compareTo("getPhone") == 0){
			return phoneNumber;
		}
		if(requiredList.compareTo("getAddress") == 0){
			return address;
		}
		return null;
	}	
	
	// Variables and data files
	public static String[] patientFile;
	public static List<String> patientID = new ArrayList<>();
	public static List<String> nameAndSurname = new ArrayList<>();
	public static List<String> phoneNumber = new ArrayList<>();
	public static List<String> address = new ArrayList<>();
	public static int patientFileSize;
	
	// This method analyzes and makes required database operations of patient file.
	public static void analyzePatientFile(String[] datas, int index){
		patientID.clear();
		nameAndSurname.clear();
		phoneNumber.clear();
		address.clear();
		patientFile = datas;
		patientFileSize = index;
		for(int i=0; i<index; i++) {
			patientID.add(datas[i].split("\t")[0]);
			nameAndSurname.add(datas[i].split("\t")[1]);
			phoneNumber.add(datas[i].split("\t")[2]);
			address.add(datas[i].split("\t")[3]);
		}
	}
	
	static List<String> patientOutput = new ArrayList<>();
	public static List<String> getPatientOutput(){
		for(int k=0; k<patientFileSize; k++) {
			patientOutput.add(patientFile[k] + "\n");
		}	
		return patientOutput;
	}
	
	// This method adds a new patient in our hospital system.
	public static void newPatient(String information){
		String[] datas = patientFile;
		String newData = information.replaceFirst(" ", "\t");
		newData = newData.replaceFirst(" ", "\t");
		newData = newData.replaceFirst(" ", "\t");
		newData = newData.replaceFirst(" ", "\t");
		int index = newData.lastIndexOf("\t");
		newData = newData.substring(0, index) + "\t" + "Address: " + newData.substring(index+1);
		newData = newData.replaceFirst("\t", " ");
		newData = newData.replaceFirst("\t", " ");
		newData = newData.replaceFirst(" ", "\t");		
		datas[patientFileSize] = newData;		
		datas = FileOperations.bubbleSort(patientFileSize+1, datas);
		analyzePatientFile(datas, patientFileSize+1);
		FileOperations.output.add("Patient " + information.split(" ")[0] + " " + information.split(" ")[1] + " added\n");
	}
	
	static String admissionID = "";
	static int indexOfPatient = 0;
	static int beginIndex = 0;
	static int endIndex = 0;
	
	public static void removePatient(int ID){
		// This method removes a patient from the database by using its identification number.
		// This method also removes information in admission file.
		
		for (int m=0; m<Admission.patientIDList.size(); m++ ) {
			if (Integer.parseInt(Admission.patientIDList.get(m)) == (ID)) {
					
		for(int i=0; i<Admission.getPatientIDList().size(); i++) {
			if(ID == Integer.parseInt(Admission.getPatientIDList().get(i))) {
				indexOfPatient = i;
			}
		}
				
		for(int i=0; i<patientID.size(); i++) {
			if(ID == Integer.parseInt(patientID.get(i))) {
				FileOperations.output.add("Patient " + ID + " " + nameAndSurname.get(i).split(" ")[0] + " removed\n");
			}
		}
		
		int index = 0;
		for(int i=0; i<patientFileSize; i++) {			
			if(String.valueOf(patientFile[i].split("\t")[0]).compareTo(String.valueOf(ID)) == 0) {
				index = i;	
			}
		}		
		
		for (int i=0; i<Admission.Index; i++) {
			if (Admission.getAdmissionFile()[i].compareTo(Admission.getAdmissionIDList().get(indexOfPatient) + "\t" + Admission.getPatientIDList().get(indexOfPatient)) == 0) {
				beginIndex = i;
			}
		}
		
		String[] datas = Admission.getAdmissionFile();
		if (indexOfPatient != Admission.getAdmissionIDList().size()-1) {
			try {
			for (int i=0; i<Admission.Index; i++) {
				if (Admission.getAdmissionFile()[i].compareTo(Admission.getAdmissionIDList().get(indexOfPatient+1) + "\t" + Admission.getPatientIDList().get(indexOfPatient+1)) == 0) {
					endIndex = i;		
				for (int j=0; j<(endIndex-beginIndex); j++) {
					List<String> lists = new ArrayList<String>(Arrays.asList(datas));
					lists.remove(beginIndex);
					Admission.Index = Admission.Index - 1;
					datas = lists.toArray(datas);}
				}
			Admission.admissionFile = datas;
			Admission.analyzeAdmissionFile(datas, Admission.Index);
			}
		} catch (Exception e) {}}
			

		else if (indexOfPatient == Admission.getAdmissionIDList().size()-1) {
			for (int i=0; i<(Admission.Index)-beginIndex; i++) {
				List<String> lists = new ArrayList<String>(Arrays.asList(datas));
				lists.remove(beginIndex);
				datas = lists.toArray(datas);
			}
			Admission.admissionFile = datas;
			Admission.analyzeAdmissionFile(datas, Admission.Index -1);
			Admission.Index = beginIndex;
		}
				
		String[] data = patientFile;
		List<String> list = new ArrayList<String>(Arrays.asList(data));
		list.remove(list.get(index));
		data = list.toArray(new String[0]);		
		analyzePatientFile(data, patientFileSize-1);
		
			}
		}
	}

	// This method prints the list of patients ordered by name.
	public static void listPatients(){
		String[] myArray = patientFile;	
		for(int j=0; j<patientFileSize; j++) {
			for (int i=j+1; i<patientFileSize; i++) {	
				if(myArray[i].split("\t")[1].compareTo(myArray[j].split("\t")[1]) < 0) {
					String temp = myArray[j];
					myArray[j] = myArray[i];
					myArray[i] = temp;
				}
			}
		}
		FileOperations.output.add("Patient List:\n");		
		for(int k=0; k<patientFileSize; k++) {
			FileOperations.output.add(myArray[k] + "\n");
		}	
		
		System.out.println("*****Patient List*****");
		System.out.println("(Ordered By PatientID)");
		String[] myArray2 = patientFile;
		for(int j=0; j<patientFileSize; j++) {
			for (int i=j+1; i<patientFileSize; i++) {	
				if(Integer.parseInt(myArray2[i].split("\t")[0]) < Integer.parseInt((myArray2[j].split("\t")[0]))) {
					String temp = myArray2[j];
					myArray2[j] = myArray2[i];
					myArray2[i] = temp;
				}
			}
		}
		for(int j=0; j<patientFileSize; j++) {
			System.out.println(myArray2[j]);
		}	
	}
}