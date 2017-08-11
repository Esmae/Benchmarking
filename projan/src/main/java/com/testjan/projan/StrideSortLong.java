package com.testjan.projan;

import java.util.Comparator;

/**
 * Sorts an array containing axes with reference to an array containing strides
 *
 */
public class StrideSortLong implements Comparator<Integer>{
	
	private long[] refArray;//the reference array in which comparisons will be made when sorting
	
	/**
	 * Initialises a StrideSortLong Comparator Object
	 * @param arrayRef - the reference array in which comparisons will be made when sorting
	 */
	public StrideSortLong(long[] arrayRef){
		refArray = arrayRef;
	}
	
	
	/**
	 * Sorts the elements such that the element that corresponds to the largest element in refArray is first
	 * @param o1 - First Object to compare
	 * @Param o2 - Second Object to compare
	 */
	@Override
	public int compare(Integer o1, Integer o2) {
		return (int) (refArray[o2]-refArray[o1]);
	}
	
}