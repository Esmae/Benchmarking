package com.testjan.projan;

import java.util.Comparator;

import org.apache.commons.lang.ArrayUtils;

/**
 * Sorts an array based on how the reference array was sorted into descending
 * order. e.g. if the 2nd element in the reference array before sorting is the
 * 4th element after sorting, then the 2nd element is moved to the 4th element
 * in toSort as well.
 *
 */
public class ReverseSortTwo implements Comparator<Integer> {

	private final long[] refArray;// the reference array
	private final int[] toSort;// the array to be sorted

	/**
	 * Constructor for ReverseSortTwo Comparator
	 * 
	 * @param strideMult
	 *            - the reference array
	 * @param unSorted
	 *            - the array to be sorted, musn't have duplicate elements.
	 *            Doesn't in this package as only used for ordering axes.
	 */
	public ReverseSortTwo(long[] strideMult, int[] unSorted) {
		refArray = strideMult;
		toSort = unSorted;
	}

	/**
	 * Determines index of the Integers in toSort - sorts based on how these
	 * positions moved in refArray when sorted in descending order
	 * @param o1 - first Object to be compared
	 * @param o2 - second Object to be compared
	 */
	@Override
	public int compare(Integer o1, Integer o2) {
		int index1 = ArrayUtils.indexOf(toSort, o1);
		int index2 = ArrayUtils.indexOf(toSort, o2);

		return compareTwo(index1, index2);
	}

	/**
	 * Compares two elements in refArray
	 * @param i1 - index of the first element(long) to compare
	 * @param i2 - index of the second element(long) to compare
	 * @return +ve if first element<second element, -ve if first element>second element, 0 if first element=second element
	 */
	private int compareTwo(int i1, int i2) {
		return (int) (refArray[i2] - refArray[i1]);
	}
}
