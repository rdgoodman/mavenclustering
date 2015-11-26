package clusterProject;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import clustering.ClusterSet;
import clustering.CompetitiveANN;
import clustering.Datum;

public class CompetitiveANNTest {	

//	@Test
//	public void testInitialization() {
//		ArrayList<Double> in = new ArrayList<Double>();
//		in.add(-2.0);
//		in.add(9.0);
//		in.add(0.44);
//		in.add(1.46);
//		
//		CompetitiveANN net = new CompetitiveANN(.5, in.size(), 3);
//		net.setInputs(in);
//		
//		net.generateOutputs();
//		net.print();
//
//	}
	
	@Test
	public void testTestandTrain(){
		ArrayList<Double> a1 = new ArrayList<Double>();
		ArrayList<Double> a2 = new ArrayList<Double>();
		ArrayList<Double> a3 = new ArrayList<Double>();
		ArrayList<Double> a4 = new ArrayList<Double>();
		ArrayList<Double> a5 = new ArrayList<Double>();
		ArrayList<Double> a6 = new ArrayList<Double>();
		a1.add(0.3);
		a1.add(.41);
		a2.add(.29);
		a2.add(.31);
		a3.add(.04);
		a3.add(.132);
		a4.add(.47);
		a4.add(.82);
		a5.add(.44);
		a5.add(.95);
		a6.add(.32);
		a6.add(.19);
		
		Datum d1 = new Datum(a1);
		Datum d2 = new Datum(a2);
		Datum d3 = new Datum(a3);
		Datum d4 = new Datum(a4);
		Datum d5 = new Datum(a5);
		Datum d6 = new Datum(a6);
		
		CompetitiveANN net = new CompetitiveANN(0.5, d1.getData().size(), 5);
		
		ArrayList<Datum> test = new ArrayList<Datum>();
		test.add(d4);
		test.add(d5);
		test.add(d6);
		ArrayList<Datum> train = new ArrayList<Datum>();
		train.add(d1);
		train.add(d2);
		train.add(d3);
		
		ClusterSet soln = new ClusterSet(net.run(train, test));
		//soln.print();
		soln.calcCohesion();
		//soln.calcSeparation();
	}

}
