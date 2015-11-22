package clusterProject;

import static org.junit.Assert.*;

import org.junit.Test;

import clustering.Particle;

public class ParticleTest {

	@Test
	public void testInitialization() {
		Particle p = new Particle(5, 3);
		p.print();
	}

}
