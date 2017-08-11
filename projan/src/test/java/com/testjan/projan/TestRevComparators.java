package com.testjan.projan;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the Comparators ReverseSortTwo and ReverseSort
 *
 */
public class TestRevComparators {
	//variables for testing ReverseSort Comparator
	private static int[] refArray1 = new int[]{32,44,4,3};
	private final int[] expectRevSortedOne = new int[]{44,32,4,3};
	
	//variables for testing ReverseSortTwo Comparator
	private static long[] refArray2 = new long[]{32,44,4,3};
	private static int[] toSort = new int[]{2,0,6,4};
	private static int[] expectRevSortedTwo = new int[]{0,2,6,4};
	
	/**
	 * Testing ReverseSort Comparator
	 */
	@Test
	public void testRev(){
		Integer[] refArrayObj = ArrayUtils.toObject(refArray1);
		Collections.sort(Arrays.asList(refArrayObj), new ReverseSort());
		Assert.assertArrayEquals(expectRevSortedOne,ArrayUtils.toPrimitive(refArrayObj));
	}
	
	/**
	 * Testing ReverseSortTwo Comparator
	 */
	@Test
	public void testRev2(){
		Integer[] refArrayObj = ArrayUtils.toObject(toSort);
		Collections.sort(Arrays.asList(refArrayObj), new ReverseSortTwo(refArray2, toSort));
		Assert.assertArrayEquals(expectRevSortedTwo,ArrayUtils.toPrimitive(refArrayObj));
	}
	
	
}
