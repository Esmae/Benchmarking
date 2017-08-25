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
 * Class for benchmarking the summation of datasets
 *
 */
@State(Scope.Thread)
public class MyBenchmarkSum {
	
	@State(Scope.Thread)
	public static class myState{
		//dataset parameters
		@Param({"60"})
		public int S;// the size of all the axes for the symmetric Dataset
		
		Dataset dataset;//original dataset
		Dataset datasetT;//transposed dataset
		
		@Setup(Level.Trial)
		public void doSetup(){
			//creating rank 4 tensors
			dataset = DatasetFactory.createRange(S*S*S*S);
			dataset = dataset.reshape(S,S,S,S);
			datasetT = DatasetFactory.createRange(S*S*S*S);
			datasetT = datasetT.reshape(S,S,S,S);
			datasetT = datasetT.getTransposedView(3,2,1,0);
		}
		
	}
	/**
	 * Benchmarks the summation of a dataset using IndexIterator
	 * @param theState
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testSumOrigIta(myState theState){
		return SumDataset.sumOrigIterator(theState.dataset);
	}
	/**
	 * Benchmarks the summation of a transposed dataset using IndexIterator
	 * @param theState
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testSumTransposeOrigIta(myState theState){
		return SumDataset.sumOrigIterator(theState.datasetT);
	}
	/**
	 * Benchmarks the summation of a dataset using MyStrideIterator
	 * @param theState
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testSumMyStrideIta(myState theState){
		return SumDataset.sumMyStrideIterator(theState.dataset);
	}
	/**
	 * Benchmarks the summation of a transposed dataset using MyStrideIterator
	 * @param theState
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testSumTransposeMyStrideIta(myState theState){
		return SumDataset.sumMyStrideIterator(theState.datasetT);
	}
	
	
	/**
	 * Benchmarks the summation of a dataset using MyStrideIterator
	 * @param theState
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testTwoSumMyStrideIta(myState theState){
		return SumDataset.sumMyStrideIterator2(theState.dataset);
	}
	/**
	 * Benchmarks the summation of a transposed dataset using MyStrideIterator
	 * @param theState
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testTwoTransposeSumMyStrideIta(myState theState){
		return SumDataset.sumMyStrideIterator2(theState.datasetT);
	}
	/**
	 * Benchmarks the summation of a dataset using MyStrideIterator
	 * @param theState
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testSumMyIndexIta(myState theState){
		return SumDataset.sumMyIndexIterator(theState.dataset);
	}
	/**
	 * Benchmarks the summation of a transposed dataset using MyStrideIterator
	 * @param theState
	 * @return
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public double testSumTransposeMyIndexIta(myState theState){
		return SumDataset.sumMyIndexIterator(theState.datasetT);
	}
	
}









