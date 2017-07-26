package com.testjan.projan;
/*
 * Benchmarks the TensorDotProduct method in LinearAlgebra in Project January
 */

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;


import java.util.concurrent.TimeUnit;


import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;


@State(Scope.Thread)
public class MyBenchmark {

	@State(Scope.Thread)
	public static class myState {
		//first data set parameters
		@Param({"100"})
		public int oneS1;//the size of the first axis for the first DataSet
		
		@Param({"100"})
		public int oneS2;//the size of the second axis for the first DataSet
		
		@Param({"100"})
		public int oneS3;//the size of the third axis for the first DataSet
		
		
		//second data set parameters
		@Param({"100"})
		public int twoS1;//the size of the first axis for the second DataSet
		
		@Param({"100"})
		public int twoS2;//the size of the second axis for the second DataSet
		
		@Param({"100"})
		public int twoS3;//the size of the third axis for the second DataSet
		 

		Dataset dataset1;
		Dataset dataset2;
		
		private int[] axesOrder12 = new int[]{1,2};
		private int[] axesOrder21 = new int[]{2,1};
		

		@Setup(Level.Trial)
		public void doSetup() {
			// creating two cube tensors, 5x5x5
			dataset1 = DatasetFactory.createRange(oneS1*oneS2*oneS3);
			dataset2 = DatasetFactory.createRange(twoS1*twoS2*twoS3);

			dataset1 = dataset1.reshape(oneS1,oneS2,oneS3);
			dataset2 = dataset2.reshape(twoS1,twoS2,twoS3);
		}

	}

	@State(Scope.Thread)
	public static class myRefState {

		Reference myRef;

		private double size;// the number of elements in the reference
									// dataset

		@Setup(Level.Trial)
		public void doRefSetup() {
			myRef = new Reference(size);
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testRef(myRefState theRefState) {
		return theRefState.myRef.addNum(theRefState.size);
	}

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDotOrig1212(myState theState) {
		return OrigTensorDot.tensorDotProduct(theState.dataset1, theState.dataset2, theState.axesOrder12,
				theState.axesOrder12);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDotMine1212(myState theState) {
		return MyTensorDot.tensorDotProduct(theState.dataset1, theState.dataset2, theState.axesOrder12,
				theState.axesOrder12);
	}
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDotOrig2121(myState theState) {
		return OrigTensorDot.tensorDotProduct(theState.dataset1, theState.dataset2, theState.axesOrder21,
				theState.axesOrder21);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDotMine2121(myState theState) {
		return MyTensorDot.tensorDotProduct(theState.dataset1, theState.dataset2, theState.axesOrder21,
				theState.axesOrder21);
	}
	
}	
	



	


