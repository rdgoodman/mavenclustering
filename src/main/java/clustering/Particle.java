package clustering;

import java.util.ArrayList;

public class Particle {

	// number of cluster centroid vectors
	private int numClusters;
	// number of dimensions in input vectors
	private int numDimensions;
	
	
	// Arraylist(i)(j) is the jth cluster centroid vector of
	// the ith particle in cluster Cij
	private ArrayList<ArrayList<Double>> centroids;
	
	/**
	 * Initializes a single candidate solution
	 * @param numClusters number of desired clusters	
	 * @param numDimensions size of input vector
	 */
	public Particle(int numClusters, int numDimensions){
		this.numClusters = numClusters;
		centroids = new ArrayList<ArrayList<Double>>();
		this.numDimensions = numDimensions;
		
		initialize();
	}
	
	private void initialize(){
		// TODO: what are the potential ranges for the centroids?
	}
	
	
	protected double calcFitness(){
		// TODO: see "Data Clustering using Particle Swarm Optimization"
		return -1;
	}
}
