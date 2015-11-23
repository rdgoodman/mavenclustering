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
	private ArrayList<ArrayList<Double>> pbest_store;
	private double bestFitness;
	
	ArrayList<ArrayList<Double>> velocity;

	/**
	 * Initializes a single candidate solution
	 * 
	 * @param numClusters
	 *            number of desired clusters
	 * @param numDimensions
	 *            size of input vector
	 */
	public Particle(int numClusters, int numDimensions) {
		this.numClusters = numClusters;
		centroids = new ArrayList<Cluster>();
		this.numDimensions = numDimensions;
		velocity = new ArrayList<ArrayList<Double>>();
		initVelocity();

		initialize();
		// initial best position
		pbest_store = copyBest();
		bestFitness = Double.MAX_VALUE;
		// TODO: set initial best fitness
	}
	
	/**
	 * Overloaded for reconstructing solution
	 */
	public Particle(ArrayList<Cluster> centroids){
		this.centroids = centroids;
		this.numClusters = centroids.size();
		this.numDimensions = centroids.get(0).getCentroid().size();
	}
	

	/**
	 * Randomly initializes particle position in the search space
	 */
	private void initialize() {
		// TODO: what are the potential ranges for the centroids?
		// going to assume [-1, 1] for now
		for (int c = 0; c < numClusters; c++) {
			ArrayList<Double> vector = new ArrayList<Double>();
			for (int d = 0; d < numDimensions; d++) {
				// randomly initialize each element of each centroid vector
				double random = Math.random();
				double prob = Math.random();
				if (prob > 0.5) {
					vector.add(random);
				} else {
					vector.add(-1 * random);
				}
			}
			centroids.add(new Cluster(vector, c));
		}
	}
	
	/**
	 * Initializes velocities to zero
	 */
	private void initVelocity(){
		for (int c = 0; c < numClusters; c++){
			ArrayList<Double> v = new ArrayList<Double>();
			for (int d = 0; d < numDimensions; d++){
				v.add(0.0);
			}
			velocity.add(v);
		}
	}

	/**
	 * Assigns a data vector to the cluster that minimizes the distance from z
	 * to the centroid
	 */
	protected int findBestCluster(Datum z) {
		// minimization of distance
		double min = Double.MAX_VALUE;
		int minIndex = 0;

		// iterates through all clusters in this particle
		for (int c = 0; c < numClusters; c++) {
			double dist = calcDistToCentroid(c, z.getData());
			// System.out.println(c + " dist: " + dist);
			// finds minimum distance
			if (dist < min) {
				// System.out.println("changed");
				min = dist;
				minIndex = c;
			}
		}

		// places this datum in the best cluster
		// (automatically assigns in other direction too)
		centroids.get(minIndex).addPoint(z);

		return minIndex;
	}

	/**
	 * Calculates Euclidean distance to the index-th centroid in this particle
	 * 
	 * @param index
	 *            centroid to calculate distance to
	 * @param z
	 *            data vector
	 */
	private double calcDistToCentroid(int index, ArrayList<Double> z) {
		double sum = 0;
		for (int i = 0; i < numDimensions; i++) {
			sum += Math.pow(z.get(i) - centroids.get(index).getCentroid().get(i), 2);
		}
		sum = Math.sqrt(sum);
		return sum;
	}

	/**
	 * Empties the list of points assigned to each cluster
	 */
	protected void clearClusters() {
		// TODO: call before assignment in PSO class
		for (Cluster c : centroids) {
			c.clear();
		}
	}

	/**
	 * Calculates this particle's fitness based on quantization error
	 * 
	 * @return
	 */
	protected double calcFitness(ArrayList<Datum> data) {
		// see "Data Clustering using Particle Swarm Optimization"
		// equation 8
		double sum = 0;
		for (Cluster c : centroids) {
			for (Datum d : c.getPts()) {
				// TODO: make sure this index is correct!!!
				sum += (calcDistToCentroid(c.getIndex(), d.getData()) / c.getPts().size());
			}
		}
		
		double fitness = sum / centroids.size();

		// handle personal best - recall, this is a min problem
		if (fitness < bestFitness) {
			//System.out.println("%%%%%%% NEW P BEST %%%%%%%");
			bestFitness = fitness;
			pbest_store = copyBest();
		}

		// divide by number of clusters
		return fitness;
	}
	
	
	/**
	 * Updates position based on velocity update
	 */
	protected void adjustPosition(ArrayList<ArrayList<Double>> velocityUpdate){
		// TODO: handle velocity clamping or something
		for (int c = 0; c < numClusters; c++){
			for (int d = 0; d < numDimensions; d++){
				centroids.get(c).getCentroid().set(d, velocityUpdate.get(c).get(d));
			}
		}
	}

	/**
	 * Copies the centroids associated with the best cluster
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<Double>> copyBest() {
		ArrayList<ArrayList<Double>> b = new ArrayList<ArrayList<Double>>();
		// copies one centroid at a time
		for (int c = 0; c < numClusters; c++) {
			ArrayList<Double> centroidCopy = new ArrayList<Double>();
			for (int d = 0; d < numDimensions; d++) {
				// copies dimension d from centroid c
				centroidCopy.add(centroids.get(c).getCentroid().get(d));
			}
			b.add(centroidCopy);
		}

		// TODO: testing, remove
		// print();
		// DecimalFormat twoDForm = new DecimalFormat("#.##");
		// System.out.println("Best stored:");
		// for (int i = 0; i < b.size(); i++){
		// for (int j = 0; j < b.get(i).size(); j++){
		// System.out.print(Double.valueOf(twoDForm.format(b.get(i).get(j))) + "
		// ");
		// }
		// System.out.println();
		// }
		return b;
	}

	protected double getPersonalBestFitness() {
		return bestFitness;
	}

	protected ArrayList<ArrayList<Double>> getPersonalBest() {
		return pbest_store;
	}
	
	protected ArrayList<ArrayList<Double>> getPosition() {
		ArrayList<ArrayList<Double>> p = new ArrayList<ArrayList<Double>>();
		for (int c = 0; c < numClusters; c++){
			p.add(centroids.get(c).getCentroid());
		}
		return p;
	}
	
	protected ArrayList<Cluster> getClusters(){
		return centroids;
	}

	public void setPersonalBest(ArrayList<ArrayList<Double>> b) {
		// TODO: also set personal best fitness, reassign
		this.pbest_store = b;
	}

	/**
	 * Prints a representation of the particle, for testing purposes only
	 */
	public void print() {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		System.out.println("PARTICLE: " + fitness);
		for (int i = 0; i < numClusters; i++) {
			System.out.print("Cluster " + i + ": ");
			for (int j = 0; j < numDimensions; j++) {
				System.out.print(Double.valueOf(twoDForm.format(centroids.get(i).getCentroid().get(j))) + "  ");
			}
			System.out.println();
		}
	}
}
