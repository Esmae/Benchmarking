package com.testjan.projan;

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
import org.eclipse.january.dataset.LinearAlgebra;

@State(Scope.Thread)
public class MyBenchmark {
	
	@State(Scope.Thread)
	public static class myState{
		
		@Param({"6"})
		public static double noElements1;//the number of elements in the first tensor
		
		@Param({"24"})
		public static double noElements2;//the number of elements in the second tensor
		
		
		static Dataset dataset1;
		static Dataset dataset2;
		
		@Setup(Level.Trial)
		public void doSetup(){
			dataset1 = DatasetFactory.createRange(noElements1);
			dataset2 = DatasetFactory.createRange(noElements2);
			
			dataset1 = dataset1.reshape(1,3,2);
			dataset2 = dataset2.reshape(4,2,3);
		}
		
		
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
	public Dataset testTenDot(myState theState){
		return LinearAlgebra.tensorDotProduct(myState.dataset1, myState.dataset2, new int[]{1,2},new int[]{2,1});
	}
	
	
	
}
