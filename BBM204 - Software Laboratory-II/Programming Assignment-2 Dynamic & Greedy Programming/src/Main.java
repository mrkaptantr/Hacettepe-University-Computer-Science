import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 
import java.io.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
 
public class Main {
    /**
     * Propogated {@link IOException} here
     * {@link #readFile} and {@link #writeOutput} methods should be called here
     * A {@link Scheduler} instance must be instantiated here
     */
    public static void main(String[] args) throws IOException {
        Assignment[] assignmentArray = readFile(args[0]);
        Scheduler scheduler = new Scheduler(assignmentArray);
        writeOutput("solution_dynamic.json", scheduler.scheduleDynamic());
        writeOutput("solution_greedy.json", scheduler.scheduleGreedy());
    }
 
    /**
     * @param filename json filename to read
     * @return Returns a list of {@link Assignment}s obtained by reading the given json file
     * @throws FileNotFoundException If the given file does not exist
     */
    private static Assignment[] readFile(String filename) throws FileNotFoundException {
 
        /* Part-1: File reading and storing operations */
        BufferedReader br = null;
        ArrayList<String> file = new ArrayList<String>();
        int size = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"ISO-8859-1"));
            String line = null;
            while((line = reader.readLine()) != null){
                file.add(line);
                size += 1;
            }
            reader.close();
        } catch (Exception ex) {
            throw new FileNotFoundException();
        }
 
        /* Part-2: File analysis and object creation operations */
        int repeatTime = (size - 2) / 7;
        int check = 0;
        Assignment[] assignment = new Assignment[repeatTime];
        for (int i = 0; i < repeatTime; i++) {
            String name = (file.get(2 + (i * 7))).substring(13, (file.get(2 + (i * 7)).length() - 2));
            String start = (file.get(3 + (i * 7))).substring(14, (file.get(3 + (i * 7)).length() - 2));
            int duration = Integer.parseInt((file.get(4 + (i * 7))).substring(16, (file.get(4 + (i * 7)).length() - 1)));
            int importance = Integer.parseInt((file.get(5 + (i * 7))).substring(18, (file.get(5 + (i * 7)).length() - 1)));
            boolean maellard = Boolean.parseBoolean((file.get(6 + (i * 7))).substring(16));
            Assignment obj = new Assignment(name, start, duration, importance, maellard);
            assignment[check] = obj;
            check += 1;
        }
        return assignment;
    }
 
    /**
     * @param filename  json filename to write
     * @param arrayList a list of {@link Assignment}s to write into the file
     * @throws IOException If something goes wrong with file creation
     */
    private static void writeOutput(String filename, ArrayList<Assignment> arrayList) throws IOException {
        try (FileWriter writer1 = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); // pretty print
            String prettyJson = gson.toJson(arrayList);
            gson.toJson(arrayList, writer1);
 
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
 