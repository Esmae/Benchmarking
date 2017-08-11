package com.testjan.projan;
import java.util.Comparator;

/**
 * Comparator that sorts based on the elements in refArray
 */
public class StrideSort implements Comparator<Integer>{
	
	private int[] refArray;//the reference array in which comparisons will be made when sorting
	
	/**
	 * Initialises a StrideSort Comparator Object
	 * @param arrayRef - the reference array in which comparisons will be made when sorting
	 */
	public StrideSort(int[] arrayRef){
		refArray = arrayRef;
	}
	
	
	/**
	 * Sorts the elements such that the element that corresponds to the largest element in refArray is first
	 * @param o1 - first Integer to be sorted, must be an index of refArray
	 * @param o2 - second Integer to be sorted, must be an index of refArray
	 */
	@Override
	public int compare(Integer o1, Integer o2) {
		return refArray[o2]-refArray[o1];
	}
	
}
 