//this code use to find distance between two edges (in meters)
//the code was derived from the following link
// https://github.com/jasonwinn/haversine/blob/master/Haversine.java

// Tamuda Chimhanda (tchimhan@u.rochester.edu)
// Ameenul Haque (ahaque@u.rochester.edu)

public class Edge {
    String id;
    double weight;
    private static final int RADIUS_EARTH = 3959; // Approx Earth radius in MILEs
    // constructor
    public Edge(String id, Node o, Node d) {

        this.weight = distance(o.lattitude, o.longitude, d.lattitude, d.longitude);
        this.id = id;
    }


    private static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
    private static double distance(double sLattitude, double sLongitude, double eLattitude, double eLongitude) {

        double disLat = Math.toRadians((eLattitude - sLattitude));
        double disLong = Math.toRadians((eLongitude - sLongitude));

        sLattitude = Math.toRadians(sLattitude);
        eLattitude = Math.toRadians(eLattitude);

        double a = haversin(disLat) + Math.cos(sLattitude) * Math.cos(eLattitude) * haversin(disLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIUS_EARTH * c; 	// returns distance in miles
    }

}