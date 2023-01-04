import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admission implements DAO{
	
	// Implementing Data Access Object Operations
	@Override
	public Object getByID(int ID) {
		for(int i=0; i<patientIDList.size(); i++){
			if(Integer.parseInt(patientIDList.get(i)) == (ID)){
				return admissionFile[i];
			}
		}
		return null;
	}
		
	@Override
	public Object deleteByID(int ID) {
		for(int i=0; i<Index; i++){
			if(Integer.parseInt(admissionIDList.get(i)) == (ID)){
				admissionIDList.remove(i);
			}
		}
		return admissionIDList;
	}

	@Override
	public void add(Object object) {
		addExamination((String) object);
	}

	public static List<?> getAll(String requiredList) {
		if(requiredList.compareTo("getAdmissionIDList") == 0){
			return admissionIDList;
		}
		if(requiredList.compareTo("getIDList") == 0){
			return patientIDList;
		}
		if(requiredList.compareTo("getInPatientList") == 0){
			return inPatientList;
		}
		if(requiredList.compareTo("getOutPatientList") == 0){
			return outPatientList;
		}
		return null;
	}
	
	// Variables and data files
	public static int Index;
	public static String requiredList;
	public static String[] admissionFile;
	public static String[] getAdmissionFile() {return admissionFile;}
	public static List<String> getAdmissionIDList() {return admissionIDList;}
	public static List<String> getPatientIDList() {return patientIDList;}
	public static List<String> admissionIDList = new ArrayList<>();
	public static List<String> patientIDList = new ArrayList<>();
	public static List<String> inPatientList = new ArrayList<>();
	public static List<String> outPatientList = new ArrayList<>();
	
	// This method analyzes admission file and makes required data operations.
	public static void analyzeAdmissionFile(String[] data, int index){
		admissionIDList.clear();
		patientIDList.clear();
		inPatientList.clear();
		outPatientList.clear();
		
		int i=-1;
		Index = index;
		admissionFile = data.clone();
		try {
			for (int j=0; j<index; j++){
				if (data[j].split("\t")[0].compareTo("Inpatient") == 0) {
					inPatientList.add("");
					inPatientList.set(i, (inPatientList.get(i) + " " + data[j].split("\t")[1]).trim());
				}
				else if (data[j].split("\t")[0].compareTo("Outpatient") == 0) {
					outPatientList.add("");
					outPatientList.set(i, (outPatientList.get(i) + " " + data[j].split("\t")[1]).trim());
				}
				else {
					admissionIDList.add(data[j].split("\t")[0]);
					patientIDList.add(data[j].split("\t")[1]);
					i += 1;
				} 
			} 
		} catch (Exception e) {		
		}
	} 
	
	// This method creates admission with using admission ID and patient ID for given patient.
	public static void createAdmission(int AdmissionID, int PatientID){		
		String[] data = admissionFile;
		data[Index] = (AdmissionID + "\t" + PatientID);
		admissionFile[Index] = AdmissionID + "\t" + PatientID;
		Index = Index + 1;
		List<String> aIDList = admissionIDList;
		aIDList.add(String.valueOf(AdmissionID));
		List<String> pIDList = patientIDList;
		pIDList.add(String.valueOf(PatientID));
		List<String> inList = inPatientList;
		inList.add("");
		List<String> outList = outPatientList;
		outList.add("");
		admissionIDList = aIDList;
		patientIDList = pIDList;
		inPatientList = inList;
		outPatientList = outList;	
		FileOperations.output.add("Admission " + AdmissionID + " created\n");
	}
	
	// This method creates examination with using given information on input.txt file.,
	static String info;
	public static void addExamination(String information){
		info = information;
		Examination examination = new Examination();
		Examination.addoperation(information);
		examination.printoperations();
	}
	
	static int index;
	static int ID;
	// This method returns total cost.
	public static void totalCost(int AdmissionID){
		ID = AdmissionID;
		Examination examination = new Examination();
		examination.cost();	
	}	
}