package com.testjan.projan;

import java.util.Comparator;

/**
 * Orders a List in descending order
 *
 */
public class ReverseSort implements Comparator<Integer>{
	
	/**
	 * Puts into descending order
	 * @param o1 - first Integer to be compared
	 * @param o2 - second Integer to be compared
	 */
	@Override
	public int compare(Integer o1, Integer o2) {
		return o2-o1;
	}
	
}
