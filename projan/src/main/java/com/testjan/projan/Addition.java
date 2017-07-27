package com.testjan.projan;

import java.util.Arrays;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.PositionIterator;

/**
 * Contains a method for the addition of two tensors of the same shape
 *
 */
public class Addition {
	public static Dataset add(Dataset a, Dataset b){
		if(!Arrays.equals(a.getShape(), b.getShape())){
			throw new IllegalArgumentException("Tensors to add must have the same shape");
		}else{
			//creating a result Dataset
			Dataset result = DatasetFactory.zeros(a.getShape());
			PositionIterator ita = a.getPositionIterator(null);
			PositionIterator itb = b.getPositionIterator(null);
			PositionIterator itr = result.getPositionIterator(null);
			final int[] apos = ita.getPos();//as the tensors have the same shape apos=bpos=rpos so are all three necessary?
			final int[] bpos = itb.getPos();
			final int[] rpos = itr.getPos();
			while(ita.hasNext() && itb.hasNext() && itr.hasNext()){
				result.set(a.getDouble(apos) + b.getDouble(bpos), rpos);
			}
			return result;
		}
	}
	
	public static void main(String[] args){
		Dataset a = DatasetFactory.createRange(24);
		a = a.reshape(2,3,4);
		/*PositionIterator ita = a.getPositionIterator(null);
		final int[] apos = ita.getPos();
		System.out.println(Arrays.toString(apos));
		while(ita.hasNext()){
			System.out.println(Arrays.toString(apos));
		}*/
		
		Dataset add = Maths.add(a, a);
		System.out.println(add.toString(true));
	
		
		
		
		/*int[] ashape = a.getShape();
		System.out.println(Arrays.toString(ashape)+"\n");
		System.out.println(a.toString(true)+"\n");
		
		
		Dataset b = a.getTransposedView(1,0,2);
		int[] bshape = b.getShape();
		System.out.println(b.toString(true)+"\n");
		System.out.println(Arrays.toString(bshape)+"\n");
		bshape[0]=2;
		bshape[1]=3;
		
		System.out.println(Arrays.equals(ashape, bshape));*/

	}
} 
