import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AirportSystem {
	private List<Airport> airports;
	private List<Flight> flights;
	private List<String> commands;
	public List<String> outputFile;
	public LinkedList<LinkedList<Integer>> graphcopy;
	private Graph graph;
	private List<ArrayList<Integer>> calculated = new ArrayList<ArrayList<Integer>>();
	public List<String> output = new ArrayList<String>();

	// Simple constructor
	public AirportSystem(List<Airport> airports, List<Flight> flights, List<String> commands) {
		this.airports = airports;
		this.flights = flights;
		this.commands = commands;
	}
	
	// Finds the index of airport with using input, input can be cityname or alias (Required for other methods)
	public int findIndexOfAirport(String inp) {
	    for (int i=0; i<airports.size(); i++) {
	    	if (airports.get(i).cityName.compareTo(inp) == 0) {
	    		return i;
	    	} else if (airports.get(i).alias.compareTo(inp) == 0) {
	    		return i;
	    	}
	    }
		return 0;
	}
	
	// Simple graph generator method
	public void generateGraph() {
	    graph = new Graph<Integer>();
	    for (int j=0; j<airports.size(); j++) {
	    	graph.addVertex(j);
	    }
	    for (int j=0; j<airports.size(); j++) {
	    	graph.addEdge(findIndexOfAirport(flights.get(j).getdept()), findIndexOfAirport(flights.get(j).getarr()), false);
	    }
	    graphcopy = graph.build();
	}
	
	// Detecting required methods and calling them
	public void executeCommands() {		
		for (int i=0; i<commands.size(); i++) {
			try {
				if ((commands.get(i).substring(0,7).compareTo("listAll") == 0)) {
					listAll(commands.get(i).split("\t")[1].split("->")[0], commands.get(i).split("\t")[1].split("->")[1], commands.get(i).split("\t")[2], true);
				}
				if ((commands.get(i).substring(0,10).compareTo("listProper") == 0)) {
					listProper(commands.get(i).split("\t")[1].split("->")[0], commands.get(i).split("\t")[1].split("->")[1], commands.get(i).split("\t")[2], true);
				}
				if ((commands.get(i).substring(0,12).compareTo("listCheapest") == 0)) {
					listCheapest(commands.get(i).split("\t")[1].split("->")[0], commands.get(i).split("\t")[1].split("->")[1], commands.get(i).split("\t")[2], true);
				}
				if ((commands.get(i).substring(0,12).compareTo("listQuickest") == 0)) {
					listQuickest(commands.get(i).split("\t")[1].split("->")[0], commands.get(i).split("\t")[1].split("->")[1], commands.get(i).split("\t")[2], true, true);
				}
				if ((commands.get(i).substring(0,11).compareTo("listCheaper") == 0)) {
					listCheaper(commands.get(i).split("\t")[1].split("->")[0], commands.get(i).split("\t")[1].split("->")[1], commands.get(i).split("\t")[2], commands.get(i).split("\t")[3]);
				}
				if ((commands.get(i).substring(0,11).compareTo("listQuicker") == 0)) {
					listQuicker(commands.get(i).split("\t")[1].split("->")[0], commands.get(i).split("\t")[1].split("->")[1], commands.get(i).split("\t")[2], commands.get(i).split("\t")[3]);
				}
				if ((commands.get(i).substring(0,13).compareTo("listExcluding") == 0)) {
					listExcluding(commands.get(i).split("\t")[1].split("->")[0], commands.get(i).split("\t")[1].split("->")[1], commands.get(i).split("\t")[2], commands.get(i).split("\t")[3]);
				}
				if ((commands.get(i).substring(0,12).compareTo("listOnlyFrom") == 0)) {
					listOnlyFrom(commands.get(i).split("\t")[1].split("->")[0], commands.get(i).split("\t")[1].split("->")[1], commands.get(i).split("\t")[2], commands.get(i).split("\t")[3]);
				}
				if ((commands.get(i).substring(0,15).compareTo("diameterOfGraph") == 0)) {
					diameterOfGraph();
				}
				if ((commands.get(i).substring(0,15).compareTo("pageRankOfNodes") == 0)) {
					pageRankOfNodes();
				}
			} catch (Exception ex) { } 
		}
		FileOperations.writeOutputFile("output.txt", output);
	}
	
	// Adds input times and returns another time value (Required for other methods)
	public String calculateTime (String x, String y) {
		int hour = Integer.parseInt(x.substring(0,2)) + Integer.parseInt(y.substring(0,2));
		int min = Integer.parseInt(x.substring(3,5)) + Integer.parseInt(y.substring(3,5));

		String left = ""; String right = "";
		
		int increment = min / 60;
		if (increment > 0) { hour += 1; min -= 60; }
		left = String.valueOf(hour); right = String.valueOf(min);
		if (hour > 24) { hour -= 24; }
		if (hour == 24) { left = "00"; } 
		if (hour < 10) { left = "0" + String.valueOf(hour); }
		if (min < 10) { right = "0" + String.valueOf(min); }

		return left + ":" + right;
	}
	
	// Finds the flight id with using dest and arr (Required for other methods)
	public Flight findFlightID(String dest, String arr) {
		for (int i=0; i<flights.size(); i++) {
			if (flights.get(i).getdept().compareTo(dest) == 0) {
				if (flights.get(i).getarr().compareTo(arr) == 0) {
					return flights.get(i);
				}
			}
		}
		return null;
	}
	
	ArrayList<ArrayList<Integer>> listall = new ArrayList<ArrayList<Integer>>();
	int totalprice = 0;
	String totaltime = "00:00";
	
	// ListAll method list all possible flight routes from dept to arr with using graph search algorithms
	public void listAll(String dept, String arr, String startDate, boolean printallowed) {
		calculated.clear();		
		if (printallowed) { 
			try {
			output.add("command : listAll\t" + dept + "->" + arr + "\t" + startDate);
			} catch (Exception ex) { }
		}
		boolean[] isVisited = new boolean[graph.map.size()];
        ArrayList<Integer> pathList = new ArrayList<>();
        pathList.add(findIndexOfAirport(dept));
        graph.printAllPathsUtil(findIndexOfAirport(dept), findIndexOfAirport(arr), isVisited, pathList);
        for(int i=graph.alist.size()-1; -1<i; i--) {            
            ArrayList<Integer> pathList2 = (ArrayList<Integer>) graph.alist.get(i);
			boolean checker = true;
			ArrayList<String> cities = new ArrayList<String>();
			for (int j=0; j<pathList2.size()-1; j++) {
				// Same city airports check
				if (pathList2.size() > 1) {
					if (airports.get(pathList2.get(j)).cityName.compareTo(airports.get(pathList2.get(j+1)).cityName) == 0) {
						checker = false;
					}
				}
				// Same city more then once check 
				cities.add(airports.get(pathList2.get(j)).cityName);
				for (int c=0; c<cities.size(); c++) {
					if (cities.size() > 1) {
						if (cities.get(c).compareTo(airports.get(pathList2.get(j)).cityName) == 0) {
							checker = false;							
						}
					}
				}				
				// Time check
				for (int m=0; m<pathList2.size()-1; m++) {
					if (pathList2.size() > 2) {
						try {
							Flight fl1 = findFlightID(airports.get(pathList2.get(m)).alias, airports.get(pathList2.get(m+1)).alias);
							Flight fl2 = findFlightID(airports.get(pathList2.get(m+1)).alias, airports.get(pathList2.get(m+2)).alias);
							Date start = fl1.getarrDate();
							Date end = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fl2.getdeptDate());			
							if ((end.compareTo(start) < 0)) { checker = false; }
						} catch (Exception e) {}
					}
				}
				// Logical check
				for (int m=0; m<pathList2.size()-1; m++) {	
					if (pathList2.size() > 1) {
						if (!(graph.hasEdge(pathList2.get(m), pathList2.get(m+1)))) {
							checker = false;
						}	
					}
				}
				if (checker) {
					if (pathList2.size() > 0) {
						ArrayList<Integer> copy = new ArrayList<Integer>();
						if (!(calculated.contains(pathList2))) { calculated.add(pathList2); }
			        	copy.addAll(pathList2);
			        	listall.add(copy);
			        	totalprice = 0;
			        	totaltime = "00:00";
			        	String outputstr = "";
						for (int p=0; p<pathList2.size()-2; p++) {
							if (pathList2.size() > 2) {
								Flight x = findFlightID(airports.get(pathList2.get(p)).alias, airports.get(pathList2.get(p+1)).alias);
								totalprice += Integer.parseInt(x.getprice());
								totaltime = calculateTime(totaltime,x.getduration());
					    		if (printallowed) { 
					    			try {
					    				outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "||"); 
					    			} catch (Exception ex) { }
					    		}
							}
						}
						Flight x = findFlightID(airports.get(pathList2.get(pathList2.size()-2)).alias, airports.get(pathList2.get(pathList2.size()-1)).alias);
						totalprice += Integer.parseInt(x.getprice());
						totaltime = calculateTime(totaltime,x.getduration());
						
						if (printallowed) { 
							try {
								outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "\t" + totaltime + "/" + totalprice); 
								output.add(outputstr);
							} catch (Exception ex) { } 
						}
					} else {
						output.add("No suitable flight plan is found");
						output.add("\n");
					}
				}
			}
		}
		output.add("\n");
	}
	
	int cheapest = 0;
	int quickest = 0;
	public void listProper(String dept, String arr, String startDate, boolean checker) {
		if (checker) { 
			try {
				output.add("command : listProper\t" + dept + "->" + arr + "\t" + startDate);
			} catch (Exception ex) { }
		}
		listCheapest(dept, arr, startDate, false);
		listQuickest(dept, arr, startDate, false, false);
    	String outputstr = "";
		if (quickest == cheapest) {
			int i = quickest;
			for (int p=0; p<calculated.size()-2; p++) {
				if (calculated.size() > 2) {
					Flight x = findFlightID(airports.get(calculated.get(i).get(p)).alias, airports.get(calculated.get(i).get(p+1)).alias);
					totaltime = calculateTime(totaltime,x.getduration());
					totalprice += Integer.parseInt(x.getprice());
					try {
		    			outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "||"); 
					} catch (Exception ex) { }
				}
			}
			Flight x = findFlightID(airports.get(calculated.get(i).get(calculated.size()-2)).alias, airports.get(calculated.get(i).get(calculated.size()-1)).alias);
			totaltime = calculateTime(totaltime,x.getduration());
			totalprice += Integer.parseInt(x.getprice());
			if (checker) { 
				try {
					outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "\t" + totaltime + "/" + totalprice); 
					output.add(outputstr);
					output.add("\n");
				} catch (Exception ex) { }
			}
		} else {
    		if (checker) { output.add("No suitable flight plan is found"); output.add("\n"); }
		}		
	}
	
	// ListCheapest method prints cheapest flight
	public void listCheapest(String dept, String arr, String startDate, boolean checkerx) {
		if (checkerx) { 
			try {
				output.add("command : listCheapest\t" + dept + "->" + arr + "\t" + startDate); 
			} catch (Exception ex) { }		
		}
		listAll(dept, arr, startDate, false);
		List<Integer> prices = new ArrayList<Integer>();
    	totalprice = 0;
		for (int i=0; i<calculated.size(); i++) {
			for (int p=0; p<calculated.size()-2; p++) {
				if (calculated.size() > 2) {
					Flight x = findFlightID(airports.get(calculated.get(i).get(p)).alias, airports.get(calculated.get(i).get(p+1)).alias);
					totalprice += Integer.parseInt(x.getprice());
				}
			}
			Flight x = findFlightID(airports.get(calculated.get(i).get(calculated.size()-2)).alias, airports.get(calculated.get(i).get(calculated.size()-1)).alias);
			totalprice += Integer.parseInt(x.getprice());
			prices.add(totalprice);
		}
    	totaltime = "00:00";
    	totalprice = 0;
    	String outputstr = "";
		int i = prices.indexOf(Collections.min(prices));
		cheapest = i;
		for (int p=0; p<calculated.size()-2; p++) {
			if (calculated.size() > 2) {
				Flight x = findFlightID(airports.get(calculated.get(i).get(p)).alias, airports.get(calculated.get(i).get(p+1)).alias);
				totaltime = calculateTime(totaltime,x.getduration());
				totalprice += Integer.parseInt(x.getprice());
	    		if (checkerx) { 
	    			try {
	    				outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "||"); 
	    			} catch (Exception ex) { }
	    		}
			}
		}
		Flight x = findFlightID(airports.get(calculated.get(i).get(calculated.size()-2)).alias, airports.get(calculated.get(i).get(calculated.size()-1)).alias);
		totaltime = calculateTime(totaltime,x.getduration());
		totalprice += Integer.parseInt(x.getprice());
		if (checkerx) { 
			try {
				outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "\t" + totaltime + "/" + totalprice);
				output.add(outputstr);
				output.add("\n");
			} catch (Exception ex) { }
		}
	}
	
	// ListQuickest method prints quickest flight
	public void listQuickest(String dept, String arr, String startDate, boolean checker, boolean checker2) {
		if (checker) { 
			if (checker2) {
			try {
				output.add("command : listQuickest\t" + dept + "->" + arr + "\t" + startDate); 
			} catch (Exception ex) { }
			}
		}
		listAll(dept, arr, startDate, false);
		List<String> times = new ArrayList<String>();
    	totaltime = "00:00";
		for (int i=0; i<calculated.size(); i++) {
			for (int p=0; p<calculated.size()-2; p++) {
				if (calculated.size() > 2) {
					Flight x = findFlightID(airports.get(calculated.get(i).get(p)).alias, airports.get(calculated.get(i).get(p+1)).alias);
					totaltime = calculateTime(totaltime,x.getduration());
				}
			}
			Flight x = findFlightID(airports.get(calculated.get(i).get(calculated.size()-2)).alias, airports.get(calculated.get(i).get(calculated.size()-1)).alias);
			totaltime = calculateTime(totaltime,x.getduration());
			times.add(totaltime);
		}
    	totaltime = "00:00";
    	totalprice = 0;
    	String outputstr = "";
		int i = times.indexOf(Collections.min(times));
		quickest = i;
		for (int p=0; p<calculated.size()-2; p++) {
			if (calculated.size() > 2) {
				Flight x = findFlightID(airports.get(calculated.get(i).get(p)).alias, airports.get(calculated.get(i).get(p+1)).alias);
				totaltime = calculateTime(totaltime,x.getduration());
				totalprice += Integer.parseInt(x.getprice());
				if (checker) { 
					try {
						outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "||"); 
					} catch (Exception ex) { }
				}
			}
		}
		Flight x = findFlightID(airports.get(calculated.get(i).get(calculated.size()-2)).alias, airports.get(calculated.get(i).get(calculated.size()-1)).alias);
		totaltime = calculateTime(totaltime,x.getduration());
		totalprice += Integer.parseInt(x.getprice());
		if (checker) { 
			try {
				outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "\t" + totaltime + "/" + totalprice);
				output.add(outputstr);
				output.add("\n");
			} catch (Exception ex) { }
		}
	}
	
	// ListChaper method prints quickest method if it is compatitible with maxprice
	public void listCheaper(String dept, String arr, String startDate, String maxPrice) {
		try {
			output.add("command : listCheaper\t" + dept + "->" + arr + "\t" + startDate + "\t" + maxPrice);
		} catch (Exception ex) { }
		listAll(dept, arr, startDate, false);
		List<Integer> prices = new ArrayList<Integer>();
    	totalprice = 0;
		for (int i=0; i<calculated.size(); i++) {
			for (int p=0; p<calculated.size()-2; p++) {
				if (calculated.size() > 2) {
					Flight x = findFlightID(airports.get(calculated.get(i).get(p)).alias, airports.get(calculated.get(i).get(p+1)).alias);
					totalprice += Integer.parseInt(x.getprice());
				}
			}
			Flight x = findFlightID(airports.get(calculated.get(i).get(calculated.size()-2)).alias, airports.get(calculated.get(i).get(calculated.size()-1)).alias);
			totalprice += Integer.parseInt(x.getprice());
			prices.add(totalprice);
		}
    	totaltime = "00:00";
    	totalprice = 0;
    	
    	List<Integer> cheapers = new ArrayList<Integer>();
    	for (int q=0; q<prices.size(); q++) {
    		if (prices.get(q) < Integer.parseInt(maxPrice)) {
    			cheapers.add(q);
    		}
    	}
    	if (cheapers.size() == 0) {
    		output.add("No suitable flight plan is found"); output.add("\n");
    	} else {
    		String outputstr = "";
	    	for (int j=0; j<cheapers.size(); j++) {
	    		for (int p=0; p<calculated.get(cheapers.get(j)).size()-2; p++) {
	    			if (calculated.get(cheapers.get(j)).size() > 2) {
	    				Flight x = findFlightID(airports.get(calculated.get(cheapers.get(j)).get(p)).alias, airports.get(calculated.get(cheapers.get(j)).get(p+1)).alias);
	    				totaltime = calculateTime(totaltime,x.getduration());
	    				totalprice += Integer.parseInt(x.getprice());
	    				try {
	    					outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "||");
	    				} catch (Exception ex) { }
	    			}	
	    		}
	    		Flight x = findFlightID(airports.get(calculated.get(cheapers.get(j)).get(calculated.size()-2)).alias, airports.get(calculated.get(cheapers.get(j)).get(calculated.size()-1)).alias);
	    		totaltime = calculateTime(totaltime,x.getduration());
	    		totalprice += Integer.parseInt(x.getprice());
	    		try {
	    			outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "\t" + totaltime + "/" + totalprice);
	    			output.add(outputstr);
					output.add("\n");
	    		} catch (Exception ex) { }
	    	}
    	}
	}
	
	// ListQuicker method prints quickest method if it is compatitible with latestdatetime
	public void listQuicker(String dept, String arr, String startDate, String latestDateTime) {
		try {
			output.add("command : listQuicker\t" + dept + "->" + arr + "\t" + startDate + "\t" + latestDateTime);
		} catch (Exception ex) { }
		listAll(dept, arr, startDate, false);
		listProper(dept, arr, startDate, false);
		int quicker = -1;
		for (int i=0; i<calculated.size(); i++) {
        	if (calculated.get(i).size() > 1) {
    		Flight x = findFlightID(airports.get(calculated.get(i).get(calculated.get(i).size()-2)).alias, airports.get(calculated.get(i).get(calculated.get(i).size()-1)).alias);	
    			try {
					Date end = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(latestDateTime);
					Date start = x.getarrDate();						
    				if (!(start.compareTo(end) > 0)) {
    					quicker = i;
    				}
				} catch (ParseException e) {
				}
        	}
    	}
    	if (quickest == quicker) {
			listQuickest(dept, arr, startDate, true, false);
    	} else {
			output.add("No suitable flight plan is found");
    	}	
	}
	
	// ListExcluding Method Lists Flight Only From Excluding Given Input's Company
	public void listExcluding(String dept, String arr, String startDate, String company) {
		try {
			output.add("command : listExcluding\t" + dept + "->" + arr + "\t" + startDate + "\t" + company);
		} catch (Exception ex) { }
		listAll(dept, arr, startDate, false);
		List<ArrayList<Integer>> flights2 = new ArrayList<ArrayList<Integer>>(calculated);
    	totalprice = 0;
		for (int i=0; i<calculated.size(); i++) {
			for (int p=0; p<calculated.get(i).size()-1; p++) {
				if (calculated.get(i).size() > 1) {
					Flight x = findFlightID(airports.get(calculated.get(i).get(p)).alias, airports.get(calculated.get(i).get(p+1)).alias);
					if (x.getflightID().substring(0,2).compareTo(company) == 0) {
						flights2.remove(i);
						break;
					}	
				}
			}
		}
		if (flights2.size() == 0) {
			output.add("No suitable flight plan is found");
			output.add("\n");
		} else {
			String outputstr = "";
			for (int p=0; p<flights2.size(); p++) {
				for (int j=0; j<flights2.get(p).size()-1; j++) {
			    	totaltime = "00:00";
			    	totalprice = 0;
					if (flights2.size() > 1) {
						Flight x = findFlightID(airports.get(flights2.get(p).get(j)).alias, airports.get(flights2.get(p+1).get(j)).alias);
						totalprice += Integer.parseInt(x.getprice());
						totaltime = calculateTime(totaltime,x.getduration());
						try {
							outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "||");
						} catch (Exception ex) { }
					}
				}
				Flight x = findFlightID(airports.get(calculated.get(p).get(calculated.size()-2)).alias, airports.get(calculated.get(p).get(calculated.size()-1)).alias);
				totalprice += Integer.parseInt(x.getprice());
				totaltime = calculateTime(totaltime,x.getduration());
				try {
					outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "\t" + totaltime + "/" + totalprice);
					output.add(outputstr);
					output.add("\n");
				} catch (Exception ex) { }
			}
		}
	}
	
	// ListOnlyFrom Method Lists Flight Only From Given Input's Company
	public void listOnlyFrom(String dept, String arr, String startDate, String company) {
		try {
			output.add("command : listOnlyFrom\t" + dept + "->" + arr + "\t" + startDate + "\t" + company);
		} catch (Exception ex) { }
		listAll(dept, arr, startDate, false);
		List<ArrayList<Integer>> flights3 = new ArrayList<ArrayList<Integer>>();
		for (int i=0; i<calculated.size(); i++) {
			boolean checkx = true;
			for (int p=0; p<calculated.get(i).size()-1; p++) {
				if (calculated.get(i).size() > 1) {
					Flight x = findFlightID(airports.get(calculated.get(i).get(p)).alias, airports.get(calculated.get(i).get(p+1)).alias);
					if ((x.getflightID().substring(0,2).compareTo(company) == 0)) {
						checkx = false;
					}
				}
			}
			if (!checkx) {
				flights3.add(calculated.get(i));
			}
		}
		if (flights3.size() == 0) {
			output.add("No suitable flight plan is found");
			output.add("\n");
		} else {
			String outputstr = "";
			for (int p=0; p<flights3.size(); p++) {
				totaltime = "00:00";
		    	totalprice = 0;
				for (int j=0; j<flights3.get(p).size()-1; j++) {
					if (flights3.get(p).size() > 1) {
						Flight x = findFlightID(airports.get(flights3.get(p).get(j)).alias, airports.get(flights3.get(p).get(j+1)).alias);
						totalprice += Integer.parseInt(x.getprice());
						totaltime = calculateTime(totaltime,x.getduration());
						try {
							outputstr +=  (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "||");
						} catch (Exception ex) { }
					}
				}
				try {
					Flight x = findFlightID(airports.get(flights3.get(p).size()-2).alias, airports.get(flights3.get(p).size()-1).alias);				
					totalprice += Integer.parseInt(x.getprice());
					totaltime = calculateTime(totaltime,x.getduration());
					outputstr += (x.getflightID() + "\t" +  x.getdept() + "->" + x.getarr() + "\t" + totaltime + "/" + totalprice + "\n");
				} catch (Exception ex) { } 
				}
				try {
					output.add(outputstr);
					output.add("\n");
				} catch (Exception ex) { }
		}
	}
	
	// Diameter Calculator Calculates The Diameter (Longest Path) Of The Graph (Not Completed)
	public void diameterOfGraph() {
		try {
			output.add("command : diameterOfGraph");
			List<Integer> diameter = new ArrayList<Integer>();
			diameter = graph.getDiameter();
			double sum = 0;
			for(int d : diameter)
			    sum += (double) d;
	        output.add("The diameter of graph : " + sum);
			output.add("\n");
		} catch (Exception ex) { }
	}
	
	// PageRank Calculator Calculates The PageRank of Each Node (Not Completed)
	public void pageRankOfNodes() {
		output.add("command : pageRankOfNodes");
		for (int i=0; i<airports.size(); i++) {
			try {
				output.add(airports.get(i).alias + " : ");
			} catch (Exception ex) { } 
		}
	}
}
