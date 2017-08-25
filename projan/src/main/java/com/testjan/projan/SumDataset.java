package com.testjan.projan;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.january.dataset.AbstractDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IndexIterator;

/**
 * Class for testing the summation of values in a dataset using different
 * iterators
 *
 */
public class SumDataset {

	/**
	 * Summing using the IndexIterator in Project January
	 * 
	 * @param a:
	 *            the dataset
	 * @return the sum
	 */
	public static double sumOrigIterator(Dataset a) {
		double sum = 0;
		IndexIterator ita = a.getIterator(true);//true: will change the position as well as the memory index for untransposed datasets
		while (ita.hasNext()) {
			sum += a.getElementDoubleAbs(ita.index);
		}
		return sum;
	}

	/**
	 * Summing using MyStrideIterator: based on the StrideIterator in project
	 * January Summs along the strides so increments axes with smallest stride
	 * first Uses hasNext() in MyStrideIterator
	 * 
	 * @param a:
	 *            the dataset
	 * @return the sum
	 */
	public static double sumMyStrideIterator(Dataset a) {
		double sum = 0;
		int[] shape = a.getShape();
		// getting the strides of the dataSets
		int[] offset = new int[1];
		final int[] astride = AbstractDataset.createStrides(a, offset);
		// creating aaxes array: 0,1,2...
		int rank = shape.length;
		int[] aaxes = new int[rank];
		for (int i = 0; i < rank; i++) {
			aaxes[i] = i;
		}

		// want to order based on the strides of a
		Integer[] aaxesobj = ArrayUtils.toObject(aaxes);
		List<Integer> aList = Arrays.asList(aaxesobj);
		Collections.sort(Arrays.asList(aaxesobj), new StrideSort(astride));// sorts
																			// aaxes
		aaxesobj = (Integer[]) aList.toArray();
		aaxes = ArrayUtils.toPrimitive(aaxesobj);
		// creating the strideIterator
		MyStrideIterator ita = new MyStrideIterator(shape, astride, aaxes);
		while (ita.hasNext()) {
			sum += a.getElementDoubleAbs(ita.index);
		}
		return sum;
	}

	/**
	 * Summing using MyStrideIterator: based on the StrideIterator in project
	 * January summs along the strides so increments axes with smallest stride
	 * first Uses hasNext2() in MyStrideIterator
	 * 
	 * @param a:
	 *            the dataset
	 * @return the sum
	 */
	public static double sumMyStrideIterator2(Dataset a) {
		double sum = 0;
		int[] shape = a.getShape();
		// getting the strides of the dataSets
		int[] offset = new int[1];
		final int[] astride = AbstractDataset.createStrides(a, offset);
		// creating aaxes array: 0,1,2..
		int rank = shape.length;
		int[] aaxes = new int[rank];
		for (int i = 0; i < rank; i++) {
			aaxes[i] = i;
		}
		// want to order based on the strides of a
		Integer[] aaxesobj = ArrayUtils.toObject(aaxes);
		List<Integer> aList = Arrays.asList(aaxesobj);
		Collections.sort(Arrays.asList(aaxesobj), new StrideSort(astride));// sorts
																			// aaxes
		aaxesobj = (Integer[]) aList.toArray();
		aaxes = ArrayUtils.toPrimitive(aaxesobj);
		// creating the strideIterator
		MyStrideIterator ita = new MyStrideIterator(shape, astride, aaxes);
		while (ita.hasNext2()) {
			sum += a.getElementDoubleAbs(ita.index);
		}
		return sum;
	}

	/**
	 * Sums the elements in the dataset in index order Doesn't use an iterator
	 * object
	 * 
	 * @param a:
	 *            the dataset
	 * @return the sum
	 */
	public static double sumMyIndexIterator(Dataset a) {
		double sum = 0;
		int size = a.getSize();
		int index = 0;
		while (index < size) {
			sum += a.getElementDoubleAbs(index);
			index++; // increments the 1DIndex
		}
		return sum;
	}

}
