import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Router {

    private static List<Vertex> stops = new ArrayList<>();
    private static Vertex start, end;

    public static LinkedList<Long> shortestPath(GraphDB g, double stlon, double stlat, double destlon, double destlat) {
        // Return the shortest path between start and end points
        // Use g.closest() to get start and end vertices
        // Return ids of vertices as a linked list
        /* Code here */
        return new LinkedList<>();
    }

    public static LinkedList<Long> addStop(GraphDB g, double lat, double lon) {
        // Find the closest vertex to the stop coordinates using g.closest()
        // Add the stop to the stop list
        // Recalculate your route when a stop is added and return the new route
        /* Code here */
        return new LinkedList<>();
    }

    public static void clearRoute() {
        start = null;
        end = null;
        stops = new ArrayList<>();
    }
}