package com.testjan.projan;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Testing my implementation of a positionIterator which is based on the orignal
 * PositionIterator but iterates through the axes in a given order
 */ 
public class TestMyPosIterator {
	
	private static MyPositionIterator ita = new MyPositionIterator(new int[]{2,2,3},new int[]{2,0,1});
	private static int[] expectShape = {2,2,3};
	private static int[] expectAxesOrder1 = {0,1,2};
	private static int[] expectAxesOrder2 = {2,0,1};
	private static int[] expectPos1 = {0,0,-1};
	private static int[] expectPos2 = {0,-1,0};
	private static int expectRank = 3;
	private static int[][] positions = {{0,0,0},{0,1,0},{1,0,0},{1,1,0},{0,0,1},{0,1,1},{1,0,1},{1,1,1},{0,0,2},{0,1,2},{1,0,2},{1,1,2}};
	 
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * Testing a MyPositionIterator constructor
	 */
	@Test
	public void testConstructor1(){
		MyPositionIterator itaC = new MyPositionIterator(new int[]{2,2,3});
		Assert.assertArrayEquals(expectShape,itaC.getShape());//testing the shape
		Assert.assertArrayEquals(expectAxesOrder1,itaC.getAxesOrder());//testing the axesorder
		Assert.assertArrayEquals(expectPos1,itaC.getPos());//testing the position
		Assert.assertEquals(expectRank,itaC.getRank());//testing the shape
	}
	
	
	
	
	/**
	 * Testing a MyPositionIterator constructor
	 */
	@Test
	public void testConstructor2(){
		MyPositionIterator itaC = new MyPositionIterator(new int[]{2,2,3},new int[]{2,0,1});
		Assert.assertArrayEquals(expectShape,itaC.getShape());//testing the shape
		Assert.assertArrayEquals(expectAxesOrder2,itaC.getAxesOrder());//testing the axesorder
		Assert.assertArrayEquals(expectPos2,itaC.getPos());//testing the position
		Assert.assertEquals(expectRank,itaC.getRank());//testing the shape
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The Axes given don't match the shape of the dataset");
		MyPositionIterator itaF = new MyPositionIterator(new int[]{2,2,2,2},new int[]{2,2});
		//here so itaF is 'used' so not optimized out
		itaF.hasNext();
	}
	
	/**
	 * Testing MyPositionIterator hasNext()
	 */
	@Test
	public void testHasNext(){
		int i = 0;
		while(ita.hasNext()){
			Assert.assertArrayEquals(positions[i],ita.getPos());//testing the position
			i++;
		}
	}
	
	/**
	 * Testing MyPositionIterator reset()
	 */
	@Test
	public void testReset(){
		MyPositionIterator itaR = new MyPositionIterator(new int[]{2,2,3},new int[]{2,0,1});
		//changing pos
		for(int i=0;i<6;i++){
			itaR.hasNext();
		}
		itaR.reset();//should reset the position
		Assert.assertArrayEquals(expectPos2, itaR.getPos());
	}
} 
