package com.testjan.projan;

import java.util.Arrays;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.PositionIterator;

/**
 * Contains a method for the addition of two tensors of the same shape
 *
 */
public class Addition {
	
	/**
	 * Adding two datasets of the same shape a and b
	 * If the datasets aren't the same shape, throws an IllegalArgumentException
	 * @param a
	 * @param b
	 * @return
	 */
	public static Dataset add(Dataset a, Dataset b){
		if(!Arrays.equals(a.getShape(), b.getShape())){
			throw new IllegalArgumentException("Tensors to add must have the same shape");
		}else{
			//creating a result Dataset
			Dataset result = DatasetFactory.zeros(a.getShape());
			PositionIterator ita = a.getPositionIterator(null);//iterating through all three tensors in the same way, so only need one iterator
			final int[] apos = ita.getPos();
			while(ita.hasNext()){
				result.set(a.getDouble(apos) + b.getDouble(apos), apos);
			}
			return result;
		}
	}
} 
