package clustering;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Particle {

	// number of cluster centroid vectors
	private int numClusters;
	// number of dimensions in input vectors
	private int numDimensions;

	// Arraylist(i)(j) is the jth cluster centroid vector of
	// the ith particle in cluster Cij
	private ArrayList<ArrayList<Double>> centroids;
	private double fitness;
	
	// personal best position
	private ArrayList<ArrayList<Double>> best;
	private double bestFitness;
	
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
		// initial best position 
		best = centroids;
	}
	
	/**
	 * Randomly initializes particle position in the search space
	 */
	private void initialize(){
		// TODO: what are the potential ranges for the centroids?
		// going to assume 0-1 for now
		for (int c = 0; c < numClusters; c++){
			ArrayList<Double> vector = new ArrayList<Double>();
			for (int d = 0; d < numDimensions; d++){
				// randomly initialize each element of each centroid vector
				vector.add(Math.random());
			}
			centroids.add(vector);
		}
	}
	
	/**
	 * Assigns a data vector to the cluster that minimizes the distance from z to the centroid
	 */
	protected int assignToBestCluster(ArrayList<Double> z){
		// minimization of distance
		double min = Double.MAX_VALUE;
		int minIndex = 0;
		
		// iterates through all clusters in this particle
		for (int c = 0; c < numClusters; c++){
			double dist = calcDistToCentroid(c, z);
			if (dist < min){
				dist = min;
				minIndex = c;
			}
		}
		// TODO: we need some way to make this assignment stick 
		// maybe inputs can be an object that have an additional field, "cluster"?
		return minIndex;
	}
	
	/**
	 * Calculates Euclidean distance to the index-th centroid in this particle
	 * @param index centroid to calculate distance to
	 * @param z data vector
	 */
	private double calcDistToCentroid(int index, ArrayList<Double> z){
		double sum = 0;
		for (int i = 0; i < numDimensions; i++){
			sum += Math.pow(z.get(i) - centroids.get(index).get(i), 2);
		}
		sum = Math.sqrt(sum);		
		return sum;
	}
	
	/**
	 * Calculates this particle's fitness based on quantization error
	 * @return
	 */
	protected double calcFitness(ArrayList<ArrayList<Double>> data){
		// TODO: see "Data Clustering using Particle Swarm Optimization"
		// apparently this is known as "quantization error"
		return -1;
	}
	
	protected double getPersonalBestFitness(){
		return bestFitness;
	}
	
	protected ArrayList<ArrayList<Double>> getPersonalBest(){
		return best;
	}
	
	/**
	 * Prints a representation of the particle, for testing purposes only
	 */
	public void print(){
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		System.out.println("PARTICLE:");
		for (int i = 0; i < numClusters; i++){
			System.out.print("Cluster " + i + ": ");
			for (int j = 0; j < numDimensions; j++){
				System.out.print(Double.valueOf(twoDForm.format(centroids.get(i).get(j))) + "  ");
			}
			System.out.println();
		}
	}
}
