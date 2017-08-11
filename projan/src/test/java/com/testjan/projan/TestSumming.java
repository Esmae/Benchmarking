package com.testjan.projan;


import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Assert;

/**
 * Class for testing SumDataset
 */
public class TestSumming {
	//declaring the varibles and expected results
	private static Dataset dataset1;
	private static Dataset dataset2;
	public static int[] expectStride = {6,1,3};
	public static int[] expectDelta = {12,3,6};
	public static int[] expectShape = {2,3,2};
	public static int expectRank = 3;
	public static int[] expectStrideOrder = {0,2,1};
	
	/**
	 * Setting up the datasets to be used in the unit tests
	 */
	@BeforeClass
	public static void setUpClass(){
		dataset1 = DatasetFactory.createFromObject(new int[]{3,5,4,3,5,4,1,1});
		dataset1.reshape(4,2);
		dataset2 = DatasetFactory.createRange(12);
		dataset2 = dataset2.reshape(2,2,3);
	}
	
	/**
	 * Testing the summing method using the original IndexIterator in Project January
	 */
	@Test
	public void testSumOrigIta(){
		Assert.assertEquals(26.0,SumDataset.sumOrigIterator(dataset1),0.001);
	}
	
	/**
	 * Testing the summing method using my first implementation of a Stride Iterator
	 */
	@Test
	public void testSumMyStrideIta(){
		Assert.assertEquals(26.0,SumDataset.sumMyStrideIterator(dataset1),0.001);
	}
	
	/**
	 * Testing the summing method using my second implementation of a Stride Iterator
	 */
	@Test
	public void testSumMyStrideIta2(){
		Assert.assertEquals(26.0,SumDataset.sumMyStrideIterator2(dataset1),0.001);
	}
	
	/**
	 * Testing the summing method using a simple Index iteration (no iterator object)
	 */
	@Test
	public void testSumMyIndexIterator(){
		Assert.assertEquals(26.0,SumDataset.sumMyIndexIterator(dataset1),0.001);
	}

	
}
