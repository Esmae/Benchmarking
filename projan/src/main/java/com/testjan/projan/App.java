package com.testjan.projan;

import org.eclipse.january.dataset.AbstractDataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;

/**
 * A testing class for how to use Datasets
 *
 */
public class App {
	public static void main(String[] args) {

		
		
		
		
		DoubleDataset dataset1 = DatasetFactory.createRange(10000000);
		dataset1.resize(100, 100, 100);

		DoubleDataset dataset2 = DatasetFactory.createRange(10000000);
		dataset2.resize(100, 100, 100);
		
		int[] myArray1 = AbstractDataset.createStrides(dataset2, new int[]{0});
		int[] myArray2 = AbstractDataset.createStrides(dataset2, new int[]{0});
		
		for(int i=0;i<myArray1.length;i++){
			System.out.println(myArray1[i]);
		}
		for(int i=0;i<myArray2.length;i++){
			System.out.println(myArray2[i]);
		}

		MyTensorDot.tensorDotProduct(dataset1, dataset2, new int[] { 2, 1 }, new int[] { 2, 0 });
		System.out.println("\ndone1");
		

	}
}
