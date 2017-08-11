package com.testjan.projan;

import java.util.Comparator;

import org.apache.commons.lang.ArrayUtils;

/**
 * Sorts an array based on how refArray2 was sorted with reference to refArray1
 * i.e. if element in position 2 moves to position 0 in refArray2 when sorted,
 * then element in position 2 must move to position 0 in the array to be sorted
 * See StrideSort for how refArray1 & 2 should be sorted
 */
public class StrideSortTwo implements Comparator<Integer> {

	private final int[] refArray1;// the reference array in which comparisons
									// are made when refArray2 is sorted
	private final int[] refArray2;
	private final int[] toSort;// the array which is sorted based on how the
								// elements in refArray2 were moved when it was
								// sorted

	/**
	 * Constructor for a StrideSortTwo Comparator
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
	 * @return -ve: if o1 element should come before o2 element 0: if the
	 *         ordering of the elements doesn't matter, as the elements are
	 *         axes,it means the strides of the axes are the same +ve: if o1
	 *         element should come after o2 element
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
	 * @param i1 - index of the first element in refArray2 to be compared, which is itself an index of refArray1
	 * @param i2 - index of the second element in refArray2 to be compared, which is itself an index of refArray1
	 * @return
	 */
	private int compareTwo(int i1, int i2) {
		return refArray1[refArray2[i2]] - refArray1[refArray2[i1]];
	}
}
