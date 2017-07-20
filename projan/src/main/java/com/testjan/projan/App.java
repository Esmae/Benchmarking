package com.testjan.projan;


import org.eclipse.january.dataset.AbstractDataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
/**
 *A testing class for how to use Datasets
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       
    	DoubleDataset dataset1 = DatasetFactory.createRange(10);
        dataset1.resize(1,5,2);
        int[] myStride = AbstractDataset.createStrides(dataset1,new int[]{0});
        
       
        
        
        
       
    }
}
