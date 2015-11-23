package clustering;

import java.util.ArrayList;

public class PSO {
	
	private ArrayList<Particle> swarm;
	private double omega;
	private double phi1;
	private double phi2;
	private ArrayList<Double> pg;
	private ArrayList<Datum> data;
	
	/**
	 * 
	 * @param omega multiplier on momentum/inertia
	 * @param phi1 upper bound on multiplier on social component
	 * @param phi2 upper bound on multiplier on cognitive component
	 * @param swarmSize number of particles in swarm
	 * @param numClusters number of clusters desired
	 * @param numDimensions length of input vector
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
	private void initSwarm(int swarmSize, int numClusters, int numDimensions){
		for (int i = 0; i < swarmSize; i++){
			swarm.add(new Particle(numClusters, numDimensions));
		}
	}
	
	private ArrayList<Cluster> run(ArrayList<Datum> data){
		// returns a set of Clusters corresponding to winning Particle
		
		// TODO: do we need to normalize the data?
		this.data = data;
		
		// Step 1
		for (Particle p : swarm){
			for (Datum z : data){
				p.findBestCluster(z);				
			}
			p.calcFitness(data);
		}
		return null;
	}
	
	
	
	
	
}
