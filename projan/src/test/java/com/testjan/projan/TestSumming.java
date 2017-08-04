package com.testjan.projan;

/**
 * Class for testing MyStrideIterator and the SumDataset class that it is used in
 */
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Assert;

public class TestSumming {
	//declaring the expected results
	private static Dataset dataset1;
	private static Dataset dataset2;
	private static int[] expectIndexOrder = {0,6,1,7,2,8,3,9,4,10,5,11};
	private static int[][] expectPos1 = {{0,0,0},{1,0,0},{0,0,1},{1,0,1},{0,0,2},{1,0,2},{0,1,0},{1,1,0},{0,1,1},{1,1,1},{0,1,2},{1,1,2}};
	private static int[][] expectPos2 = {{0,0,0},{0,1,0},{0,2,0},{0,0,1},{0,1,1},{0,2,1},{1,0,0},{1,1,0},{1,2,0},{1,0,1},{1,1,1},{1,2,1}};
	private static int expectResetIndex1 = -6;
	private static int expectResetIndex2 = -1;
	private static int[] expectResetPos1 = {-1,0,0};
	private static int[] expectResetPos2 = {0,-1,0};
	public static int[] expectStride = {6,1,3};
	public static int[] expectDelta = {12,3,6};
	public static int[] expectShape = {2,3,2};
	public static int expectRank = 3;
	public static int[] expectStrideOrder = {0,2,1};
	
	/**
	 * setting up the datasets to be used in the unit tests
	 */
	@BeforeClass
	public static void setUpClass(){
		dataset1 = DatasetFactory.createFromObject(new int[]{3,5,4,3,5,4,1,1});
		dataset1.reshape(4,2);
		dataset2 = DatasetFactory.createRange(12);
		dataset2 = dataset2.reshape(2,2,3);
	}
	/**
	 * Testing the summing method using the original IndexIterator in Porject January
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
	/**
	 * Testing my first implementation of hasNext() for MyStrideIterator
	 * (First test)	
	 */
	@Test
	public void testHasNext(){
		MyStrideIterator ita = new MyStrideIterator(new int[]{2,2,3}, new int[]{6,3,1}, new int[]{1,2,0});
		int i=0;
		while(ita.hasNext()){
			Assert.assertEquals(expectIndexOrder[i], ita.index,0.001);
			Assert.assertArrayEquals(expectPos1[i], ita.getPos());
			i++;
		}
	}
	/**
	 * Testing my second implementation of hasNext() for MyStrideIterator
	 * (First test)
	 */
	@Test
	public void testHasNext2(){
		MyStrideIterator ita = new MyStrideIterator(new int[]{2,2,3}, new int[]{6,3,1}, new int[]{1,2,0});
		int i=0;
		while(ita.hasNext2()){
			Assert.assertEquals(expectIndexOrder[i], ita.index,0.001);
			Assert.assertArrayEquals(expectPos1[i], ita.getPos());
			i++;
		}
	}
	/**
	 * Testing my first implementation of hasNext() for MyStrideIterator
	 * (Second test)	
	 */
	@Test
	public void testHasNextAgain(){
		MyStrideIterator ita = new MyStrideIterator(new int[]{2,3,2}, new int[]{6,1,3}, new int[]{0,2,1});
		int i=0;
		while(ita.hasNext()){
			Assert.assertEquals(i, ita.index,0.001);
			Assert.assertArrayEquals(expectPos2[i], ita.getPos());
			i++;
		}
	}
	/**
	 * Testing my second implementation of hasNext() for MyStrideIterator
	 * (Second test)	
	 */
	@Test
	public void testHasNext2Again(){
		MyStrideIterator ita = new MyStrideIterator(new int[]{2,3,2}, new int[]{6,1,3}, new int[]{0,2,1});
		int i=0;
		while(ita.hasNext2()){
			Assert.assertEquals(i, ita.index,0.001);
			Assert.assertArrayEquals(expectPos2[i], ita.getPos());
			i++;
		}
	}
	/**
	 * Testing the reset method in MyStrideIterator
	 * Should reset the pos and index fields so the iterator can be used again
	 * (first test)
	 */
	@Test
	public void testReset(){
		MyStrideIterator ita1 = new MyStrideIterator(new int[]{2,2,3}, new int[]{6,3,1}, new int[]{1,2,0});
		
		for(int i=0;i<5;i++){
			ita1.hasNext();
		}
		ita1.reset();
		Assert.assertArrayEquals(expectResetPos1, ita1.getPos());
		Assert.assertEquals(expectResetIndex1, ita1.index);
		
		MyStrideIterator ita2 = new MyStrideIterator(new int[]{2,3,2}, new int[]{6,1,3}, new int[]{0,2,1});
		
		for(int i=0;i<5;i++){
			ita2.hasNext();
		}
		ita2.reset();
		Assert.assertArrayEquals(expectResetPos2, ita2.getPos());
		Assert.assertEquals(expectResetIndex2, ita2.index);
	}
	/**
	 * Testing the reset method in MyStrideIterator
	 * Should reset the pos and index fields so the iterator can be used again
	 * (second test)
	 */
	@Test
	public void testReset2(){
		MyStrideIterator ita1 = new MyStrideIterator(new int[]{2,2,3}, new int[]{6,3,1}, new int[]{1,2,0});
		
		for(int i=0;i<5;i++){
			ita1.hasNext2();
		}
		ita1.reset();
		Assert.assertArrayEquals(expectResetPos1, ita1.getPos());
		Assert.assertEquals(expectResetIndex1, ita1.index);
		
		MyStrideIterator ita2 = new MyStrideIterator(new int[]{2,3,2}, new int[]{6,1,3}, new int[]{0,2,1});
		
		for(int i=0;i<5;i++){
			ita2.hasNext();
		}
		ita2.reset();
		Assert.assertArrayEquals(expectResetPos2, ita2.getPos());
		Assert.assertEquals(expectResetIndex2, ita2.index);
	}
	/**
	 * testing MyStrideIterator constructor
	 */
	@Test
	public void testConstructor(){
		MyStrideIterator ita = new MyStrideIterator(new int[]{2,3,2}, new int[]{6,1,3}, new int[]{0,2,1});
		Assert.assertArrayEquals(expectStride, ita.getStride());
		Assert.assertArrayEquals(expectDelta, ita.getDelta());
		Assert.assertArrayEquals(expectShape, ita.getShape());
		Assert.assertArrayEquals(expectStrideOrder, ita.getStrideOrder());
		Assert.assertEquals(expectRank, ita.getRank());
		
	}
	
}
