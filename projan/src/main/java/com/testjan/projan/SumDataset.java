package com.testjan.projan;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.january.dataset.AbstractDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IndexIterator;

/**
 * Class for testing the summation of values in a dataset   
 * using different iterators
 *
 */
public class SumDataset {
	/**
	 * summing using the IndexIterator in project january
	 * @param a: the dataset
	 * @return
	 */
	public static double sumOrigIterator(Dataset a){
		double sum = 0;
		IndexIterator ita = a.getIterator();
		while(ita.hasNext()){
			sum += a.getElementDoubleAbs(ita.index);
		}
		return sum;
	}
	/**
	 * summing using MyStrideIterator: based on the StrideIterator in project January
	 * summs along the strides in a more optimal order
	 * @param a: the dataset
	 * @return
	 */
	public static double sumMyStrideIterator(Dataset a){
		double sum = 0;
		int[] shape = a.getShape();
		//getting the strides of the dataSets
		int[] offset = new int[1];
		final int[] astride= AbstractDataset.createStrides(a, offset);
		//want to order based on the strides of a
		int rank = a.getRank();
		int[] aaxes = new int[rank];
		for(int i=0;i<rank;i++){
			aaxes[i] = i;
		}
		Integer[] aaxesobj = ArrayUtils.toObject(aaxes);
		Collections.sort(Arrays.asList(aaxesobj),new StrideSort(astride));//sorts aaxes
		aaxes = ArrayUtils.toPrimitive(aaxesobj);
		//creating the strideIterator
		MyStrideIterator ita = new MyStrideIterator(shape, astride,aaxes);
		while(ita.hasNext()){
			sum += a.getElementDoubleAbs(ita.index);
		}
		return sum;
	}
}
