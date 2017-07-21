package com.testjan.projan;

import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;

/**
 * A testing class for how to use Datasets
 *
 */
public class App {
	public static void main(String[] args) {

		DoubleDataset dataset1 = DatasetFactory.createRange(80000000);
		dataset1.resize(200, 200, 200);

		DoubleDataset dataset2 = DatasetFactory.createRange(80000000);
		dataset2.resize(200, 200, 200);

		MyTensorDot.tensorDotProduct(dataset1, dataset2, new int[] { 2, 1 }, new int[] { 2, 1 });
		System.out.println("\ndone1");
		OrigTensorDot.tensorDotProduct(dataset1, dataset2, new int[] { 2, 1 }, new int[] { 2, 1 });
		System.out.println("\ndone2");

	}
}
