package clusterProject;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import clustering.CompetitiveANN;

public class CompetitiveANNTest {
	
	// TODO: testing -> prune out nodes that have never had a weight update, 
	// then run instances through and assign to clusters associated with nodes

	@Test
	public void testInitialization() {
		ArrayList<Double> in = new ArrayList<Double>();
		in.add(-2.0);
		in.add(9.0);
		in.add(0.44);
		in.add(1.46);
		
		CompetitiveANN net = new CompetitiveANN(.5, in.size(), 3);
		net.setInputs(in);
		
		net.generateOutputs();
		net.print();

	}

}
