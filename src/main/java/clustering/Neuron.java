package clustering;

import java.util.ArrayList;

public class Neuron {

	ArrayList<Neuron> parents;
	ArrayList<Neuron> children;
	ArrayList<Double> w;
	double output;
	boolean competeUnit;
	
	/**
	 * Creates a new neuron for a competitive learning neural network
	 * @param competeUnit is this a competitive (output) node?
	 */
	public Neuron(boolean competeUnit){
		parents =  new ArrayList<Neuron>();
		children = new ArrayList<Neuron>();
		this.competeUnit = competeUnit;
		w = new ArrayList<Double>();
	}
	
	public double calcOutput(){
		// TODO
		
		if (competeUnit){
			
		} else {
			return output;
		}
		
		return -1;
	}
	
	protected void initializeWeights(int numWeights){
		// TODO: these need to be less than 1 anyway, right?
		for (int i = 0; i < numWeights; i++){
			w.add(Math.random());
		}
		normalizeWeights();
	}
	
	private void normalizeWeights(){
		// 1: find max
		double max = 0;
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
	
	public void addChild(Neuron c){
		children.add(c);
	}
	
	public void addParent(Neuron p){
		parents.add(p);
	}
	
	public void setOutput(double o){
		this.output = output;
	}
	
}
