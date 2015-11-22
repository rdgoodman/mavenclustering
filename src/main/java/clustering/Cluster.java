package clustering;

import java.util.ArrayList;

public class Cluster {

	ArrayList<Double> centroid;
	ArrayList<Datum> pts;
	// TODO: index should be unique
	int index;
	
	/**
	 * Represents a cluster by storing the centroid's location and the points in this cluster
	 */
	public Cluster(ArrayList<Double> centroid){
		this.centroid = centroid;
		pts = new ArrayList<Datum>();
	}
}
