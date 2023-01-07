import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
 
public class Scheduler {
 
    private Assignment[] assignmentArray;
    private Integer[] C;
    private Double[] max;
    private ArrayList<Assignment> solutionDynamic;
    private ArrayList<Assignment> solutionGreedy;
 
    /**
     * @throws IllegalArgumentException when the given array is empty
     */
    public Scheduler(Assignment[] assignmentArray) throws IllegalArgumentException {
        try {
            this.assignmentArray = assignmentArray;
            Arrays.sort(assignmentArray);
 
            // Finding compatitible assignments
            this.C = new Integer[assignmentArray.length];
            calculateC();
 
            // Calculating the total maximum value
            this.max = new Double[assignmentArray.length];
            calculateMax(assignmentArray.length - 1);
 
            // Dynamic solution part of assignment
            this.solutionDynamic = new ArrayList<Assignment>();
            findSolutionDynamic(assignmentArray.length - 1);
 
            // Greedy solution part of assignment
            //this.solutionGreedy = scheduleGreedy();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
 
    /**
     * @param index of the {@link Assignment}
     * @return Returns the index of the last compatible {@link Assignment},
     * returns -1 if there are no compatible assignments
     */
    private int binarySearch(int index) {
        int lo = 0; int hi = index - 1;
        while (lo <= hi)
        {
            int mid = (lo + hi) / 2;
            if (assignmentArray[mid].getFinishTime().compareTo(assignmentArray[index].getStartTime()) <= 0) {
                if (assignmentArray[mid + 1].getFinishTime().compareTo(assignmentArray[index].getStartTime()) <= 0)
                    lo = mid + 1;
                else
                    return mid;
            }
            else
                hi = mid - 1;
        }
        return -1;
    }
 
    /**
     * {@link #C} must be filled after calling this method
     */
    private void calculateC() {
        for(int i=0; i<assignmentArray.length; i++) {
            C[i] = binarySearch(i);
        }
    }
 
 
    /**
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleDynamic() {
        Collections.reverse(solutionDynamic);
        return solutionDynamic;
    }
 
    /**
     * {@link #solutionDynamic} must be filled after calling this method
     */
    private void findSolutionDynamic(int i) {
        System.out.println("findSolutionDynamic("+i+")");
        if (i==0){
            if (solutionDynamic.contains(assignmentArray[i])==false) {
                solutionDynamic.add(assignmentArray[0]);
                System.out.println("Adding "+assignmentArray[i].toString()+" to the dynamic schedule");
            }
            return;
        }
        if (C[i]!=-1){
 
            if (max[C[i]]+assignmentArray[i].getWeight()>max[i-1]){
                if (solutionDynamic.contains(assignmentArray[i])==false) {
                    solutionDynamic.add(assignmentArray[i]);
                    System.out.println("Adding "+assignmentArray[i].toString()+" to the dynamic schedule");
                } findSolutionDynamic(C[i]);
                return;
            }
            else if (solutionDynamic.contains(assignmentArray[i-1])==false){
                //solutionDynamic.add(assignmentArray[i-1]);
                //System.out.println("Adding "+assignmentArray[i-1].toString()+" to the dynamic schedule");
                findSolutionDynamic(i-1);
                return;
            }
        }
        else if (C[i]==-1){
            if (assignmentArray[i].getWeight()>max[i-1]){
                solutionDynamic.add(assignmentArray[i]);
                System.out.println("Adding "+assignmentArray[i].toString()+" to the dynamic schedule");
                return;
            }
            else if (solutionDynamic.contains(assignmentArray[i-1])==false){
                //solutionDynamic.add(assignmentArray[i-1]);
                //System.out.println("Adding "+assignmentArray[i-1].toString()+" to the dynamic schedule");
                findSolutionDynamic(i-1);
                return;
            }
        }
    }
 
    /**
     * {@link #max} must be filled after calling this method
     */
    private Double calculateMax(int i) {
        if (i==-1){
            return 0.0;
        }
        else if (i==0) {
            System.out.println("calculateMax(0): Zero");
            max[0]=assignmentArray[0].getWeight();
            return max[0];
        }
        if (max[i]==null){
            System.out.println("calculateMax("+i+"): Prepare");
            double doub=assignmentArray[i].getWeight()+calculateMax(C[i]);
            double d=calculateMax(i-1);
            if (d>doub) {
                max[i]=d;
                return d;
            }
            max[i]=doub;
            return doub;
        }
        else {
            System.out.println("calculateMax("+i+"): Present");
            return max[i];
        }
    }
 
    private Double max(Double a, Double b) {
        return (a>b ? a:b);
    }
 
    /**
     * {@link #solutionGreedy} must be filled after calling this method
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleGreedy() {
        ArrayList<Assignment> solution = new ArrayList<Assignment>();
        solution.add(assignmentArray[0]);
        System.out.println("Adding "+assignmentArray[0].toString()+" to the greedy schedule");
        int index = 0;
        String time = assignmentArray[0].getFinishTime();
 
        for(int i=1; i<assignmentArray.length; i++){
            if (assignmentArray[i].getStartTime().compareTo(assignmentArray[index].getFinishTime()) >= 0) {
                index = i;
                time = assignmentArray[i].getFinishTime();
                solution.add(assignmentArray[i]);
                System.out.println("Adding "+assignmentArray[i].toString()+" to the greedy schedule");
            }
        }
        return solution;
    }
}
 