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
		
		
		
		Dataset dataseta;//original dataset
		Dataset datasetTa;//transposed dataset
		Dataset datasetTTa;//A dataset that has been transposed and then tranposed back
		
		Dataset datasetb;//original dataset
		Dataset datasetTb;//transposed dataset
		Dataset datasetTTb;//A dataset that has been transposed and then tranposed back
		
		@Setup(Level.Trial)
		public void doSetup(){
			//creating rank 4 tensors
			dataseta = DatasetFactory.createRange(S*S*S*S);
			dataseta = dataseta.reshape(S,S,S,S);
			datasetTa = DatasetFactory.createRange(S*S*S*S);
			datasetTa = datasetTa.reshape(S,S,S,S);
			datasetTa = datasetTa.getTransposedView(3,1,2,0);//swapping axes round
			datasetTTa = DatasetFactory.createRange(S*S*S*S);
			datasetTTa = datasetTa.reshape(S,S,S,S);
			datasetTTa = datasetTTa.getTransposedView(3,1,2,0);
			datasetTTa = datasetTTa.getTransposedView(3,1,2,0);
			
			datasetb = DatasetFactory.createRange(S*S*S*S);
			datasetb = datasetb.reshape(S,S,S,S);
			datasetTb = DatasetFactory.createRange(S*S*S*S);
			datasetTb = datasetTb.reshape(S,S,S,S);
			datasetTb = datasetTb.getTransposedView(3,1,2,0);//swapping axes round
			datasetTTb = DatasetFactory.createRange(S*S*S*S);
			datasetTTb = datasetTb.reshape(S,S,S,S);
			datasetTTb = datasetTTb.getTransposedView(3,1,2,0);
			datasetTTb = datasetTTb.getTransposedView(3,1,2,0);
			
			
		}
		
	}
	/**
	 * Benchmarking adding a tensor to itself, with original iterator
	 * @param theState - contains the dataset
	 * @return
	 */
/*	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddOrig(myState theState){
		return Addition.add(theState.dataseta,theState.datasetb);
	}*/
	/**
	 * Benchmarking adding a transposed view of a tensor to itself, with original iterator
	 * @param theState - contains the dataset 
	 * @return
	 */
	/*@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeOrig(myState theState){
		return Addition.add(theState.datasetTa,theState.datasetTb);
	}*/
	
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
		return Addition.myAdd(theState.dataseta,theState.datasetb);
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
		return Addition.myAdd(theState.datasetTa,theState.datasetTb);
	}
/*	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeMineRev(myState theState){
		return Addition.myAddRev(theState.datasetTa,theState.datasetTb);
	}*/
	/*@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddMineRev(myState theState){
		return Addition.myAddRev(theState.dataseta,theState.datasetb);
	}*/
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTTMine(myState theState){
		return Addition.myAdd(theState.datasetTTa,theState.datasetTTb);
	}
	
	
}
