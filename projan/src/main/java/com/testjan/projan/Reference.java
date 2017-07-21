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
	
	//TODO: delete this, used to see why this is making code run slow
	public static int swapRows(int[] aaxes,int[] baxes,int[] subbstride){
		//simple bubble sort,ordering with respect to subbstride
		for(int i=0;i<subbstride.length-1;i++){
			for(int j=0;j<subbstride.length-1;j++){
				if(subbstride[j]<subbstride[j+1]){
					//swapping the stride values
					int temp = subbstride[j];
					subbstride[j]=subbstride[j+1];
					subbstride[j+1]=temp;
					//swapping the a axes values
					temp = aaxes[j];
					aaxes[j]=aaxes[j+1];
					aaxes[j+1]=temp;
					//swapping the b axes values
					temp = baxes[j];
					baxes[j]=baxes[j+1];
					baxes[j+1]=temp;
				}
			}
		}
		
		
		
		return aaxes[0]+baxes[0]+subbstride[0];
	}
}
