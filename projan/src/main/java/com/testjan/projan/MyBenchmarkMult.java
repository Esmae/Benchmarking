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
public class MyBenchmarkMult {

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
			// creating two cube tensors
			dataset1 = DatasetFactory.createRange(oneS1*oneS2*oneS3);
			dataset2 = DatasetFactory.createRange(twoS1*twoS2*twoS3);

			dataset1 = dataset1.reshape(oneS1,oneS2,oneS3);
			dataset2 = dataset2.reshape(twoS1,twoS2,twoS3);
		}

	}
	
	@State(Scope.Thread) 
	public static class myStateCDE{
		
		//the datasets used for comparing ordering strides 
		//when adding two datasets that don't have the same strides
		Dataset datasetc;
		Dataset datasetd;
		Dataset datasete;
		
		Dataset dataset1;
		Dataset dataset2;
		
		
		
		@Setup(Level.Trial)
		public void doSetup(){
			

			//creating the datasets for comparing ordering the strides
			datasetc = DatasetFactory.createRange(13*18*22*18);
			datasetc = datasetc.reshape(18,13,22,18);
			
			datasetd = DatasetFactory.createRange(18*13*22*18);
			datasetd = datasetd.reshape(18,13,22,18);
			datasetd = datasetd.getTransposedView(0,1,2,3);//creating strides, not really getting a transpose
			
			datasete = DatasetFactory.createRange(18*13*18*22);
			datasete = datasete.reshape(18,13,22,18);
			datasete = datasete.getTransposedView(3,1,2,0);
			
			dataset1 = DatasetFactory.createRange(100*100*100);
			dataset1 = dataset1.reshape(100,100,100);
			
			dataset2 = DatasetFactory.createRange(100*99*100);
			dataset2 = dataset2.reshape(100,99,100);
			
		}
		
	}

	@State(Scope.Thread)
	public static class myRefState {

		Reference myRef;

		private int[] shape = new int[]{10,10,10};//the shape of the dataset

		@Setup(Level.Trial)
		public void doRefSetup() {
			myRef = new Reference(shape);
		}
	}

	/**
	 * Running the reference benchmark so can more easily compare tests on different builds
	 * @param theRefState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testRef(myRefState theRefState) {
		return theRefState.myRef.addNum();
	}
	/**
	 * Performing the tensor Dot Product on axes {1,2} on Original tensorDotProduct method   
	 * @param theState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDotOrig1212(myState theState) {
		return OrigTensorDot.tensorDotProduct(theState.dataset1, theState.dataset2, theState.axesOrder12,
				theState.axesOrder12);
	}
	/**
	 * Performing the tensor Dot Product on axes {1,2} on new tensorDotProduct method   
	 * @param theState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDotMine1212(myState theState) {
		return MyTensorDot.tensorDotProduct1(theState.dataset1, theState.dataset2, theState.axesOrder12,
				theState.axesOrder12);
	}
	/**
	 * Performing the tensor Dot Product on axes {2,1} on Original tensorDotProduct method   
	 * @param theState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDotOrig2121(myState theState) {
		return OrigTensorDot.tensorDotProduct(theState.dataset1, theState.dataset2, theState.axesOrder21,
				theState.axesOrder21);
	}
	/**
	 * Performing the tensor Dot Product on axes {2,1} on new tensorDotProduct method   
	 * @param theState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDotMine2121(myState theState) {
		return MyTensorDot.tensorDotProduct1(theState.dataset1, theState.dataset2, theState.axesOrder21,
				theState.axesOrder21);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot1_untranposed(myStateCDE theState){
		return MyTensorDot.tensorDotProduct1(theState.datasetc, theState.datasetc, new int[]{0, 1},new int[]{3,1});
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot2_untranposed(myStateCDE theState){
		return MyTensorDot.tensorDotProduct2(theState.datasetc, theState.datasetc, new int[]{0, 1},new int[]{3,1});
	}
/*	//NEXT
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot1_tranposed(myStateCDE theState){
		return MyTensorDot.tensorDotProduct1(theState.datasetd, theState.datasetd, new int[]{0, 1},new int[]{3,1});
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot2_tranposed(myStateCDE theState){
		return MyTensorDot.tensorDotProduct2(theState.datasetd, theState.datasetd, new int[]{0, 1},new int[]{3,1});
	}
	//NEXT
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot1_mixedDE(myStateCDE theState){
		return MyTensorDot.tensorDotProduct1(theState.datasetd, theState.datasete, new int[]{2, 3},new int[]{2,3});
	}
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot1_mixedED(myStateCDE theState){
		return MyTensorDot.tensorDotProduct1(theState.datasete, theState.datasetd, new int[]{2, 3},new int[]{2,3});
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot2_mixed(myStateCDE theState){
		return MyTensorDot.tensorDotProduct2(theState.datasetd, theState.datasete, new int[]{2, 3},new int[]{2,3});*/
	//}
	//NEXT
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot1_mixed12(myStateCDE theState){
		return MyTensorDot.tensorDotProduct1(theState.dataset1, theState.dataset2, new int[]{0, 1},new int[]{2,0});
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot2_mixed12(myStateCDE theState){
		return MyTensorDot.tensorDotProduct2(theState.dataset1, theState.dataset2, new int[]{0, 1},new int[]{2,0});
	}
	
}	
	



	


