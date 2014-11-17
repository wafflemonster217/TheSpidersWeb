package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import main.Bug;
import main.SpiderWeb;

import org.junit.Before;
import org.junit.Test;

public class FileLoadingTests {
	private SpiderWeb sp;
	@Before
	public void before() {
		sp = new SpiderWeb();
	}

	@Test
	public void testObjectInstantiation() {
		assertTrue(sp != null);
	}
	
	@Test
	public void testCorrectNumBugs() {
		assertEquals(91, sp.n);
	}

	@Test
	public void testGraphSize() {
		assertEquals(93, sp.web1.size());
	}
	
	@Test
	public void testBug0Adjacencies() {
		for (Bug b : sp.web1)
			if (b.id == 0) {
				assertEquals(1, b.neighbors.size());
				assertTrue(b.neighbors.contains(1));
			}
	}
	
	@Test
	public void testBug1Adjacencies() {
		for (Bug b : sp.web1)
			if (b.id == 1) {
				assertEquals(3, b.neighbors.size());
				assertTrue(b.neighbors.contains(0));
				assertTrue(b.neighbors.contains(2));
				assertTrue(b.neighbors.contains(12));
			}
	}
	
	@Test
	public void testBug10Adjacencies() {
		for (Bug b : sp.web1)
			if (b.id == 10) {
				assertEquals(3, b.neighbors.size());
				assertTrue(b.neighbors.contains(9));
				assertTrue(b.neighbors.contains(16));
				assertTrue(b.neighbors.contains(11));
			}
	}
	
	@Test
	public void testBug35Adjacencies() {
		for (Bug b : sp.web1)
			if (b.id == 35) {
				assertEquals(4, b.neighbors.size());
				assertTrue(b.neighbors.contains(30));
				assertTrue(b.neighbors.contains(45));
				assertTrue(b.neighbors.contains(36));
				assertTrue(b.neighbors.contains(34));
			}
	}
	
	@Test
	public void testDirection0_1() {
		assertEquals("E", sp.directions[0][1]);
	}
	
	@Test
	public void testDirection1_0() {
		assertEquals("W", sp.directions[1][0]);
	}
	
	@Test
	public void testDirection3_14() {
		assertEquals("SW", sp.directions[3][14]);
	}
	
	@Test
	public void testDirection14_3() {
		assertEquals("NE", sp.directions[14][3]);
	}
	
	@Test
	public void testDirection42_48() {
		assertEquals("S", sp.directions[42][48]);
	}
	
	@Test
	public void testDirection48_42() {
		assertEquals("N", sp.directions[48][42]);
	}
	
	@Test
	public void testDirection66_62() {
		assertEquals("NE", sp.directions[66][62]);
	}
	
	@Test
	public void testDirection62_66() {
		assertEquals("SW", sp.directions[62][66]);
	}
	
	@Test
	public void testBuildWeb2() {
		sp.buildWeb2();
		assertEquals(89, sp.web2.size());
		assertTrue(!sp.web2.contains(13));
		assertTrue(!sp.web2.contains(14));
	}
	
	@Test
	public void testWeb2Adjacencies_0() {
		sp.buildWeb2();
		for (Bug b : sp.web2)
			if (b.id == 0) {
				assertEquals(1, b.neighbors.size());
				assertTrue(b.neighbors.contains(3));
			}
	}
	
	@Test
	public void testWeb2Adjacencies_64() {
		sp.buildWeb2();
		for (Bug b : sp.web2)
			if (b.id == 64) {
				assertEquals(2, b.neighbors.size());
				assertTrue(b.neighbors.contains(45));
				assertTrue(b.neighbors.contains(92));
			}
	}
	
	@Test
	public void testWeb2Adjacencies_92() {
		sp.buildWeb2();
		for (Bug b : sp.web2)
			if (b.id == 92) {
				assertEquals(1, b.neighbors.size());
				assertTrue(b.neighbors.contains(64));
			}
	}
	
	@Test
	public void testWeb2Adjacencies_3() {
		sp.buildWeb2();
		for (Bug b : sp.web2)
			if (b.id == 3) {
				assertEquals(4, b.neighbors.size());
				assertTrue(b.neighbors.contains(0));
				assertTrue(b.neighbors.contains(6));
				assertTrue(b.neighbors.contains(30));
				assertTrue(b.neighbors.contains(19));
			}
	}
	
	@Test
	public void testBFS1() {
		sp.buildWeb2();
		sp.BFS();
		sp.traceBack();
		while (!sp.solution.empty())
			System.out.print(sp.solution.pop() + ", ");
	}
	
}
