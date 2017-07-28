package com.testjan.projan;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testing the add method in Addition - which adds together two tensors of the same shape 
 *
 */
public class TestAddition {
	
	private static Dataset dataset1;
	private static Dataset dataset2;
	private static int[] expect = new int[]{2,4,6,8,10,12,14,8,4,3,3,3};//the expected result of the addition of the two tensors
	
	/**
	 * Code executed before the first test method
	 * creates two Datasets, rank 3 tensors
	 */
	@BeforeClass
	public static void setUpClass() {
		dataset1 = DatasetFactory.createFromObject(new int[]{0,1,2,3,4,5,6,7,1,1,2,2});
		dataset2 = DatasetFactory.createFromObject(new int[]{2,3,4,5,6,7,8,1,3,2,1,1});
		//The two datasets must have the same shape in order to add them
		dataset1 = dataset1.reshape(2,2,3);
		dataset2 = dataset2.reshape(2,2,3);
	}
	
	/**
	 * Tests the addition of two tensors, by converting to a 1D dataset and comparing each element in turn with expect
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
	
}
