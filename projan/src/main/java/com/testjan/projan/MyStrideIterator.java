package com.testjan.projan;

import java.util.Arrays;

/**
 * Class to provide iteration through a dataset using strides
 *
 */
public class MyStrideIterator {

	private int[] stride;
	private int[] delta;
	private int[] shape;
	private int endrank;
	private int[] pos;
	private int[] strideOrder;
	public int index;

	/**
	 * Constructor for a MyStrideIterator
	 * 
	 * @param shape
	 *            : the shape of the dataset
	 * @param strides
	 *            : strides of the dataset
	 * @param theStrideOrder
	 *            : iterates through the strides in this order - where the first
	 *            axes given is the 'outer' loop and the last axis will be the
	 *            'inner' loop i.e. the last axis is incremented first
	 */
	public MyStrideIterator(final int[] shape, final int[] strides, final int[] theStrideOrder) {
		this.shape = shape;
		int rank = shape.length;
		endrank = rank - 1;
		pos = new int[rank];
		delta = new int[rank];
		stride = strides;
		for (int j = endrank; j >= 0; j--) {
			delta[j] = stride[j] * shape[j];
		}
		strideOrder = theStrideOrder;
		reset();
	}

	/**
	 * 
	 * @return shape of dataset
	 */
	public int[] getShape() {
		return shape;
	}
	/**
	 * 
	 * @return the strides of the corresponding dataset
	 */
	public int[] getStride(){
		return stride;
	}
	/**
	 * 
	 * @return the deltas of the corresponding dataset
	 */
	public int[] getDelta(){
		return delta;
	}
	/**
	 * 
	 * @return the order in which to iterate through the strides of the corresponding dataset
	 */
	public int[] getStrideOrder(){
		return strideOrder;
	}
	/**
	 * 
	 * @return the rank of the corresponding dataset
	 */
	public int getRank(){
		return endrank + 1;
	}
	
	
	
	/**
	 * 
	 * @return current position in dataset
	 */
	public int[] getPos() {
		return pos;
	}

	/**
	 * Returns true is there is another iteration, and moves onto the next
	 * Position
	 * Runs about 50% faster than hasNext2()
	 * 
	 * @return
	 */
	public boolean hasNext() {
		// now move on one position
		int j = endrank;
		for (; j >= 0; j--) {
			int axis = strideOrder[j];
			index += stride[axis];
			final int p = pos[axis] + 1;
			if (p < shape[axis]) {
				pos[axis] = p;
				break;
			}
			pos[axis] = 0;
			index -= delta[axis]; // reset this dimension
			 			
			
		}
		return j >= 0;
	}
	
	/**
	 * Returns true is there is another iteration, and moves onto the next
	 * Position
	 * Runs slower than hasNext()
	 * @return
	 */
	public boolean hasNext2(){
		int j = endrank;
		for (; j >= 0; j--) {
			
			int axis = strideOrder[j];
			int p = pos[axis] + 1 ;//running slow?, spends ~90% of time on this line
			if (p < shape[axis]) {
				pos[axis] = p;
				break;
			}
			pos[axis] = 0;
		}
		
		index = 0;
		for(int i=0;i<=endrank;i++){
			index += pos[i]*stride[i];
		}
		
		return j >= 0;
	}

	/**
	 * Resets the iterator position and index
	 */
	public void reset() {
		Arrays.fill(pos, 0);
		int thisStride = strideOrder[endrank];
		pos[thisStride] = -1;
		index = -1 * stride[thisStride];
	}
}