package clustering;

import java.util.ArrayList;

// largely relies on http://page.mi.fu-berlin.de/rojas/neural/chapter/K5.pdf

public class CompetitiveANN {
	
	private int numInputs;
	private double eta;
	private int numOutputs;
	private ArrayList<ArrayList<Neuron>> nodes;
	private ArrayList<Double> inputs;

	/**
	 * Creates a new competitive learning neural network
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
	
	private void setInputs(ArrayList<Double> inputs){
		this.inputs = inputs;
		createNetStructure();
	}
	
	
	/**
	 * Creates and connects all nodes in the network
	 */
	private void createNetStructure(){
		ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
		ArrayList<Neuron> competeLayer = new ArrayList<Neuron>();
		
		// populates input layer
		for(int i = 0; i < numInputs; i++){
			inputLayer.add(new Neuron(false));
			// init weights
			inputLayer.get(i).initializeWeights(numInputs);
		}
		
		// populates output layer
		for (int i = 0; i < numOutputs; i++){
			competeLayer.add(new Neuron(true));
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
			}
		}
		
		nodes.add(inputLayer);
		nodes.add(competeLayer);
	}

}
