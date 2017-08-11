package com.testjan.projan;


import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit tests for OrigTensorDot and MyTensorDot 
 *
 */
public class TestTensorDot {

	private static Dataset dataset1;
	private static Dataset dataset2;

	/**
	 * Code executed before the first test method, creates the datasets - rank 3 tensors
	 */
	@BeforeClass
	public static void setUpClass() {
		dataset1 = DatasetFactory.createRange(6);
		dataset2 = DatasetFactory.createRange(24);

		dataset1 = dataset1.reshape(1, 3, 2);
		dataset2 = dataset2.reshape(4, 2, 3);
	} 
	private double[] expect1DTensor = { 50, 140, 230, 320 };

	/**
	 * Runs a test case for the Original Tensor Dot Product Method 
	 */
	@Test
	public void testTensorDotProductOrig() {
		Dataset result = OrigTensorDot.tensorDotProduct(dataset1, dataset2, new int[] { 1, 2 }, new int[] { 2, 1 });
		//converting to a 1D DataSet
		result = result.reshape(4);
		for (int i = 0; i < 4; i++) {
			//testing each element individually
			Assert.assertEquals(expect1DTensor[i], result.getDouble(i), 0.0001);
		}

	}
	
	/**
	 * Runs a test case for my Tensor Dot Product 1
	 * Needs two cases because want covereage of if else statement 
	 */
	@Test
	public void testTensorDotProductMine1() {
		Dataset result1 = MyTensorDot.tensorDotProduct1(dataset1, dataset2, new int[] { 1, 2 }, new int[] { 2, 1 });
		//converting to a 1D DataSet
		result1 = result1.reshape(4);
		for (int i = 0; i < 4; i++) {
			//testing each element individually
			Assert.assertEquals(expect1DTensor[i], result1.getDouble(i), 0.0001);
		}
		
		Dataset result2 = MyTensorDot.tensorDotProduct1(dataset2, dataset1, new int[] { 1, 2 }, new int[] { 2, 1 });
		//converting to a 1D DataSet
		result2 = result2.reshape(4);
		for (int i = 0; i < 4; i++) {
			//testing each element individually
			Assert.assertEquals(expect1DTensor[i], result2.getDouble(i), 0.0001);
		}

	}
	
	/**
	 * Runs a test case for my Tensor Dot Product 2
	 */
	@Test
	public void testTensorDotProductMine2() {
		Dataset result1 = MyTensorDot.tensorDotProduct2(dataset1, dataset2, new int[] { 1, 2 }, new int[] { 2, 1 });
		//converting to a 1D DataSet
		result1 = result1.reshape(4);
		for (int i = 0; i < 4; i++) {
			//testing each element individually
			Assert.assertEquals(expect1DTensor[i], result1.getDouble(i), 0.0001);
		}
		
		Dataset result2 = MyTensorDot.tensorDotProduct2(dataset2, dataset1, new int[] { 1, 2 }, new int[] { 2, 1 });
		//converting to a 1D DataSet
		result2 = result2.reshape(4);
		for (int i = 0; i < 4; i++) {
			//testing each element individually
			Assert.assertEquals(expect1DTensor[i], result2.getDouble(i), 0.0001);
		}

	}

}
