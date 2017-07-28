package com.testjan.projan;
/*
 * used to create some reference benchmarking data
 * creates Dataset of a size given by the parameter passed to the constructor
 * Puts random numbers in the Dataset then sums the numbers
 */

import java.util.Random;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

public class Reference {

	private static Random randGenerator = new Random(9658);
	private Dataset refDataset;
	private double size;
	
	/**
	 * initialise a 1D dataset with size number of elements
	 * @param theSize
	 */
	public Reference(double theSize){
		refDataset = DatasetFactory.createRange(theSize);
		for(int i=0;i<theSize;i++){
			refDataset.set(randGenerator.nextDouble(), i);
		}
		size = theSize;
		
	}
	
	/**
	 * sums up all the elements in the dataset
	 * @return the sum
	 */
	public double addNum(){
		double sum = 0;
		for(int i=0; i<size;i++){
			sum += refDataset.getDouble(i);
		}
		return sum;
	}
}
