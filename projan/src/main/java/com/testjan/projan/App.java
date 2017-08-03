package com.testjan.projan;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

/**
 * A testing class for how to use Datasets
 *
 */
public class App {
	public static void main(String[] args) {
			
			/*Dataset c = DatasetFactory.createRange(60*60*60*60);
			c = c.reshape(60,60,60,60);
			Dataset d = DatasetFactory.createRange(60*60*60*60);
			d = d.reshape(60,60,60,60);
			Addition.myAdd(c, d);
			while (true) {
				Addition.myAdd(c, d);
			}*/
		
		Dataset a = DatasetFactory.createFromObject(new int[]{1,2,3,4,5,6});
		a = a.reshape(2,3);
		System.out.println(a.toString(true));
		

	}
}
