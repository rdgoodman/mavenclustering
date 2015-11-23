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
	private ArrayList<Cluster> centroids;
	private double fitness;
	
	// personal best position
	private ArrayList<Cluster> best;
	private double bestFitness;
	
	/**
	 * Initializes a single candidate solution
	 * @param numClusters number of desired clusters	
	 * @param numDimensions size of input vector
	 */
	public Particle(int numClusters, int numDimensions){
		this.numClusters = numClusters;
		centroids = new ArrayList<Cluster>();
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
			centroids.add(new Cluster(vector, c));
		}
	}
	
	/**
	 * Assigns a data vector to the cluster that minimizes the distance from z to the centroid
	 */
	protected int findBestCluster(Datum z){
		// minimization of distance
		double min = Double.MAX_VALUE;
		int minIndex = 0;
		
		// iterates through all clusters in this particle
		for (int c = 0; c < numClusters; c++){
			double dist = calcDistToCentroid(c, z.getData());
			if (dist < min){
				dist = min;
				minIndex = c;
			}
		}

		// places this datum in the best cluster
		// (automatically assigns in other direction too)
		centroids.get(minIndex).addPoint(z);	
		
		return minIndex;
	}
	
	/**
	 * Empties the list of points assigned to each cluster
	 */
	private void clearClusters(){
		// TODO: call before assignment in PSO class
		for (Cluster c : centroids){
			c.clear();
		}
	}
	
	/**
	 * Calculates Euclidean distance to the index-th centroid in this particle
	 * @param index centroid to calculate distance to
	 * @param z data vector
	 */
	private double calcDistToCentroid(int index, ArrayList<Double> z){
		double sum = 0;
		for (int i = 0; i < numDimensions; i++){
			sum += Math.pow(z.get(i) - centroids.get(index).getCentroid().get(i), 2);
		}
		sum = Math.sqrt(sum);		
		return sum;
	}
	
	/**
	 * Calculates this particle's fitness based on quantization error
	 * @return
	 */
	protected double calcFitness(ArrayList<Datum> data){
		// see "Data Clustering using Particle Swarm Optimization"
		// equation 8
		double sum = 0;
		for (Cluster c : centroids){
			for (Datum d : c.getPts()){
				// TODO: make sure this index is correct!!!
				sum += (calcDistToCentroid(c.getIndex(), d.getData())/c.getPts().size());				
			}
		}
		// divide by number of clusters
		return (sum / centroids.size());
	}
	
	protected double getPersonalBestFitness(){
		return bestFitness;
	}
	
	protected ArrayList<Cluster> getPersonalBest(){
		return best;
	}
	
	/**
	 * Prints a representation of the particle, for testing purposes only
	 */
	public void print(){
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		System.out.println("PARTICLE: " + fitness);
		for (int i = 0; i < numClusters; i++){
			System.out.print("Cluster " + i + ": ");
			for (int j = 0; j < numDimensions; j++){
				System.out.print(Double.valueOf(twoDForm.format(centroids.get(i).getCentroid().get(j))) + "  ");
			}
			System.out.println();
		}
	}
}
