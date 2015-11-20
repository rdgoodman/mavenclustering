package clusterProject;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import clustering.CompetitiveANN;

public class CompetitiveANNTest {

	@Test
	public void testInitialization() {
		ArrayList<Double> in = new ArrayList<Double>();
		in.add(-2.0);
		in.add(9.0);
		in.add(0.44);
		in.add(1.46);
		
		CompetitiveANN net = new CompetitiveANN(.5, in.size(), 3);
		net.setInputs(in);
		net.print();
		
		net.generateOutputs();
		net.print();
	}

}
