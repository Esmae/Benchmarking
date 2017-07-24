package com.testjan.projan;

import java.util.Comparator;


public class StrideSort implements Comparator<Integer>{
	
	//make sure no methods in this class change this array
	private int[] refArray;
	
	public StrideSort(int[] arrayRef){
		refArray = arrayRef;
	}
	
	//standard sort
	public int compare(Integer o1, Integer o2) {
		return refArray[o2]-refArray[o1];
	}
	
}
