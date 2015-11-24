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
	
	/** 
	 * Calculates pairwise distance to another Datum
	 */
	public double calcDist(Datum o){
		double dist = 0;
		for (int i = 0; i < inputs.size(); i++){
			dist += Math.pow(this.inputs.get(i) - o.getData().get(i), 2);
		}
		return dist;
	}
	
	public ArrayList<Double> getData(){
		return inputs;
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
