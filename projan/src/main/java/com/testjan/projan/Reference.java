package com.testjan.projan;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.Random;
/**
 * used to create some reference benchmarking data. Initalises a Dataset full of
 * random doubles, of a shape given by the parameter passed to the constructor
 * 
 */
public class Reference {
 
	private Dataset refDataset;
	private double size;

	/**
	 * Initialises a Dataset
	 * 
	 * @param theShape
	 *            - the shape of the dataset
	 */
	public Reference(int[] theShape) {
		Random.seed(1455);
		refDataset = Random.rand(theShape);
		size = refDataset.getSize();
	}
 
	/**
	 * Sums up all the elements in the dataset
	 * 
	 * @return the sum
	 */
	public double addNum() {
		double sum = 0;
		for (int i = 0; i < size; i++) {
			sum += refDataset.getElementDoubleAbs(i);
		}
		return sum;
	}
	
}
