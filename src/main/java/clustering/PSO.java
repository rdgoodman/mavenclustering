package clustering;

import java.util.ArrayList;

public class PSO {
	
	private ArrayList<Particle> swarm;
	private double omega;
	private double phi1;
	private double phi2;
	private ArrayList<Double> pg;
	
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
		// TODO: will need to take training data
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
	
	private void run(){
		// TODO: should return something other than "void"
		for (Particle p : swarm){
			double fc = p.calcFitness();
			if (fc < p.getPersonalBestFitness()){
				
			}
		}
	}
	
	
	
	
}
