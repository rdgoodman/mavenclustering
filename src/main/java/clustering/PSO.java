package clustering;

import java.util.ArrayList;

public class PSO {

	private ArrayList<Particle> swarm;
	private double omega;
	private double phi1;
	private double phi2;
	private ArrayList<Double> pg;
	private ArrayList<Datum> data;
	private ArrayList<ArrayList<Double>> gbest_store;

	/**
	 * 
	 * @param omega
	 *            multiplier on momentum/inertia
	 * @param phi1
	 *            upper bound on multiplier on social component
	 * @param phi2
	 *            upper bound on multiplier on cognitive component
	 * @param swarmSize
	 *            number of particles in swarm
	 * @param numClusters
	 *            number of clusters desired
	 * @param numDimensions
	 *            length of input vector
	 */
	public PSO(double omega, double phi1, double phi2, int swarmSize, int numClusters, int numDimensions) {
		this.omega = omega;
		this.phi1 = phi1;
		this.phi2 = phi2;
		swarm = new ArrayList<Particle>();
		initSwarm(swarmSize, numClusters, numDimensions);
	}

	/**
	 * Initializes a swarm of particles
	 */
	private void initSwarm(int swarmSize, int numClusters, int numDimensions) {
		for (int i = 0; i < swarmSize; i++) {
			swarm.add(new Particle(numClusters, numDimensions));
		}
	}

	/**
	 * Runs the optimization given a set of data to cluster
	 * 
	 * @param data
	 *            the data vectors to cluster
	 * @return best clustering
	 */
	public ArrayList<Cluster> run(ArrayList<Datum> data) {
		// returns a set of Clusters corresponding to winning Particle

		// TODO: do we need to normalize the data?
		this.data = data;

		//while (!terminationCriterionMet()) {
		int count = 0;
		while (count < 1) {

			// Step 1: evaluate fitness
			double minGlobalFitness = Double.MAX_VALUE;
			for (Particle p : swarm) {
				for (Datum z : data) {
					int cluster = p.findBestCluster(z);
					// TODO: testing, remove
					System.out.println(z.getData().get(0) + " belongs in " + cluster);
				}

				double fit = p.calcFitness(data);
				if (fit < minGlobalFitness) {
					// this particle is the new global best
					gbest_store = p.copyBest();
					minGlobalFitness = fit;
					
					System.out.println("%%%%%%% NEW BEST %%%%%%%");
				}

				// TODO: testing, remove
				System.out.println("Particle fitness: " + fit);
				System.out.println();
			}

			// Step 2:
			// TODO: update global and local bests

			// Step 3: velocity update
			// TODO

			count++;
		}

		// TODO: make sure that all assignments are correct!
		// this may require that the particle does NOT keep track of its
		// cluster...

		// TODO: reassign/reevaluate g_best & return

		return null;
	}
	
	
	private boolean terminationCriterionMet(){
		// TODO: decide on termination criterion
		return false;
	}

	/**
	 * Prints a representation of the swarm for testing purposes
	 */
	public void print() {
		for (int i = 0; i < swarm.size(); i++) {
			System.out.print(i + " ~ ");
			swarm.get(i).print();
			System.out.println();
		}
	}

}
