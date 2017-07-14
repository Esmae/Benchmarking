package com.testjan.projan;

import java.util.Arrays;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.LinearAlgebra;
/**
 *A testing class for how to use Datasets
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       
        
        
        Dataset dataset1 = DatasetFactory.createFromObject(new double[] { 0,1,2, 3, 4, 5});
        
        dataset1 = dataset1.reshape(1,3,2);
        Dataset dataset2 = DatasetFactory.createFromObject(new double[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23});
        dataset2 = dataset2.reshape(4,2,3);
        
        Dataset result = LinearAlgebra.tensorDotProduct(dataset1, dataset2,new int[]{1,2},new int[]{2,1});
        System.out.println("result " + result.toString(true));
        System.out.println("shape of result: " + Arrays.toString(result.getShape()));
        result = result.reshape(4);
        System.out.println("shape of result: " + Arrays.toString(result.getShape()));
        System.out.println("result " + result.toString(true));
        
        for(int i=0;i<4;i++){
        	System.out.println(result.getDouble(i));
        }

        
     // Print the output:
     System.out.println("shape of dataset: " + Arrays.toString(dataset2.getShape()));
     System.out.println("toString of dataset: " + dataset2.toString());
     System.out.println("toString, with data, of dataset: \n" + dataset2.toString(true));
    }
}
