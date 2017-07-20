package com.testjan.projan;


import java.util.TreeMap;

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
       /*
    	DoubleDataset dataset1 = DatasetFactory.createRange(10);
        dataset1.resize(1,5,2);
        int[] myStride = AbstractDataset.createStrides(dataset1,new int[]{0});*/

    	
        //testing TreeMap
        TreeMap<Integer,String> myMap = new TreeMap();
        myMap.put(1,"banana" );
        myMap.put(5, "apple");
        myMap.put(2, "pineapple");
        myMap.put(2, "carrot");
        
        int last = myMap.lastKey();
        System.out.println(last);
        int nextLast = myMap.lowerKey(last);
        System.out.println(nextLast);
        System.out.println(myMap.get(last));
       
    }
}
