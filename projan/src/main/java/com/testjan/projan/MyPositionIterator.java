package com.testjan.projan;
/**
 * Class to provide iteration through a dataset 
 *
 */

public class MyPositionIterator {
	final private int[] shape;//shape of the dataset
	final private int[] axesOrder;// first axis given will be the outer loop,
									// the last axis given will be the inner
									// loop
	final private int[] pos;	//the current position
	final private int endrank;	// rank - 1 
	

	/**
	 * Constructor for a Position Iterator
	 * @param theShape - shape of the dataset
	 */
	public MyPositionIterator(int[] theShape){
		int[] theAxesOrder = new int[theShape.length];
		for(int i=0;i<theAxesOrder.length;i++){
			//default axesOrder is 0,1,2...
			theAxesOrder[i] = i;
		}
		endrank = theShape.length - 1;
		shape = theShape;
		axesOrder = theAxesOrder;
		pos = new int[endrank + 1];
		reset();
	}
	
	
	/**
	 * Constructor for a Position Iterator that iterates through the axes using
	 * theAxesOrder, where the first axis given will be the outer loop and the
	 * last axis given will be the inner loop
	 * 
	 * Throws an IllegalArgumentException if the axes don't match the shape of
	 * the dataset
	 * 
	 * @param theShape -shape of the dataset
	 * @param theAxesOrder - the order to iterate the axes through
	 */
	public MyPositionIterator(int[] theShape, int[] theAxesOrder) {
		if (theShape.length != theAxesOrder.length) {
			throw new IllegalArgumentException("The Axes given don't match the shape of the dataset");
		}
		endrank = theShape.length - 1;
		shape = theShape;
		axesOrder = theAxesOrder;
		pos = new int[endrank + 1];
		reset();
	}

	/**
	 * 
	 * @return : true if there is another iteration, and moves onto the next
	 * Position
	 */
	public boolean hasNext() {
		// increments the last axis given in axesOrder first
		for (int j = endrank; j >= 0; j--) {
			int axis = axesOrder[j];
			pos[axis] += 1;
			if (pos[axis] >= shape[axis]) {
				pos[axis] = 0;
				// now need to increment the next axis along (if there is one)
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Resets the iterator position so it can be used again. All elements of pos
	 * are set to zero apart from the element which will be first incremented in
	 * hasNext which is set to -1
	 */
	public void reset() {
		// resets the iterator
		for (int i = 0; i < endrank + 1; i++) {
			pos[i] = 0;
		}
		pos[axesOrder[endrank]] = -1;

	}
	/**
	 * @return shape
	 */
	public int[] getShape() {
		return shape;
	}
	/**
	 * @return axesOrder 
	 */
	public int[] getAxesOrder() {
		return axesOrder;
	}
	/**
	 * @return pos 
	 */
	public int[] getPos() {
		return pos;
	}
	/**
	 * @return rank of the dataset
	 */
	public int getRank() {
		return endrank + 1;
	}

}
