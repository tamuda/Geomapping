import java.util.HashMap;
/**This class represents an Intersection/ a Node**/

// Tamuda Chimhanda (tchimhan@u.rochester.edu)
// Ameenul Haque (ahaque@u.rochester.edu)


public class Node implements Comparable<Node> {
    double distToSource;
    String id;
    double lattitude;
    double longitude;
    boolean visited=false;
    Node updater;//acts as parent of Node during Dijsktras Algorithm
    HashMap<Node, Edge> adjlist;// stores the edges that this node is connected to (undirected graph)
    // constructor
    public Node(String id, double lat, double lon) {
        this.id = id;
        this.lattitude = lat;
        this.longitude = lon;
        adjlist = new HashMap<Node, Edge>();
    }
    /** This method will allow priority queueu to know to order Nodes according to their distance from source**/
    @Override
    public int compareTo(Node arg) {
        if(this.distToSource<arg.distToSource) {
            return -1;
        }
        else if(this.distToSource>arg.distToSource) {
            return 1;
        }
        return 0;
    }
    @Override
    public String toString() {
        return "Node [ id=" + id + "]";
    }
}
