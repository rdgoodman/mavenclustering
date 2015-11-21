package clustering;

import java.util.ArrayList;

// largely relies on http://page.mi.fu-berlin.de/rojas/neural/chapter/K5.pdf
// and Engelbrecht p60

public class CompetitiveANN {
	
	private int numInputs;
	private double eta;
	private int numOutputs;
	private ArrayList<ArrayList<Neuron>> nodes;
	private ArrayList<Double> inputs;

	/**
	 * Creates a new competitive learning neural network - requires calling setInputs immediately after
	 * @param eta learning rate
	 * @param numInputs number of inputs (size)
	 * @param numOutputs number of clusters
	 */
	public CompetitiveANN(double eta, int numInputs, int numOutputs){
		this.eta = eta;
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		
		// number of outputs must be =< number of inputs(?) for some reason (see class notes 10/7)
		if(numOutputs > numInputs){
			numOutputs = numInputs;
		}
		
		nodes = new ArrayList<ArrayList<Neuron>>();
	}
	
	/**
	 * Takes inputs and builds network structure
	 */
	public void setInputs(ArrayList<Double> inputs){
		this.inputs = inputs;
		normalize(inputs);
		createNetStructure();
	}
	
	/**
	 * Normalizes the input vector
	 */
	private ArrayList<Double> normalize(ArrayList<Double> i){
		// find max
		double max = Double.MIN_VALUE;
		for (int x = 0; x < i.size(); x++){
			if (i.get(x) > max){
				max = i.get(x);
			}
		}
		
		// divide all by max
		for (int x = 0; x < i.size(); x++){
			i.set(x, i.get(x)/max);
		}
		return i;
	}
	
	
	/**
	 * Creates and connects all nodes in the network
	 */
	private void createNetStructure(){
		ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
		ArrayList<Neuron> competeLayer = new ArrayList<Neuron>();
		
		// populates input layer
		for(int i = 0; i < numInputs; i++){
			inputLayer.add(new Neuron(false, eta));
			// init weights
			inputLayer.get(i).initializeWeights(numInputs);
		}
		
		// populates output layer
		for (int i = 0; i < numOutputs; i++){
			competeLayer.add(new Neuron(true, eta));
			// init weights
			competeLayer.get(i).initializeWeights(numInputs);
		}
		
		// set output for input nodes
		for (int i = 0; i < numInputs; i++){
			inputLayer.get(i).setOutput(inputs.get(i));
		}
		
		// connects all input nodes to output nodes & vice versa
		for (int i = 0; i < numInputs; i++){
			for (int j = 0; j < numOutputs; j++){
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
	public void generateOutputs(){
		// TODO: will eventually need to return something other than "void"
		
		// find "winner"
		int maxIndex = 0;
		double max = Double.MIN_VALUE;
		for (int o = 0; o < numOutputs; o++){
			nodes.get(1).get(o).calcOutput();
			double op = nodes.get(1).get(o).getOutput();
			if (op > max){
				max = op;
				maxIndex = o;
			}
		}
		
		// TODO: testing, remote
		print();
		
		// update winner's weights
		nodes.get(1).get(maxIndex).updateWeights();
		
	}
	

	
	/**
	 * Prints net for testing purposes
	 */
	public void print(){
		System.out.println();
		System.out.print("inputs: ");
		for(int i = 0; i < numInputs; i++){
			System.out.print(nodes.get(0).get(i));
		}
		System.out.println();
		System.out.print("compete: ");
		for(int i = 0; i < numOutputs; i++){
			System.out.print(nodes.get(1).get(i));
		}
		System.out.println();
	}

}
