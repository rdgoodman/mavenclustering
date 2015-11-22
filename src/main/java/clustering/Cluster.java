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
	public Cluster(ArrayList<Double> centroid, int index){
		this.centroid = centroid;
		pts = new ArrayList<Datum>();
		this.index = index;
	}

	public ArrayList<Double> getCentroid() {
		return centroid;
	}

	public void setCentroid(ArrayList<Double> centroid) {
		this.centroid = centroid;
	}

	public ArrayList<Datum> getPts() {
		return pts;
	}

	public void setPts(ArrayList<Datum> pts) {
		this.pts = pts;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
