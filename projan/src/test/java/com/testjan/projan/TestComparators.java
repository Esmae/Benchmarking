package com.testjan.projan;
/**
 * Test the comparators used to order the axes based on the strides of the tensor
 */
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestComparators {
	static int[] refArray;//the reference array
	static int[] firstArray;//sort first Array with reference to refArray
	static int[] secondArray;//sort secodnArray based on what elements were swapped in firstArray
	//i.e.: if swap the 2rd and 3rd element in first array, swap them in the second array and so on
	
	final int[] firstArrayExpect = new int[]{3,2,4,1};
	final int[] secondArrayExpect = new int[]{0,4,3,1};
	
	/**
	 * Sets up the test case arrays
	 */
	@BeforeClass
	public static void setUpClass(){
		refArray = new int[]{40,25,70,80,33};
		firstArray = new int[]{2,4,3,1};
		secondArray = new int[]{4,3,0,1};
	}
	
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
	 * Test whether the secondArray is sorted correctly
	 */
	@Test
	public void testStrideSortTwo(){
		Integer[] secondArrayObj = ArrayUtils.toObject(secondArray);
		Collections.sort(Arrays.asList(secondArrayObj),new StrideSortTwo(refArray,firstArray,secondArray));
		Assert.assertArrayEquals(secondArrayExpect, ArrayUtils.toPrimitive(secondArrayObj));
	}
}
