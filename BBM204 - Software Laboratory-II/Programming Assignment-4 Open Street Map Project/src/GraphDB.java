import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class GraphDB {


    public Graph graph = new Graph();
    public TST<Vertex> tst = new TST<>();

    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputFile, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    static String normalizeString(String s) {
        // Should match all strings that are not alphabetical
        String regex = "[^a-zA-Z]"/* Replace *//* Code here */;
        return s.replaceAll(regex, "").toLowerCase();
    }

    private void clean() {
        // Remove the vertices with no incoming and outgoing connections from your graph
        /* Code here */
    }

    public double distance(Vertex v1, Vertex v2) {
        // Return the euclidean distance between two vertices
        /* Code here */
        return 0;
    }


    public long closest(double lon, double lat) {
        // Returns the closest vertex to the given latitude and longitude values
        /* Code here */
        return -1;
    }

    double lon(long v) {
        // Returns the longitude of the given vertex, v is the vertex id
        /* Code here */
        return 0;
    }


    double lat(long v) {
        // Returns the latitude of the given vertex, v is the vertex id
        /* Code here */
        return 0;
    }
}
