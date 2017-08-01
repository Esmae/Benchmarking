package com.testjan.projan;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

public class AppTT {
	public static void main(String[] args) {
		Dataset a = DatasetFactory.createRange(3*3);
		a = a.reshape(3,3);
		a = a.getTransposedView(1,0);
		a = a.getTransposedView(1,0);
		Dataset b = DatasetFactory.createRange(3*3);
		b = b.reshape(3,3);
		b = b.getTransposedView(1,0);
		b = b.getTransposedView(1,0);
		while (true) {
			Addition.myAdd(a, b);
		}
		
	
	}

}
