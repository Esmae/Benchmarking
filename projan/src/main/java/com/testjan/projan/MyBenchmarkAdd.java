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
 * Benchmarks the addition of two tensors (identical, though not necessarily the same object), before and after taking a transpose view
 * 
 *
 */

@State(Scope.Thread)
public class MyBenchmarkAdd {
	
	@State(Scope.Thread) 
	public static class myStateAB1{
		//data set parameters
		@Param({"60"})
		public int S;//the size of all the axes for the symmetric DataSet
		
		Dataset dataseta;//original dataset
		Dataset datasetTa;//transposed dataset
		
		Dataset datasetb;//original dataset
		Dataset datasetTb;//transposed dataset
		
		Dataset dataset1;
		
		@Setup(Level.Trial)
		public void doSetup(){
			//creating rank 4 tensors
			dataseta = DatasetFactory.createRange(S*S*S*S);
			dataseta = dataseta.reshape(S,S,S,S);
			datasetTa = DatasetFactory.createRange(S*S*S*S);
			datasetTa = datasetTa.reshape(S,S,S,S);
			datasetTa = datasetTa.getTransposedView(3,1,2,0);//swapping axes round
			
			datasetb = DatasetFactory.createRange(S*S*S*S);
			datasetb = datasetb.reshape(S,S,S,S);
			datasetTb = DatasetFactory.createRange(S*S*S*S);
			datasetTb = datasetTb.reshape(S,S,S,S);
			datasetTb = datasetTb.getTransposedView(3,1,2,0);//swapping axes round
			
			dataset1 = DatasetFactory.createRange(S*S*S*S);
			dataset1 = dataset1.reshape(S,S,S,S);
			
			
		}
		
	}
	
	@State(Scope.Thread) 
	public static class myStateCDE{
		//data set parameters
		@Param({"60"})
		public int S;//the size of all the axes for the symmetric DataSet
		
		//the datasets used for comparing ordering strides 
		//when adding two datasets that don't have the same strides
		Dataset datasetc;
		Dataset datasetd;
		Dataset datasete;
		
		@Setup(Level.Trial)
		public void doSetup(){
			

			//creating the datasets for comparing ordering the strides
			datasetc = DatasetFactory.createRange(60*50*65*60);
			datasetc = datasetc.reshape(60,50,65,60);
			
			datasetd = DatasetFactory.createRange(60*50*65*60);
			datasetd = datasetd.reshape(60,50,65,60);
			datasetd = datasetd.getTransposedView(0,1,2,3);//creating strides, not really getting a transpose
			
			datasete = DatasetFactory.createRange(50*60*60*65);
			datasete = datasete.reshape(60,50,65,60);
			datasete = datasete.getTransposedView(3,1,2,0);
		}
		
	}
	
	
	@State(Scope.Thread)
	public static class myState{
		@Param({"60"})
		public int S;
		
		Dataset dataset;
		int[] axes = {0,1,2,3};
		int[] strides = {S*S*S,S*S,S,1};
		double[] multstrides = {S*S*S*S*S*S,S*S*S*S,S*S,1};
		
		@Setup(Level.Trial)
		public void doSetup(){
			
			dataset = DatasetFactory.createRange(S*S*S*S);
			dataset = dataset.reshape(S,S,S,S);
		}
	}
	
	
	
	/**
	 * Benchmarking adding a tensor to itself, with original iterator
	 * @param theState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddOrig(myStateAB1 theState){
		return Addition.add(theState.dataseta,theState.datasetb);
	}
	/**
	 * Benchmarking adding a transposed view of a tensor to itself, with original iterator
	 * @param theState - contains the datasets
	 * @return 
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeOrig(myStateAB1 theState){
		return Addition.add(theState.datasetTa,theState.datasetTb);
	}
	
	/**
	 * Benchmarking adding two tensors
	 * @param theState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset addMine(myStateAB1 theState){
		return Addition.myAddPartOne(theState.dataseta,theState.datasetb);
	}
	
	/**
	 * Benchmarking adding two tensors (with strides - as they have been transposed)
	 * using iterator in myAdd2
	 * @param theState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset addTwoMine(myStateAB1 theState){
		return Addition.myAdd2PartOne(theState.dataset1,theState.dataset1);
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Benchmarking adding a transposed view of a tensor to itself, with my iterator
	 * @param theState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeMine(myStateAB1 theState){
		return Addition.myAddPartOne(theState.datasetTa,theState.datasetTb);
	}
	/**
	 * Benchmarking adding two tensors, one has been tranposed one hasn't
	 * using iterator in myAdd - they have different strides
	 * @param theState - contains the datasets
	 * @return
	 */
	/*@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddMine_1032(myStateCDE theState){
		return Addition.myAddPartOne(theState.datasetc,theState.datasete);
	}*/
	/**
	 * Benchmarking adding two tensors, both have strides
	 * using iterator in myAdd - they have different strides
	 * @param theState - contains the datasets
	 * @return
	 */
/*	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeMine_1032(myStateCDE theState){
		return Addition.myAddPartOne(theState.datasetd,theState.datasete);
	}*/
	
	
	
	
	
	

	
	
	
	/**
	 * Benchmarking adding two tensors (with strides - as they have been transposed)
	 * using iterator in myAdd2
	 * @param theState - contains the datasets
	 * @return
	 */
	/*@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeMine2(myStateAB1 theState){
		return Addition.myAdd2PartOne(theState.datasetTa,theState.datasetTb);
	}*/
	/**
	 * Benchmarking adding two tensors, one has been tranposed one hasn't
	 * using iterator in myAdd2 - they have different strides
	 * @param theState - contains the datasets
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddMine2_1032(myStateCDE theState){
		return Addition.myAdd2PartOne(theState.datasetc,theState.datasete);
	}
	/**
	 * Benchmarking adding two tensors, both have strides
	 * using iterator in myAdd2 - they have different strides
	 * @param theState - contains the datasets
	 * @return
	 */
/*	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testAddTransposeMine2_1032(myStateCDE theState){
		return Addition.myAdd2PartOne(theState.datasetd,theState.datasete);
	}*/
	
	/**
	 * Benchmarking the last part of the Addition methods
	 *//*
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testMyAddPartTwo(myState theState){
		return Addition.myAddPartTwo(theState.dataset, theState.dataset, theState.axes);
	}
	*/
	
	
	
	
}
