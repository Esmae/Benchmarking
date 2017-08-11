package com.testjan.projan;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import org.apache.commons.lang.ArrayUtils;
import org.eclipse.january.dataset.AbstractDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.PositionIterator;

/**
 * Contains methods for the addition of two tensors of the same shape
 * add - uses PositionIterator in Project January
 * myAddPartOne - uses MyPositionIterator, sorts based on strides of first dataset
 * myAdd2PartOne - uses MyPositionIterator, sorts based on strides of both datasets
 *
 */
public class Addition {
	
	/**
	 * Adding two datasets of the same shape a and b
	 * Uses PositionIterator in Project January
	 * If the datasets aren't the same shape, throws an IllegalArgumentException
	 * @param a - The first dataset
	 * @param b - The second dataset
	 * @return
	 */
	public static Dataset add(Dataset a, Dataset b){
		if(!Arrays.equals(a.getShape(), b.getShape())){
			throw new IllegalArgumentException("Tensors to add must have the same shape");
		}else{
			//creating a result Dataset
			Dataset result = DatasetFactory.zeros(a.getShape());
			PositionIterator ita = a.getPositionIterator();
			final int[] apos = ita.getPos();
			while(ita.hasNext()){
				result.set(a.getDouble(apos) + b.getDouble(apos), apos);
			}
			return result;
		}
	}
	
	/**
	 * Adding two datasets of the same shape a and b, iterates through axes based on the strides of the first Dataset
	 * If the datasets aren't the same shape, throws an IllegalArgumentException
	 * @param a - The first dataset
	 * @param b - The second dataset
	 * @return
	 */
	public static Dataset myAddPartOne(Dataset a, Dataset b){
		if(!Arrays.equals(a.getShape(), b.getShape())){
			throw new IllegalArgumentException("Tensors to add must have the same shape");
		}else{
			
			//want to order based on the strides of a
			int[] aoffset = new int[1];
			final int[] astride= AbstractDataset.createStrides(a, aoffset); //getting the strides of a
			
			
			int[] aaxes = new int[a.getRank()];
			//initialising aaxes
			for(int i=0;i<a.getRank();i++){
				aaxes[i]=i;
			}
			
			aaxes = sortOne(aaxes, astride);//sorts aaxes based on the strides of a
			return myAddPartTwo(a ,b ,aaxes);
		}
	}
	
	/**
	 * Adding two datasets of the same shape a and b, iterates through axes based on their strides
	 * If the datasets aren't the same shape, throws an IllegalArgumentException
	 * Unlike myAdd, compares the strides of the two datasets to see how to order the strides 
	 * rather than just ordering based on the strides of a
	 * @param a -the first dataset
	 * @param b - the second dataset
	 * @return
	 */
	public static Dataset myAdd2PartOne(Dataset a, Dataset b){
		if(!Arrays.equals(a.getShape(), b.getShape())){
			throw new IllegalArgumentException("Tensors to add must have the same shape");
		}else{
			
			//getting the strides of the two datasets
			int[] aoffset = new int[1];
			final int[] astride= AbstractDataset.createStrides(a, aoffset);
			int[] boffset = new int[1];
			final int[] bstride= AbstractDataset.createStrides(b, boffset);
			
			//'multiplying' the strides from the two datasets
			final long[] strideMult = new long[astride.length];
			for(int i=0;i<strideMult.length;i++){
				strideMult[i] = astride[i]*(long)bstride[i]; 
			}
			
			int[] aaxes = new int[a.getRank()];
			//initialising aaxes
			for(int i=0;i<a.getRank();i++){
				aaxes[i]=i;
			}
			aaxes = sortTwo(aaxes, strideMult);//sorts aaxes based on strideMult
			return myAddPartTwo(a,b,aaxes);
		}
	}
	
	
	
	/**
	 *  Adds together two datasets - iterates in order given by axes
	 *  Called by myAddPartOne and myAdd2PartOne
	 * @param a - the first dataset
	 * @param b - the second dataset
	 * @param axes - the order in which to iterate through the axes
	 * @return
	 */
	private static Dataset myAddPartTwo(Dataset a, Dataset b, int[] axes){
		//creating a result Dataset
		Dataset result = DatasetFactory.zeros(a.getShape());
		MyPositionIterator ita = new MyPositionIterator(a.getShape(),axes);//iterating through all three tensors in the same way
		//so only need one iterator
		final int[] apos = ita.getPos();
		while(ita.hasNext()){
			//adding elements
			result.set(a.getDouble(apos) + b.getDouble(apos), apos);
		}
		
		return result;
		
	}
	
	
	/**
	 * Sorts using StrideSort
	 * @param axes - the axes to sort
	 * @param refStrides - the reference array which to sort by
	 * @return a sorted axes array based on refStrides
	 */
	static int[] sortOne(int[] axes, int[] refStrides){
		Integer[] aaxesobj = ArrayUtils.toObject(axes);
		List<Integer> aList = Arrays.asList(aaxesobj);
		Collections.sort(aList,new StrideSort(refStrides));//sorts aaxes
		aaxesobj = (Integer[]) aList.toArray();
		return ArrayUtils.toPrimitive(aaxesobj);
	}
	
	/**
	 * Sorts using StrideSortLong
	 * @param axes - the axes to sort
	 * @param strideMult - the reference array which to sort by
	 * @return a sorted axes array based on strideMult
	 */
	static int[] sortTwo(int[] axes, long[] strideMult){
		Integer[] aaxesobj = ArrayUtils.toObject(axes);
		List<Integer> aList = Arrays.asList(aaxesobj);
		Collections.sort(aList,new StrideSortLong(strideMult));//sorts aList
		aaxesobj = (Integer[]) aList.toArray();
		return ArrayUtils.toPrimitive(aaxesobj);	
	}
	
	


	
} 
