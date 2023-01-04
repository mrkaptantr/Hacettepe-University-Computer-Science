import java.util.List;

public interface DAO {
	// Interface of our programs data manipulations.
	// Admission.java and Patient.java files implement this interface.
	public abstract Object getByID(int ID);
	public abstract Object deleteByID(int ID);
	public abstract void add(Object object);
	public static List<?> getAll(String fileName){
		return null;
	}
}
