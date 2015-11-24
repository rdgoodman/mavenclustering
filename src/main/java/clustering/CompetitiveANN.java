package clustering;

import java.util.ArrayList;

// largely relies on http://page.mi.fu-berlin.de/rojas/neural/chapter/K5.pdf
// and Engelbrecht p60

public class CompetitiveANN {

	// TODO: testing -> prune out nodes that have never had a weight update,
	// then run instances through and assign to clusters associated with nodes

	private int numDimensions;
	private double eta;
	private int numOutputs;
	private ArrayList<ArrayList<Neuron>> nodes;
	private ArrayList<Double> inputs;
	
	private boolean training = true;

	/**
	 * Creates a new competitive learning neural network - requires calling
	 * setInputs immediately after
	 * 
	 * @param eta
	 *            learning rate
	 * @param numDim
	 *            Size of input vector (dimensions)
	 * @param numOutputs
	 *            number of clusters
	 */
	public CompetitiveANN(double eta, int numDim, int numOutputs) {
		this.eta = eta;
		this.numDimensions = numDim;
		this.numOutputs = numOutputs;

		// number of outputs must be =< number of inputs(?) for some reason (see
		// class notes 10/7)
		if (numOutputs > numDim) {
			numOutputs = numDim;
		}

		nodes = new ArrayList<ArrayList<Neuron>>();
		createNetStructure();
	}

	/**
	 * Takes inputs and builds network structure
	 */
	public void setInputs(ArrayList<Double> inputs) {
		this.inputs = inputs;
		normalize(this.inputs);
		giveInputs();
	}
	
	private void giveInputs(){
		// set output for input nodes
		for (int i = 0; i < numDimensions; i++) {
			nodes.get(0).get(i).setOutput(inputs.get(i));
		}
	}

	/**
	 * Runs train and test process given two (non-overlapping) datasets
	 * @param train Training set
	 * @param test Test set
	 * @return Clustered data
	 */
	public ArrayList<Cluster> run(ArrayList<Datum> train, ArrayList<Datum> test){
		// first train
		for (Datum d : train){
			// set inputs
			setInputs(d.getData());
			generateOutputs();
		}
		training = false;
		
		// trim off unused compete nodes
		prune();
		
		// then test
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		
		// create a cluster for each remaining compete node
		for (int i = 0; i < nodes.get(1).size(); i++){
			// TODO: wtf is the centroid...? the weights?
			clusters.add(new Cluster(nodes.get(1).get(i).getWeights(), i));
		}
		
		for(Datum d : test){
			setInputs(d.getData());
			int index = generateOutputs();
			// assign this datum to the cluster
			clusters.get(index).addPoint(d);
		}
				
		return clusters;
	}

	/**
	 * Normalizes the input vector
	 */
	private ArrayList<Double> normalize(ArrayList<Double> i) {
		// find max
		double max = Double.MIN_VALUE;
		for (int x = 0; x < i.size(); x++) {
			if (i.get(x) > max) {
				max = i.get(x);
			}
		}

		// divide all by max
		for (int x = 0; x < i.size(); x++) {
			i.set(x, i.get(x) / max);
		}
		return i;
	}

	/**
	 * Creates and connects all nodes in the network
	 */
	private void createNetStructure() {
		ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
		ArrayList<Neuron> competeLayer = new ArrayList<Neuron>();

		// populates input layer
		for (int i = 0; i < numDimensions; i++) {
			inputLayer.add(new Neuron(false, eta));
			// init weights
			inputLayer.get(i).initializeWeights(numDimensions);
		}

		// populates output layer
		for (int i = 0; i < numOutputs; i++) {
			competeLayer.add(new Neuron(true, eta));
			// init weights
			competeLayer.get(i).initializeWeights(numDimensions);
		}


		// connects all input nodes to output nodes & vice versa
		for (int i = 0; i < numDimensions; i++) {
			for (int j = 0; j < numOutputs; j++) {
				inputLayer.get(i).addChild(competeLayer.get(j));
				competeLayer.get(j).addParent(inputLayer.get(i));
				// set inputs
				competeLayer.get(j).addInput(inputLayer.get(i).getOutput());
			}
		}

		nodes.add(inputLayer);
		nodes.add(competeLayer);
	}

	/**
	 * Propagates inputs through network
	 */
	public int generateOutputs() {
		// find "winner"
		int maxIndex = 0;
		double max = Double.MIN_VALUE;
		for (int o = 0; o < numOutputs; o++) {
			nodes.get(1).get(o).calcOutput();
			double op = nodes.get(1).get(o).getOutput();
			if (op > max) {
				max = op;
				maxIndex = o;
			}
		}

		// TODO: testing, remove
		print();

		if (training) {
			// update winner's weights
			nodes.get(1).get(maxIndex).setUsed();
			nodes.get(1).get(maxIndex).updateWeights();
		}

		return maxIndex;

	}
	
	
	/**
	 * Removes nodes/clusters that never have their weights updated
	 */
	private void prune(){
		ArrayList<Neuron> toPrune = new ArrayList<Neuron>();
		for (Neuron n : nodes.get(1)){
			if (!n.isUsed()){
				System.out.println("Not used");
				// remove connections
				for (Neuron p : n.getParents()){
					p.getChildren().remove(n);
					n.getParents().remove(p);
				}
				toPrune.add(n);
			}
		}
		// remove node from network entirely
		for (Neuron n : toPrune){
			nodes.get(1).remove(n);
		}
		
	}
	

	/**
	 * Prints net for testing purposes
	 */
	public void print() {
		System.out.println();
		System.out.print("inputs: ");
		for (int i = 0; i < numDimensions; i++) {
			System.out.print(nodes.get(0).get(i));
		}
		System.out.println();
		System.out.print("compete: ");
		for (int i = 0; i < numOutputs; i++) {
			System.out.print(nodes.get(1).get(i));
		}
		System.out.println();
	}

}
