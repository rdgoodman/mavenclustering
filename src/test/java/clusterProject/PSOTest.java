package clusterProject;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import clustering.Datum;

public class PSOTest {

	@Test
	public void test() {
		ArrayList<Double> a1 = new ArrayList<Double>();
		a1.add(-0.56);
		a1.add(0.22);
		a1.add(0.08);
		ArrayList<Double> a2 = new ArrayList<Double>();
		a2.add(0.34);
		a2.add(-0.20);
		a2.add(0.7);
		ArrayList<Double> a3 = new ArrayList<Double>();
		a3.add(0.0);
		a3.add(0.58);
		a3.add(-0.97);
		
		Datum d1 = new Datum(a1);
		Datum d2 = new Datum(a1);
		Datum d3 = new Datum(a1);

	}

}
