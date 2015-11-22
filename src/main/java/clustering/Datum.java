package clustering;

import java.util.ArrayList;

public class Datum {
	
	
	private ArrayList<Double> inputs;
	private Cluster cluster;
	private int cIndex;
	
	/**
	 * Stores an input vector and the cluster it belongs to
	 */
	public Datum(ArrayList<Double> inputs){
		this.inputs = inputs;
	}
	
	public void assignToCluster(Cluster c){
		this.cluster = c;
		this.cIndex = c.getIndex();
	}
	
	public int getClusterIndex(){
		return cIndex;
	}
	
	public Cluster getCluster(){
		return cluster;
	}
	
}
