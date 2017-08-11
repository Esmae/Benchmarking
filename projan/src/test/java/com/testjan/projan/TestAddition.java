package com.testjan.projan;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Testing the add methods in Addition - which add together two tensors of the same shape 
 *
 */
public class TestAddition {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private static Dataset dataset1;
	private static Dataset dataset2;
	private static Dataset dataset3;
	private static int[] expect = new int[]{2,4,6,8,10,12,14,8,4,3,3,3};//the expected result of the addition of the two tensors
	
	//arrays used for testSortOne and testSortTwo
	private static int[] axes = new int[]{0,1,2,3}; 
	private static int[] refMult = new int[]{106,17,15,53};
	private static long[] refMultLong = new long[]{106,17,15,53};
	private static int[] expectSortedAxes = new int[]{0,3,1,2};
	
	
	/**
	 * Code executed before the first test method
	 * creates Datasets, rank 3 tensors
	 */
	@BeforeClass
	public static void setUpClass() {
		dataset1 = DatasetFactory.createFromObject(new int[]{0,1,2,3,4,5,6,7,1,1,2,2});
		dataset2 = DatasetFactory.createFromObject(new int[]{2,3,4,5,6,7,8,1,3,2,1,1});
		dataset3 = DatasetFactory.createFromObject(new int[]{2,3,4,5,6,7,8,1,3,2,1,1});
		//The two datasets must have the same shape in order to add them
		dataset1 = dataset1.reshape(2,2,3);
		dataset2 = dataset2.reshape(2,2,3);
		dataset3 = dataset3.reshape(4,1,3);
	}
	
	/**
	 * Tests the addition of two tensors using the original Position Iterator, by converting to a 1D dataset and comparing each element in turn with expect
	 */
	@Test
	public void testAdd(){
		Dataset result = Addition.add(dataset1, dataset2);
		//converting to a 1D dataset
		result = result.reshape(result.getSize());
		for(int i=0;i<expect.length;i++){
			//testing each element individually 
			Assert.assertEquals(expect[i], result.getInt(i));
		}
	}
	
	/**
	 * Tests the addition of two tensors using MyPositionIterator, by converting to a 1D dataset and comparing each element in turn with expect
	 */
	@Test
	public void testMyAdd(){
		Dataset result = Addition.myAddPartOne(dataset1, dataset2);
		//converting to a 1D dataset
		result = result.reshape(result.getSize());
		for(int i=0;i<expect.length;i++){
			//testing each element individually
			Assert.assertEquals(expect[i], result.getInt(i));
		}
	}
	
	/**
	 * Tests the addition of two tensors using MyPositionIterator, by converting to a 1D dataset and comparing each element in turn with expect
	 */
	@Test
	public void testMyAdd2(){
		Dataset result = Addition.myAdd2PartOne(dataset1, dataset2);
		//converting to a 1D dataset
		result = result.reshape(result.getSize());
		for(int i=0;i<expect.length;i++){
			//testing each element individually
			Assert.assertEquals(expect[i], result.getInt(i));
		}
	}
	
	/**
	 * Testing that add throws an exception when it should
	 */
	@Test
	public void testAddException(){
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Tensors to add must have the same shape");
		Addition.add(dataset1, dataset3);
	}
	
	/**
	 * Testing that myAdd throws an exception when it should
	 */
	@Test
	public void testMyAddException(){
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Tensors to add must have the same shape");
		Addition.myAddPartOne(dataset1, dataset3);
	}
	/**
	 * Testing that myAdd throws an exception when it should
	 */
	@Test
	public void testMyAdd2Exception(){
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Tensors to add must have the same shape");
		Addition.myAdd2PartOne(dataset1, dataset3);
	}
	
	/**
	 * Testing sortOne
	 */
	@Test
	public void testSortOne(){
		int[] sorted = Addition.sortOne(axes, refMult);
		Assert.assertArrayEquals(expectSortedAxes, sorted);
	}
	
	/**
	 * Testing sortTwo
	 */
	@Test
	public void testSortTwo(){
		int[] sorted = Addition.sortTwo(axes, refMultLong);
		Assert.assertArrayEquals(expectSortedAxes, sorted);
	}
}
