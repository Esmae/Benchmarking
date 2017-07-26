package com.testjan.projan;
/**
 * Sorts an array based on how refArray2 was sorted with reference to refArray1
 * i.e. if element in position 2 moves to position 0 in refArray2 when sorted, then element in position 2 must move to position 0 in the array to be sorted
 * See StrideSort for how refArray1 should be sorted
 */

import java.util.Comparator;

import org.apache.commons.lang.ArrayUtils;

public class StrideSortTwo implements Comparator<Integer> {

	// make sure no method in this class changes these arrays
	private final int[] refArray1;
	private final int[] refArray2;
	private final int[] toSort;

	/**
	 * 
	 * @param arrayRef1
	 *            : the original reference array
	 * @param arrayRef2
	 *            : this array is 'sorted' based on arrayRef1
	 * @param unSorted
	 *            : this array is 'sorted' based on how the elements on
	 *            arrayRef2 moved when it was sorted
	 */
	public StrideSortTwo(int[] arrayRef1, int[] arrayRef2, int[] unSorted) {
		refArray1 = arrayRef1;
		refArray2 = arrayRef2;
		toSort = unSorted;
	}

	/**
	 * returns: -ve: if o1 element should come before o2 element 
	 * 0: if the ordering of the elements doesn't matter (this doesn't mean the elements
	 * are the same), as the elements are axes, they won't be - it just means
	 * the strides of the axes are the same 
	 * +ve: if o1 element should come after o2 element 
	 */
	public int compare(Integer o1, Integer o2) {
		// getting the indexes of the elements in the array
		int index1 = ArrayUtils.indexOf(toSort, o1);
		int index2 = ArrayUtils.indexOf(toSort, o2);
		// seeing how the elements in those indexes in refArray2 were sorted
		// based on arrayRef1
		return compareTwo(index1, index2);
	}

	/**
	 * seeing how the elements in those indexes in refArray2 were sorted based
	 * on arrayRef1
	 * 
	 * @param i1
	 * @param i2
	 * @return
	 */
	public int compareTwo(int i1, int i2) {
		return refArray1[refArray2[i2]] - refArray1[refArray2[i1]];
	}
}
