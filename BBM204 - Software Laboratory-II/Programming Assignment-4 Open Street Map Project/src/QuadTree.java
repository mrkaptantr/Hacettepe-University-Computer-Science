import java.util.*;


public class QuadTree {
    public QTreeNode root;
    private String imageRoot;
    int currDepth = 0;

    public QuadTree(String imageRoot) {
        // Instantiate the root element of the tree with depth 0
        // Use the ROOT_ULLAT, ROOT_ULLON, ROOT_LRLAT, ROOT_LRLON static variables of MapServer class
        // Call the build method with depth 1
        // Save the imageRoot value to the instance variable
    	/*this.root = new QTreeNode("root", MapServer.ROOT_ULLAT, MapServer.ROOT_ULLON, MapServer.ROOT_LRLAT, MapServer.ROOT_LRLON, 0);
    	currDepth = root.getDepth();
    	build (root, 2);
    	this.imageRoot = imageRoot;*/
    }

    public void build(QTreeNode subTreeRoot, int depth) {
        // Recursive method to build the tree as instructed
	    /*if (depth < currDepth + 8){
	    	if (depth == currDepth + 1) {
	            double midpointx = (subTreeRoot.getUpperLeftLongtitude() + subTreeRoot.getLowerRightLongtitude()) / 2;
	            double midpointy = (subTreeRoot.getUpperLeftLatitude() + subTreeRoot.getLowerRightLatitude()) / 2;
	           	subTreeRoot.NW = new QTreeNode("1", subTreeRoot.getUpperLeftLatitude(), subTreeRoot.getUpperLeftLongtitude(), midpointy, midpointx, depth + 1);	           	
	           	subTreeRoot.NE = new QTreeNode("2", subTreeRoot.getUpperLeftLatitude(), midpointx, midpointy,subTreeRoot.getLowerRightLongtitude(), depth + 1);
	            subTreeRoot.SW = new QTreeNode("3", midpointy, subTreeRoot.getUpperLeftLongtitude(), subTreeRoot.getLowerRightLatitude(), midpointx, depth + 1);
	            subTreeRoot.SE = new QTreeNode("4", midpointy, midpointx, subTreeRoot.getLowerRightLatitude(), subTreeRoot.getLowerRightLongtitude(), depth + 1);   
	            build(subTreeRoot.NW, depth+1);
	            build(subTreeRoot.NE, depth+1);
	            build(subTreeRoot.SW, depth+1);
	            build(subTreeRoot.SE, depth+1);
	    	} else {
	            double midpointx = (subTreeRoot.getUpperLeftLongtitude() + subTreeRoot.getLowerRightLongtitude()) / 2;
	            double midpointy = (subTreeRoot.getUpperLeftLatitude() + subTreeRoot.getLowerRightLatitude()) / 2;
	            String name = subTreeRoot.getName();
	            subTreeRoot.NW = new QTreeNode(name + "1", subTreeRoot.getUpperLeftLatitude(), subTreeRoot.getUpperLeftLongtitude(), midpointy, midpointx, depth + 1);
	            subTreeRoot.NE = new QTreeNode(name + "2", subTreeRoot.getUpperLeftLatitude(), midpointx, midpointy,subTreeRoot.getLowerRightLongtitude(), depth + 1);
	            subTreeRoot.SW = new QTreeNode(name + "3", midpointy, subTreeRoot.getUpperLeftLongtitude(), subTreeRoot.getLowerRightLatitude(), midpointx, depth + 1);
	            subTreeRoot.SE = new QTreeNode(name + "4", midpointy, midpointx, subTreeRoot.getLowerRightLatitude(), subTreeRoot.getLowerRightLongtitude(), depth + 1);
	            build(subTreeRoot.NW, depth+1);
	            build(subTreeRoot.NE, depth+1);
	            build(subTreeRoot.SW, depth+1);
	            build(subTreeRoot.SE, depth+1);
	    	}
	    }*/
    }

    public Map<String, Object> search(Map<String, Double> params) {
        /*
         * Parameters are:
         * "ullat": Upper left latitude of the query box
         * "ullon": Upper left longitude of the query box
         * "lrlat": Lower right latitude of the query box
         * "lrlon": Lower right longitude of the query box
         * */

        // Instantiate a QTreeNode to represent the query box defined by the parameters
        // Calculate the lonDpp value of the query box
        // Call the search() method with the query box and the lonDpp value
        // Call and return the result of the getMap() method to return the acquired nodes in an appropriate way

    	/*ArrayList<QTreeNode> reqList = new ArrayList<QTreeNode>();
    	QTreeNode querybox = new QTreeNode("QueryBox", params.get("ullat"), params.get("ullon"), params.get("lrlat"), params.get("lrlon"), 1);
    	double lonDpp = (querybox.getLowerRightLongtitude() - querybox.getUpperLeftLongtitude()) / (params.get("w"));
    	search(querybox, root, lonDpp, reqList);
    	Map<String, Object> map = new HashMap<String, Object>();    	
    	map = getMap(reqList);
        return map;*/
        return new HashMap<>();
    }

    private Map<String, Object> getMap(ArrayList<QTreeNode> list) {
        Map<String, Object> map = new HashMap<>();
        
        // Check if the root intersects with the given query box
        /*if (list.size() == 0) {
            map.put("query_success", false);
            return map;
        }*/

        // Use the get2D() method to get organized images in a 2D array
        //map.put("render_grid", get2D(list));

        // Upper left latitude of the retrieved grid (Imagine the
        // 2D string array you have constructed as a big picture)
        //map.put("raster_ul_lat", list.get(0).getUpperLeftLatitude());

        // Upper left longitude of the retrieved grid (Imagine the
        // 2D string array you have constructed as a big picture)
        //map.put("raster_ul_lon", list.get(0).getUpperLeftLongtitude());

        // Upper lower right latitude of the retrieved grid (Imagine the
        // 2D string array you have constructed as a big picture)
        //map.put("raster_lr_lat", list.get(list.size()-1).getLowerRightLatitude());

        // Upper lower right longitude of the retrieved grid (Imagine the
        // 2D string array you have constructed as a big picture)
        //map.put("raster_lr_lon", list.get(list.size()-1).getLowerRightLongtitude());

        // Depth of the grid (can be thought as the depth of a single image)
        //map.put("depth", list.get(0).getDepth());

        //map.put("query_success", true);
        return map;
    }

    private String[][] get2D(ArrayList<QTreeNode> list) {
        String[][] images = new String[0][0];
        // After you retrieve the list of images using the recursive search mechanism described above, you
        // should order them as a grid. This grid is nothing more than a 2D array of file names. To order
        // the images, you should determine correct row and column for each image (node) in the retrieved
        // list. As a hint, you should consider the latitude values of images to place them in the row, and
        // the file names of the images to place them in a column.

        /*ArrayList<Double> keys = new ArrayList<Double>();
        for (QTreeNode x : list) {
            if (!keys.contains(x.getUpperLeftLatitude())) {
                keys.add(x.getUpperLeftLatitude());
            }
        }
        Collections.sort(keys);
        
        Map<Double,ArrayList<String>> map = new LinkedHashMap<>();
        for (QTreeNode x : list) {
            if(map.containsKey(x.getUpperLeftLatitude())) {
               map.get(x.getUpperLeftLatitude()).add(x.getName());
            } else {
                ArrayList<String> l = new ArrayList<>();
                l.add(x.getName());
                map.put(x.getUpperLeftLatitude(),l);
            }
        }

        String[][] images = new String[keys.size()][];
        int i = 0;
        for (double y : map.keySet()) {
            images[i] = new String[(map.get(y)).size()];
            for (int j = 0; j < map.get(y).size(); j++) {
                images[i][j] = imageRoot + map.get(y).get(j) + ".png";
            }
            i += 1;
        } */
        return images;
    }

    public void search(QTreeNode queryBox, QTreeNode tile, double lonDpp, ArrayList<QTreeNode> list) {
        // The first part includes a recursive search in the tree. This process should consider both the
        // lonDPP property (discussed above) and if the images in the tree intersect with the query box.
        // (To check the intersection of two tiles, you should use the checkIntersection() method)
        // To achieve this, you should retrieve the first depth (zoom level) of the images which intersect
        // with the query box and have a lower lonDPP than the query box.
        // This method should fill the list given by the "ArrayList<QTreeNode> list" parameter

        /*Queue<QTreeNode> overlapps = new ArrayDeque<>();
        overlapps.add(tile);
        while(!overlapps.isEmpty()) {
            tile = overlapps.remove();
            if (checkIntersection(tile,queryBox)) {
            	if (tile.getLonDPP() <= lonDpp) {
                    list.add(tile);
                } else {
                    overlapps.add(tile.NW);
                    overlapps.add(tile.NE);
                    overlapps.add(tile.SW);
                    overlapps.add(tile.SE);
                }
            }
        }*/
    }

    public boolean checkIntersection(QTreeNode tile, QTreeNode queryBox) {
        // Return true if two tiles are intersecting with each other
        /*if (tile.getLowerRightLongtitude() <= queryBox.getUpperLeftLongtitude() || queryBox.getLowerRightLongtitude() <= tile.getUpperLeftLongtitude()
                || tile.getLowerRightLatitude() >= queryBox.getUpperLeftLatitude() || tile.getUpperLeftLatitude() <= queryBox.getLowerRightLatitude()) {
            return false;
        }*/
        return true;
    }
}