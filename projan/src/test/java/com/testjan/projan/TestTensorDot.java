package com.testjan.projan;



import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.LinearAlgebra;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestTensorDot {

	private static Dataset dataset1;
	private static Dataset dataset2;
	@BeforeClass
	public static void setUpClass(){
		//Code executed before the first test method
		dataset1 = DatasetFactory.createRange(6);
		dataset2 = DatasetFactory.createRange(24);
				
		dataset1 = dataset1.reshape(1,3,2);
		dataset2 = dataset2.reshape(4,2,3);
	}
	
	private double[] expect1DTensor = {50,140,230,320};
	
	@Test
	public void testTensorDotProduct(){
		Dataset result = LinearAlgebra.tensorDotProduct(dataset1, dataset2, new int[] {1, 2},new int[] {2,1});
		result = result.reshape(4);
		for(int i=0;i<4;i++){
			Assert.assertEquals(expect1DTensor[i], result.getDouble(i),0.0001);
		}
	
		
	}
	
	
}
