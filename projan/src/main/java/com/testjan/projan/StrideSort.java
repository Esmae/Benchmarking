package com.testjan.projan;
/**
 * Comparator that sorts based on the elements in refArray
 */
import java.util.Comparator;


public class StrideSort implements Comparator<Integer>{
	
	//make sure no methods in this class change this array
	private int[] refArray;
	
	public StrideSort(int[] arrayRef){
		refArray = arrayRef;
	}
	
	
	//want the element that corresponds to the largest element in refArray to be first
	@Override
	public int compare(Integer o1, Integer o2) {
		return refArray[o2]-refArray[o1];
	}
	
}
 