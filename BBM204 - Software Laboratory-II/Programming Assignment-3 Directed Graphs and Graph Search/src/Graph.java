import java.util.*;
import java.util.Map.Entry;
  
class Graph<Integer> {
  
    // We use Hashmap to store the edges in the graph
    public Map<Integer, List<Integer> > map = new HashMap<>();
	public ArrayList<ArrayList<Integer>> alist = new ArrayList<ArrayList<Integer>>();

    // This function adds a new vertex to the graph
    public void addVertex(Integer s) {
        map.put(s, new LinkedList<Integer>());
    }
  
    // This function adds the edge between source to destination
    public void addEdge(Integer source, Integer destination, boolean bidirectional) {
        if (!map.containsKey(source))
            addVertex(source);
  
        if (!map.containsKey(destination))
            addVertex(destination);
  
        map.get(source).add(destination);
        if (bidirectional == true) {
            map.get(destination).add(source);
        }
    }
  
    // This function gives whether an edge is present or not.
    public boolean hasEdge(Integer s, Integer d) {
        if (map.get(s).contains(d)) {
            return true;
        }
        else {
        	return false;
        }
    }
    
    // This method builds and returns an output file which includes values
    public LinkedList<LinkedList<Integer>> build() {
    	LinkedList<LinkedList<Integer>> build = new LinkedList<LinkedList<Integer>>();
	    for (Integer i : map.keySet()) {
	    	build.add((LinkedList<Integer>) map.get(i));
	    }
    	return build;
    }
    
    // Graph possible paths search algorithm
    public void printAllPathsUtil(Integer u, Integer d, boolean[] isVisited, ArrayList<Integer> localPathList) {
        /*if (u.equals(d)) {
        	ArrayList<Integer> copy = new ArrayList<>();
        	copy.addAll(localPathList);
        	this.alist.add(copy);
        	return;
        }
        isVisited[Integer.parseInt(u)] = true;
	    for (Integer i : map.keySet()) {   	
	    	if (!isVisited[Integer.parseInt(i)]) {	    		
	            localPathList.add(i);
	            printAllPathsUtil(i, d, isVisited, localPathList);
	            localPathList.remove(i);
	        }
	    }
	    isVisited[Integer.parseInt(u)] = false;*/
	}   
    
    static int z;
    static int maxCount;
    
	// The function to do DFS traversal. It uses recursive dfsUtil()
    void dfs(int node, int n, LinkedList<LinkedList<Integer>> adj) {	
        //boolean[] visited = new boolean[n + 1];
        //int count = 0;
        //Arrays.fill(visited, false);
        //dfsUtil(node, count + 1, visited, adj);    
    }
    
    // Sets maxCount as maximum distance from node
    void dfsUtil(int node, int count, boolean visited[], LinkedList<LinkedList<Integer>> adj) {
        /*visited[node] = true;
        count++;
        LinkedList<int> l = adj.get(node);
        for(int i: l){
            if(!visited[i]){
                if (count >= maxCount) {
                	diameter.add(i);
                    maxCount = count;
                    z = i;
                }
                dfsUtil(i, count, visited, adj);
            }
        }*/
    }

    // Diameter calculator function but not completed
	LinkedList<Integer> diameter = new LinkedList<Integer>();
    public LinkedList<Integer> getDiameter() {
    	/*LinkedList<LinkedList<Integer>> mainList = new LinkedList<LinkedList<Integer>>();
		mainList = this.build();	
		dfs(1, this.map.size(), mainList);
        dfs(z, this.map.size(), mainList);
    	return diameter;*/
	LinkedList<Integer> mainList = new LinkedList<Integer>();
	//mainList.add(0);
	return mainList;
    }
    
    // Prints the adjancency list of each vertex. (Required for debugging)
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Integer v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (Integer w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }
        return (builder.toString());
    }
}
  