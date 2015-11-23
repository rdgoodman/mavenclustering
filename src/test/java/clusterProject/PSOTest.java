package clusterProject;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import clustering.Datum;
import clustering.PSO;

public class PSOTest {

//	@Test
//	public void test() {
//		
//		ArrayList<Double> a1 = new ArrayList<Double>();
//		a1.add(-0.56);
//		a1.add(0.22);
//		//a1.add(0.08);
//		ArrayList<Double> a2 = new ArrayList<Double>();
//		a2.add(0.34);
//		a2.add(-0.20);
//		//a2.add(0.7);
//		ArrayList<Double> a3 = new ArrayList<Double>();
//		a3.add(0.0);
//		a3.add(0.58);
//		//a3.add(-0.33);
//		
//		Datum d1 = new Datum(a1);
//		Datum d2 = new Datum(a2);
//		Datum d3 = new Datum(a3);
//		ArrayList<Datum> data = new ArrayList<Datum>();
//		data.add(d1);
//		data.add(d2);
//		data.add(d3);
//		
//		PSO pso = new PSO(0.5, 0.5, 0.5, 10, 3, d1.getData().size());
//		pso.print();
//		pso.run(data);
//
//	}

	@Test
	public void testCopyBest(){
		ArrayList<Double> a1 = new ArrayList<Double>();
		a1.add(-0.56);
		a1.add(0.22);
		//a1.add(0.08);
		ArrayList<Double> a2 = new ArrayList<Double>();
		a2.add(0.34);
		a2.add(-0.20);
		//a2.add(0.7);
		ArrayList<Double> a3 = new ArrayList<Double>();
		a3.add(0.0);
		a3.add(0.58);
		//a3.add(-0.33);
		
		Datum d1 = new Datum(a1);
		Datum d2 = new Datum(a2);
		Datum d3 = new Datum(a3);
		ArrayList<Datum> data = new ArrayList<Datum>();
		data.add(d1);
		data.add(d2);
		data.add(d3);
		
		PSO pso = new PSO(0.8, 0.8, 0.8, 10, 3, d1.getData().size());
		pso.print();
		pso.run(data);
	}
}
