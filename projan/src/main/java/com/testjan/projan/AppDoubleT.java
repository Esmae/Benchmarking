package com.testjan.projan;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

public class AppDoubleT {
	
	public static void main(String[] args) {
		Dataset a = DatasetFactory.createRange(60*60*60*60);
		a = a.reshape(60,60,60,60);
		a = a.getTransposedView(3,1,2,0);
		a = a.getTransposedView(3,1,2,0);
		Dataset b = DatasetFactory.createRange(60*60*60*60);
		b = b.reshape(60,60,60,60);
		b = b.getTransposedView(3,1,2,0);
		b = b.getTransposedView(3,1,2,0);
		while (true) {
			Addition.myAdd(a, b);
		}
		
	
	}

}
