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
	
	//initialise a 10,000 element 1D dataset
	public Reference(double size){
		refDataset = DatasetFactory.createRange(size);
		for(int i=0;i<size;i++){
			refDataset.set(randGenerator.nextDouble(), i);
		}
	}
	
	//sums up all the elements in the dataset
	public double addNum(double size){
		double sum = 0;
		for(int i=0; i<size;i++){
			sum += refDataset.getDouble(i);
		}
		return sum;
	}
}
