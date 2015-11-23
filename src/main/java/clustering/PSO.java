package clustering;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PSO {

	private ArrayList<Particle> swarm;
	private double omega;
	private double phi1;
	private double phi2;
	private double kappa;
	private ArrayList<Datum> data;
	private ArrayList<ArrayList<Double>> gbest_store;
	private int numDimensions;

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
	 * @param kappa
	 * 			  used for constriction coefficient
	 */
	public PSO(double omega, double phi1, double phi2, int swarmSize, int numClusters, int numDimensions, double kappa) {
		this.omega = omega;
		this.phi1 = phi1;
		this.phi2 = phi2;
		this.kappa = kappa;
		
		swarm = new ArrayList<Particle>();
		this.numDimensions = numDimensions;
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
		double minGlobalFitness = Double.MAX_VALUE;
		
		while (count < 100) {	
			//System.out.println(">>>>>>>>>> ITERATION " + count + " <<<<<<<<");
			int pcount = 0;
			for (Particle p : swarm) {
				// Step 1: evaluate fitness
				for (Datum z : data) {
					int cluster = p.findBestCluster(z);
					// TODO: testing, remove
					//System.out.println(z.getData().get(0) + " belongs in " + cluster);
				}
				double fit = p.calcFitness(data);
				p.clearClusters();
				
				// Step 2: update global best
				// note: local best taken care of in fitness evaluation within particle
				if (fit < minGlobalFitness) {
					// this particle is the new global best
					gbest_store = p.copyBest();
					minGlobalFitness = fit;
					
					//System.out.println("%%%%%%% NEW G BEST %%%%%%%");
					evaluateGBest();
				}

				// TODO: testing, remove
				//DecimalFormat twoDForm = new DecimalFormat("#.##");
				//System.out.println("Particle " + pcount + " fitness: " + Double.valueOf(twoDForm.format(fit)));
				//System.out.println();
				
				// Step 3: velocity update
				p.adjustPosition(calcVelocityUpdate(p), kappa, phi1 + phi2);	
				
				pcount++;
			}
			count++;
		}

		// reassign/reevaluate g_best & return
		System.out.println("RETURN: ");
		return evaluateGBest().getClusters();
	}
	
	
	/**
	 * Constructs a full solution out of stored info on global best
	 */
	private Particle evaluateGBest(){
		ArrayList<Cluster> solution = new ArrayList<Cluster>();
		
		for (int c = 0; c < gbest_store.size(); c++){
			solution.add(new Cluster(gbest_store.get(c), c));
		}
		
		Particle p = new Particle(solution);
		
		for (Datum z : data) {
			int cluster = p.findBestCluster(z);
			// TODO: testing, remove
			//System.out.println(z.getData().get(0) + " belongs in " + cluster);
		}
		double fit = p.calcFitness(data);
		System.out.println("Best fitness: " + fit);
		
		return p;
	}
	
	/**
	 * Calculates particle velocity update
	 */
	protected ArrayList<ArrayList<Double>> calcVelocityUpdate(Particle p){		
		ArrayList<ArrayList<Double>> velocity = new ArrayList<ArrayList<Double>>();		
		ArrayList<ArrayList<Double>> position = p.getPosition();
		ArrayList<ArrayList<Double>> pbest = p.getPersonalBest();
		
		// calculates position update for each dimension of each cluster
		for (int c = 0; c < position.size(); c++){
			ArrayList<Double> v = new ArrayList<Double>();
			for (int d = 0; d < position.get(c).size(); d++){
				// TODO vi and xi are not the same
				double momentum = omega * position.get(c).get(d);
				double social = Math.random() * phi1 * (gbest_store.get(c).get(d) - position.get(c).get(d));
				double global = Math.random() * phi2 * (pbest.get(c).get(d) - position.get(c).get(d));
				v.add(momentum + social + global);
			}
			velocity.add(v);
		}
		return velocity;
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
