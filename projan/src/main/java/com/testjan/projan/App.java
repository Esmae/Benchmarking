package com.testjan.projan;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.january.dataset.AbstractDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;

/**
 * A testing class for how to use Datasets
 *
 */
public class App {
	public static void main(String[] args) {

		Integer[] myArray = new Integer[]{3,4,2,5,1};
		Collections.sort(Arrays.asList(myArray));
		int[] array1 = ArrayUtils.toPrimitive(myArray);
		System.out.println(	Arrays.toString(array1));
		
		/*Dataset dataset1 = DatasetFactory.createRange(125);
		Dataset dataset2 = DatasetFactory.createRange(125);

		dataset1 = dataset1.reshape(5, 5, 5);
		dataset2 = dataset2.reshape(5, 5, 5);
		Dataset result = MyTensorDot.tensorDotProduct(dataset1, dataset2, new int[] { 1, 2 }, new int[] { 2, 0 });*/
		
		
		

	}
}
