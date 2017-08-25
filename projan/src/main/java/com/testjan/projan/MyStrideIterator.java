package com.testjan.projan;

import java.util.Arrays;

/**
 * Class to provide iteration through a dataset using strides
 * Has an option of specifying the order through which to iterate
 */
public class MyStrideIterator {

	private int[] stride;// strides of the dataset
	private int[] delta;// stride*shape - used to reset index in hasNext()
	private int[] shape;// shape of the dataset
	private int endrank;// rank of the dataset - 1
	private int[] pos;// current position in dataset
	private int[] strideOrder;// order in which to iterate through the strides,
								// first stride is outer loop and last stride is
								// the inner loop - i.e. the first to be
								// incremented
	public int index;// current 1D index position in dataset

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
	 * @return shape
	 */
	public int[] getShape() {
		return shape;
	}

	/**
	 * 
	 * @return stride
	 */
	public int[] getStride() {
		return stride;
	}

	/**
	 * 
	 * @return delta
	 */
	public int[] getDelta() {
		return delta;
	}

	/**
	 * 
	 * @return strideOrder
	 */
	public int[] getStrideOrder() {
		return strideOrder;
	}

	/**
	 * 
	 * @return the rank of the corresponding dataset
	 */
	public int getRank() {
		return endrank + 1;
	}

	/**
	 * 
	 * @return pos
	 */
	public int[] getPos() {
		return pos;
	}

	/**
	 * Runs about 50% faster than hasNext2()
	 * 
	 * @return true is there is another iteration, and moves onto the next
	 *         position. Otherwise false.
	 */
	public boolean hasNext() {
		// now move on one position
		int j = endrank;
		for (; j >= 0; j--) {
			int axis = strideOrder[j];//the axis to increment
			index += stride[axis];
			final int p = pos[axis] + 1;
			if (p < shape[axis]) {
				pos[axis] = p;//incrementing the position
				break;
			}// Otherwise need to reset this dimension
			pos[axis] = 0;
			index -= delta[axis]; //moving index back to start of this dimension

		}
		return j >= 0;
	}

	/**
	 * Runs slower than hasNext()
	 * Doesn't used delta
	 * @return true is there is another iteration, and moves onto the next
	 *         Position. Otherwise false.
	 */
	public boolean hasNext2() {
		int j = endrank;
		for (; j >= 0; j--) {
			int axis = strideOrder[j];//the axis to increment
			int p = pos[axis] + 1;
			if (p < shape[axis]) {
				pos[axis] = p;//incrementing the position
				break;
			}//Otherwise need to reset this dimension
			pos[axis] = 0;
		}
		//resetting the index
		index = 0;
		//calculting the 1D index from the current position
		for (int i = 0; i <= endrank; i++) {
			index += pos[i] * stride[i];
		}

		return j >= 0;
	}

	/**
	 * Resets the iterator position and index All elements of pos are set to 0
	 * apart from the first element to be incremented in hasNext() which is set
	 * to -1.
	 * 
	 */
	public void reset() {
		Arrays.fill(pos, 0);
		int thisStride = strideOrder[endrank];
		pos[thisStride] = -1;
		//the first call of hasNext() will increment index by +stride[thisStride]
		index = -1 * stride[thisStride];
	}
}
