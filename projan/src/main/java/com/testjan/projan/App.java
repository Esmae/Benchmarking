package com.testjan.projan;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

/**
 * A testing class for how to use Datasets
 *
 */
public class App {
	public static void main(String[] args) {
			
			Dataset c = DatasetFactory.createRange(3*3);
			c = c.reshape(3,3);
			Dataset d = DatasetFactory.createRange(3*3);
			d = d.reshape(3,3);
			Addition.myAdd(c, d);
			while (true) {
				Addition.myAdd(c, d);
			}
		

	}
}
