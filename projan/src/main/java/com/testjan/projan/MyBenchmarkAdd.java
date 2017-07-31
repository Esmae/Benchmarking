package com.testjan.projan;

import java.util.concurrent.TimeUnit;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
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

/**
 * Benchmarks the addition of two tensors (identical), before and after taking a transpose view
 * 
 *
 */

@State(Scope.Thread)
public class MyBenchmarkAdd {
	
	@State(Scope.Thread) 
	public static class myState{
		//data set parameters
		@Param({"250"})
		public int S1;//the size of the first axis for the DataSet
		
		@Param({"250"})
		public int S2;//the size of the second axis for the DataSet
		
		@Param({"250"})
		public int S3;//the size of the third axis for the DataSet
		
		Dataset dataset;//original dataset
		Dataset datasetT;//transposed dataset
		
		@Setup(Level.Trial)
		public void doSetup(){
			//creating cubic tensors
			dataset = DatasetFactory.createRange(S1*S2*S3);
			dataset = dataset.reshape(S1,S2,S3);
			datasetT = DatasetFactory.createRange(S1*S2*S3);
			datasetT = datasetT.reshape(S1,S2,S3);
			datasetT = datasetT.getTransposedView(1,0,2);//swapping the first and second axes round
		}
		
	}
	/**
	 * Benchmarking adding a tensor to itself, with original iterator
	 * @param theState - contains the dataset
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddOrig(myState theState){
		return Addition.add(theState.dataset,theState.dataset);
	}
	/**
	 * Benchmarking adding a transposed view of a tensor to itself, with original iterator
	 * @param theState - contains the dataset
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeOrig(myState theState){
		return Addition.add(theState.datasetT,theState.datasetT);
	}
	
	/**
	 * Benchmarking adding a tensor to itself, with my iterator
	 * @param theState - contains the dataset
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddMine(myState theState){
		return Addition.myAdd(theState.dataset,theState.dataset);
	}
	/**
	 * Benchmarking adding a transposed view of a tensor to itself, with my iterator
	 * @param theState - contains the dataset
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeMine(myState theState){
		return Addition.myAdd(theState.datasetT,theState.datasetT);
	}
	
	
}
