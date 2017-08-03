package com.testjan.projan;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Assert;

public class TestSumming {
	
	private static Dataset dataset1;
	private static Dataset dataset2;
	private static int[] expectIndexOrder = {0,6,1,7,2,8,3,9,4,10,5,11};
	
	@BeforeClass
	public static void setUpClass(){
		dataset1 = DatasetFactory.createFromObject(new int[]{3,5,4,3,5,4,1,1});
		dataset1.reshape(4,2);
		dataset2 = DatasetFactory.createRange(12);
		dataset2 = dataset2.reshape(2,2,3);
	}
	
	@Test
	public void testSumOrigIta(){
		Assert.assertEquals(26.0,SumDataset.sumOrigIterator(dataset1),0.001);
	}
	
	@Test
	public void testSumMyStrideIta(){
		Assert.assertEquals(26.0,SumDataset.sumMyStrideIterator(dataset1),0.001);
	}
	
	@Test
	public void testHasNext(){
		MyStrideIterator ita = new MyStrideIterator(new int[]{2,2,3}, new int[]{6,3,1}, new int[]{1,2,0});
		int i=0;
		while(ita.hasNext()){
			System.out.println(ita.index);
			Assert.assertEquals(expectIndexOrder[i], ita.index,0.001);
			i++;
		}
	}
	
}
