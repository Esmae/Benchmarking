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
		@Param({"60"})
		public int S;//the size of all the axes for the symmetric DataSet
		
		
		
		Dataset dataset;//original dataset
		Dataset datasetT;//transposed dataset
		
		@Setup(Level.Trial)
		public void doSetup(){
			//creating cubic tensors
			dataset = DatasetFactory.createRange(S*S*S*S);
			dataset = dataset.reshape(S,S,S,S);
			datasetT = DatasetFactory.createRange(S*S*S*S);
			datasetT = datasetT.reshape(S,S,S,S);
			datasetT = datasetT.getTransposedView(3,2,1,0);//swapping axes round
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
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeMineRev(myState theState){
		return Addition.myAddRev(theState.datasetT,theState.datasetT);
	}
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddMineRev(myState theState){
		return Addition.myAddRev(theState.dataset,theState.dataset);
	}
	
	
}
