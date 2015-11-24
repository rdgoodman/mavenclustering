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
	
	/**
	 * Removes points from this cluster
	 */
	public void clear(){
		pts.clear();
	}
	
	/**
	 * Adds a Datum (vector/point) to this cluster
	 */
	public void addPoint(Datum d){
		pts.add(d);
		// since the dependence goes both ways...
		d.assignToCluster(this);
	}
	
	/**
	 * Calculates the center/mean of the cluster
	 */
	public ArrayList<Double> calcMidpoint(){
		ArrayList<Double> means = new ArrayList<Double>();
		
		// find midpoint
		// for some algorithms, like PSO, this is the basically the centroid anyway
		for (int d = 0; d < centroid.size(); d++){
			double sum = 0;
			for (Datum t : pts){
				sum += t.getData().get(d);
			}
			means.add(sum/centroid.size());
		}
		
		return means;
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
	
	public void print(){
		System.out.println();
		System.out.println(">>>>> Cluster " + index);
		System.out.print("   Centroid: <");
		for (double i : centroid){
			System.out.print(i + " ");
		}
		System.out.print(">");
		System.out.println("   Elements: " + pts.size());
	}
}
