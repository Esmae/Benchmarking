package com.testjan.projan;
/**
 * Comparator that sorts based on the elements in refArray
 */
import java.util.Comparator;


public class StrideSort implements Comparator<Integer>{
	
	//make sure no methods in this class change this array
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
	 */
	@Override
	public int compare(Integer o1, Integer o2) {
		return refArray[o2]-refArray[o1];
	}
	
}
 