import java.util.ArrayList;
import java.util.List;

public class Volleyball extends Sports{
	
	// They are variables, which we will use in calculations
	static int index = 0;
	static List<Integer> players = new ArrayList<>();
	static List<String> clubs = FileOperations.getFirstClub();
	static List<String> clubs2 = FileOperations.getSecondClub();
	static List<String> result = FileOperations.getResult();
	static List<String> clubName = new ArrayList<>();
	static List<String> printFile = new ArrayList<>();
	static List<String> clubNames = new ArrayList<>();
	static int[] scores;
	static int[] win;
	static int[] tie;
	static int[] lose;
	static int[] sets;
	static String[] sets2;

	public static void calculateResults(List<String> typeOfSport) {
		// First of all, we analyze and find player teams and get rid of duplicated elements.
		for (int i=0; i<typeOfSport.size(); i++) {
			if (typeOfSport.get(i).compareTo("V") == 0){
				players.add(i);
			}
		}
		
		for (int m=0; m<players.size() ; m++) {
			clubName.add(clubs.get(players.get(m)));
		}
		
		clubNames = Sports.removeDuplicates(clubName);
		scores = new int[clubNames.size()];
		win = new int[clubNames.size()];
		tie = new int[clubNames.size()];
		lose = new int[clubNames.size()];
		sets = new int[clubNames.size() * 2];
		sets2 = new String[clubNames.size()];
				
		// We'll think every situation and give points to teams with these for-if methods.
		for (int j=0; j<players.size(); j++) {
			if ((Integer.parseInt(result.get(players.get(j)).split(":")[0]) == 3) & (Integer.parseInt(result.get(players.get(j)).split(":")[1]) == 0)) {
				int index = (clubNames.indexOf(clubs.get(players.get(j))));
				scores[index] = scores[index] + 3;
				win[index] = win[index] + 1;
				sets[index * 2] = sets[index * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				sets[(index * 2) + 1] = sets[(index * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				int index2 = (clubNames.indexOf(clubs2.get(players.get(j))));
				scores[index2] = scores[index2];
				lose[index2] = lose[index2] + 1;
				sets[index * 2] = sets[index * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				sets[(index2 * 2) + 1] = sets[(index2 * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				}
			if ((Integer.parseInt(result.get(players.get(j)).split(":")[0]) == 3) & (Integer.parseInt(result.get(players.get(j)).split(":")[1]) == 1)) {
				int index = (clubNames.indexOf(clubs2.get(players.get(j))));
				scores[index] = scores[index];	
				lose[index] = lose[index] + 1;
				sets[index * 2] = sets[index * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				sets[(index * 2) + 1] = sets[(index * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				int	index2 = (clubNames.indexOf(clubs.get(players.get(j))));
				scores[index2] = scores[index2] + 3;
				win[index2] = win[index2] + 1;
				sets[index2 * 2] = sets[index2 * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				sets[(index2 * 2) + 1] = sets[(index2 * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				}	
			if ((Integer.parseInt(result.get(players.get(j)).split(":")[0]) == 3) & (Integer.parseInt(result.get(players.get(j)).split(":")[1]) == 2)) {
				int index = (clubNames.indexOf(clubs2.get(players.get(j))));
				scores[index] = scores[index] + 1;	
				lose[index] = lose[index] + 1;
				sets[index * 2] = sets[index * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				sets[(index * 2) + 1] = sets[(index * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				int	index2 = (clubNames.indexOf(clubs.get(players.get(j))));
				scores[index2] = scores[index2] + 2;
				win[index2] = win[index2] + 1;
				sets[index2 * 2] = sets[index2 * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				sets[(index2 * 2) + 1] = sets[(index2 * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
			}		
			if ((Integer.parseInt(result.get(players.get(j)).split(":")[1]) == 3) & (Integer.parseInt(result.get(players.get(j)).split(":")[0]) == 0)) {
				int index = (clubNames.indexOf(clubs2.get(players.get(j))));
				scores[index] = scores[index] + 3;
				win[index] = win[index] + 1;
				sets[index * 2] = sets[index * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				sets[(index * 2) + 1] = sets[(index * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				int index2 = (clubNames.indexOf(clubs.get(players.get(j))));
				scores[index2] = scores[index2];
				lose[index2] = lose[index2] + 1;
				sets[index * 2] = sets[index * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				sets[(index2 * 2) + 1] = sets[(index2 * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				}
			if ((Integer.parseInt(result.get(players.get(j)).split(":")[1]) == 3) & (Integer.parseInt(result.get(players.get(j)).split(":")[0]) == 1)) {
				int index = (clubNames.indexOf(clubs.get(players.get(j))));
				scores[index] = scores[index];	
				lose[index] = lose[index] + 1;
				sets[index * 2] = sets[index * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				sets[(index * 2) + 1] = sets[(index * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				int	index2 = (clubNames.indexOf(clubs2.get(players.get(j))));
				scores[index2] = scores[index2] + 3;
				win[index2] = win[index2] + 1;
				sets[index2 * 2] = sets[index2 * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				sets[(index2 * 2) + 1] = sets[(index2 * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				}	
			if ((Integer.parseInt(result.get(players.get(j)).split(":")[1]) == 3) & (Integer.parseInt(result.get(players.get(j)).split(":")[0]) == 2)) {
				int index = (clubNames.indexOf(clubs.get(players.get(j))));
				scores[index] = scores[index] + 1;	
				lose[index] = lose[index] + 1;
				sets[index * 2] = sets[index * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
				sets[(index * 2) + 1] = sets[(index * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				int	index2 = (clubNames.indexOf(clubs2.get(players.get(j))));
				scores[index2] = scores[index2] + 2;
				win[index2] = win[index2] + 1;
				sets[index2 * 2] = sets[index2 * 2] + Integer.parseInt(result.get(players.get(j)).split(":")[1]);
				sets[(index2 * 2) + 1] = sets[(index2 * 2) + 1] + Integer.parseInt(result.get(players.get(j)).split(":")[0]);
			}		
		}
		for (int j=0; j<sets2.length; j++) {
			sets2[j] = String.valueOf(sets[2*j]) + ":" + String.valueOf(sets[(2*j) + 1]);
		}
		sortAndPrepareOutput();
	}
	
	// After that, we'll use bubble sort to every list.
	public static void sortAndPrepareOutput(){
		bubbleSort(clubNames, win, tie, lose, sets2, scores);
		writeFiles();
		for (int j=0; j<players.size(); j++) {
			
		}
	}
		
	// We referencing score list to sort every lists.
	public static void bubbleSort(List<String> clubNames, int[] win, int[] tie, int[] lose, String[] sets2, int[] scores){		
		for (int i = 0; i < scores.length; i++) {
			for (int j = 0; j < scores.length-1-i; j++) { 
				if(scores[j]>scores[j+1]){
					int temp = scores[j];
					scores[j]=scores[j+1];
					scores[j+1]=temp;
					int temp2 = tie[j];
					tie[j]=tie[j+1];
					tie[j+1]=temp2;
					int temp3 = lose[j];
					lose[j]=lose[j+1];
					lose[j+1]=temp3;
					String temp4 = sets2[j];
					sets2[j]=sets2[j+1];
					sets2[j+1]=temp4;
					int temp5 = win[j];
					win[j]=win[j+1];
					win[j+1]=temp5;
					String temp6 = clubNames.get(j);
					clubNames.set(j,clubNames.get(j+1));
					clubNames.set(j+1, temp6);	
				}
			
			if(scores[j]==scores[j+1]) {
				int x = Integer.parseInt(sets2[j].split(":")[0]) - Integer.parseInt(sets2[j].split(":")[1]);
				int y = Integer.parseInt(sets2[j+1].split(":")[0]) - Integer.parseInt(sets2[j+1].split(":")[1]);
				if (x - y > 0) {
					int temp = scores[j]; 
					scores[j]=scores[j+1];
					scores[j+1]=temp;     
					int temp2 = tie[j];
					tie[j]=tie[j+1];
					tie[j+1]=temp2;
					int temp3 = lose[j];
					lose[j]=lose[j+1];
					lose[j+1]=temp3;
					String temp4 = sets2[j];
					sets2[j]=sets2[j+1];
					sets2[j+1]=temp4;
					int temp5 = win[j];
					win[j]=win[j+1];
					win[j+1]=temp5;
					String temp6 = clubNames.get(j);
					clubNames.set(j,clubNames.get(j+1));
					clubNames.set(j+1, temp6);
				}
				if (x - y < 0) {
				}
				}	
			}
		}
	}
		
	// With all variables, we can write our output file.
	public static void writeFiles(){
		for (int i=0; i<clubNames.size(); i++) {
			printFile.add(Integer.toString(clubNames.size() -i) + ".\t" + clubNames.get(i) + "\t" + (win[i] + lose[i] + tie[i]) + "\t" + win[i] + "\t" + tie[i] + "\t"+ lose[i] + "\t" + sets2[i] + "\t" + scores[i] + "\n");
		}		
		FileOperations.writeOutput("volleyball", printFile);	
	}
}