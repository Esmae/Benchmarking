package com.testjan.projan;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing the reference benchmark 
 *
 */
public class TestReference {
	
	private static int[] testShape = new int[]{10,10,10};
	private double expectSum = 491.6736;
	
	/**
	 * Testing the reference Benchmark
	 * If the seed is changed, this unit test must be re-written
	 */
	@Test
	public void testRef(){
		Reference myRef = new Reference(testShape);
		Assert.assertEquals(expectSum,myRef.addNum(),0.001);
	}
}
