package com.testjan.projan;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

/**
 * A testing class for how to use Datasets
 *
 */
public class App {
	public static void main(String[] args) {
		int S = 60;
		Dataset datasetTTa;
		datasetTTa = DatasetFactory.createRange(S*S*S*S);
		datasetTTa = datasetTTa.reshape(S,S,S,S);
		datasetTTa = datasetTTa.getTransposedView(3,1,2,0);
		datasetTTa = datasetTTa.getTransposedView(3,1,2,0);
		
		Dataset dataset4;
		

	}
}
