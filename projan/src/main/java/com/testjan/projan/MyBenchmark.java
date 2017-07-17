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

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.LinearAlgebra;

@State(Scope.Thread)
public class MyBenchmark {

	@State(Scope.Thread)
	public static class myState {

		@Param({ "125000" })
		public static double noElements1;// the number of elements in the first
											// tensor

		@Param({ "125000" })
		public static double noElements2;// the number of elements in the second
											// tensor

		static Dataset dataset1;
		static Dataset dataset2;

		@Setup(Level.Trial)
		public void doSetup() {
			// creating two cube tensors, 5x5x5
			dataset1 = DatasetFactory.createRange(noElements1);
			dataset2 = DatasetFactory.createRange(noElements2);

			dataset1 = dataset1.reshape(50,50,50);
			dataset2 = dataset2.reshape(50,50,50);
		}

	}

	@State(Scope.Thread)
	public static class myRefState {

		static Reference myRef;

		private static double size;// the number of elements in the reference
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
		return myRefState.myRef.addNum(myRefState.size);
	}

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot01(myState theState) {
		return LinearAlgebra.tensorDotProduct(myState.dataset1, myState.dataset2, new int[] { 0, 1 },
				new int[] { 0,1});
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot02(myState theState) {
		return LinearAlgebra.tensorDotProduct(myState.dataset1, myState.dataset2, new int[] { 0, 2 },
				new int[] { 0,1});
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot10(myState theState) {
		return LinearAlgebra.tensorDotProduct(myState.dataset1, myState.dataset2, new int[] { 1, 0 },
				new int[] { 0,1 });
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot12(myState theState) {
		return LinearAlgebra.tensorDotProduct(myState.dataset1, myState.dataset2, new int[] { 1, 2 },
				new int[] { 0,1});
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot20(myState theState) {
		return LinearAlgebra.tensorDotProduct(myState.dataset1, myState.dataset2, new int[] { 2, 0 },
				new int[] { 0,1});
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot21(myState theState) {
		return LinearAlgebra.tensorDotProduct(myState.dataset1, myState.dataset2, new int[] { 2, 1 },
				new int[] { 0,1});
}

	

}
