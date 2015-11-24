package clustering;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Neuron {

	ArrayList<Neuron> parents;
	ArrayList<Neuron> children;
	ArrayList<Double> w;
	ArrayList<Double> inputs;
	double output;
	double eta;
	boolean competeUnit;
	boolean used = false;
	
	/**
	 * Creates a new neuron for a competitive learning neural network
	 * @param competeUnit is this a competitive (output) node?
	 */
	public Neuron(boolean competeUnit, double eta){
		parents =  new ArrayList<Neuron>();
		children = new ArrayList<Neuron>();
		this.competeUnit = competeUnit;
		w = new ArrayList<Double>();
		inputs = new ArrayList<Double>();
		this.eta = eta;
	}
	
	/**
	 * Calculates neuron's output as a weighted sum of inputs
	 */
	public double calcOutput(){
		if (competeUnit){
			output = 0;
			for (int i = 0; i < inputs.size(); i++){
				output += (w.get(i) * inputs.get(i));
			}
			return output;
		} else {
			return output;
		}
	}
	
	/**
	 * Initializes unnormalized weights
	 * @param numWeights number of weights fo this neuron
	 */
	protected void initializeWeights(int numWeights){
		// these need to be less than 1 anyway, right?
		for (int i = 0; i < numWeights; i++){
			w.add(Math.random());
		}
		normalizeWeights();
	}
	
	/**
	 * Normalizes weights
	 */
	private void normalizeWeights(){
		// 1: find max
		double max = Double.MIN_VALUE;
		for (int i = 0; i < w.size(); i++){
			if(w.get(i) > max){
				max = w.get(i);
			}
		}
		
		// 2: divide all by max
		for (int i = 0; i < w.size(); i++){
			w.set(i, w.get(i)/max);
		}
	}
	
	/**
	 * Uses weight update equation on a single neuron
	 */
	protected void updateWeights(){
		
		// update each weight
		for (int i = 0; i < w.size(); i++){
			double delta = eta * (inputs.get(i) - w.get(i));
			w.set(i, w.get(i) + delta);
		}
		
		// TODO: re-normalize
		normalizeWeights();
	}
	

	
	public void addChild(Neuron c){
		children.add(c);
	}
	
	public void addParent(Neuron p){
		parents.add(p);
	}
	
	public void setOutput(double o){
		this.output = o;
	}
	
	public double getOutput(){
		return output;
	}
	
	public ArrayList<Double> getWeights(){
		return w;
	}
	
	public void addInput(double i){
		inputs.add(i);
	}
	
	public ArrayList<Double> getInputs(){
		return inputs;
	}
	
	public void setUsed(){
		this.used = true;
	}
	
	public boolean isUsed(){
		return used;
	}
	
	public ArrayList<Neuron> getParents(){
		return parents;
	}
	
	public ArrayList<Neuron> getChildren(){
		return children;
	}
	
	public String toString(){
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		DecimalFormat threeDForm = new DecimalFormat("#.###");		
		
		String s = "[";
		for (int i = 0; i < w.size(); i++){
			s += (Double.valueOf(twoDForm.format(w.get(i))) + " ");
		}
		
		return s += (" ~ " + Double.valueOf(threeDForm.format(output)) + "]  ");
	}
}
