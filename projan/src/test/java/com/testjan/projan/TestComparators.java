package com.testjan.projan;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the comparators used to order the axes based on the strides of the tensor
 * Junit tests for StrideSort, StrideSortTwo and StrideSortLong comparators
 *
 */
public class TestComparators {
	static int[] refArray = new int[]{40,25,70,80,33};//the reference array
	static long[] refArrayLong = new long[]{40,25,70,80,33};
	static int[] firstArray = new int[]{2,4,3,1};//sort first Array with reference to refArray
	static int[] secondArray = new int[]{4,3,0,1};//sort secodnArray based on what elements were swapped in firstArray
	//i.e.: if swap the 2rd and 3rd element in first array, swap them in the second array and so on
	
	final int[] firstArrayExpect = new int[]{3,2,4,1};
	final int[] secondArrayExpect = new int[]{0,4,3,1};
	
	
	/**
	 * Tests whether the firstArray is sorted correctly
	 */
	@Test
	public void testStrideSort(){
		Integer[] firstArrayObj = ArrayUtils.toObject(firstArray);
		Collections.sort(Arrays.asList(firstArrayObj),new StrideSort(refArray));
		Assert.assertArrayEquals(firstArrayExpect, ArrayUtils.toPrimitive(firstArrayObj));
	}
	
	/**
	 * Tests whether the firstArray is sorted correctly
	 */
	@Test
	public void testStrideSortLong(){
		Integer[] firstArrayObj = ArrayUtils.toObject(firstArray);
		Collections.sort(Arrays.asList(firstArrayObj),new StrideSortLong(refArrayLong));
		Assert.assertArrayEquals(firstArrayExpect, ArrayUtils.toPrimitive(firstArrayObj));
	}
	
	
	/**
	 * Tests whether the secondArray is sorted correctly
	 */
	@Test
	public void testStrideSortTwo(){
		Integer[] secondArrayObj = ArrayUtils.toObject(secondArray);
		Collections.sort(Arrays.asList(secondArrayObj),new StrideSortTwo(refArray,firstArray,secondArray));
		Assert.assertArrayEquals(secondArrayExpect, ArrayUtils.toPrimitive(secondArrayObj));
	}
}
