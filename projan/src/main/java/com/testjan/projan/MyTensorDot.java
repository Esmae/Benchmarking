package com.testjan.projan;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.january.dataset.AbstractDataset;
import org.eclipse.january.dataset.DTypeUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.SliceIterator;

//TODO: Questions
//Why final?

public class MyTensorDot {
	public static Dataset tensorDotProduct(final Dataset a, final Dataset b, final int[] axisa, final int[] axisb) {
		if (axisa.length != axisb.length) {
			throw new IllegalArgumentException("Numbers of summing axes must be same");
		}
		final int[] ashape = a.getShapeRef();//array of lengths for each dimension
		final int[] bshape = b.getShapeRef();
		final int arank = ashape.length; //the rank of the tensors
		final int brank = bshape.length;
		int[] aaxes = new int[axisa.length];//stores axisa values for between 0 and arank
		int[] baxes = new int[axisa.length];
		for (int i = 0; i < axisa.length; i++) {
			int n;

			//performs checks that axisa and axisb arguments are valid
			n = axisa[i];
			if (n < 0) n += arank;
			if (n < 0 || n >= arank)
				throw new IllegalArgumentException("Summing axis outside valid rank of 1st dataset");
			aaxes[i] = n;

			n = axisb[i];
			if (n < 0) n += brank;
			if (n < 0 || n >= brank)
				throw new IllegalArgumentException("Summing axis outside valid rank of 2nd dataset");
			baxes[i] = n;

			if (ashape[aaxes[i]] != bshape[n])
				throw new IllegalArgumentException("Summing axes do not have matching lengths");
		}
		
		//TODO: Finish adding your stuff
	// ordering by the strides of b
		int[] offset = new int[1];
		final int[] bstride= AbstractDataset.createStrides(b, offset);
	
		final int[] subbstride = new int[baxes.length];
		for(int i=0;i<baxes.length;i++){
			subbstride[i]=bstride[baxes[i]];
		}
		//TODO:Write new sort and get rid of old stuff, test then get rid of testing
		//writing a new sorting section that uses comparator
		Integer[] aList = ArrayUtils.toObject(aaxes);
		Collections.sort(Arrays.asList(aList),new StrideSort(bstride));
		aaxes = ArrayUtils.toPrimitive(aList);
		
		Integer[] bList = ArrayUtils.toObject(baxes);
		Collections.sort(Arrays.asList(bList),new StrideSort(bstride));
		baxes = ArrayUtils.toPrimitive(bList);
		
		

		
		

		final boolean[] achoice = new boolean[arank];
		final boolean[] bchoice = new boolean[brank];
		Arrays.fill(achoice, true);
		Arrays.fill(bchoice, true);
		//axes passed in argument array can put to false
		for (int i = 0; i < aaxes.length; i++) { // flag which axes to iterate over
			achoice[aaxes[i]] = false;
			bchoice[baxes[i]] = false;
		}
		//calculating the rank of the result tensor
		int drank = arank + brank - 2*aaxes.length;
		int[] dshape = new int[drank];
		int d = 0;
		//calculating the lengths of each axis
		for (int i = 0; i < arank; i++) {
			if (achoice[i])
				dshape[d++] = ashape[i];
		}
		for (int i = 0; i < brank; i++) {
			if (bchoice[i])
				dshape[d++] = bshape[i];
		}
		//deciding the best datatype
		int dtype = DTypeUtils.getBestDType(a.getDType(), b.getDType());
		
		@SuppressWarnings("deprecation")
		Dataset data = DatasetFactory.zeros(dshape, dtype);

		SliceIterator ita = a.getSliceIteratorFromAxes(null, achoice);
		int l = 0;
		final int[] apos = ita.getPos();
		while (ita.hasNext()) {
			SliceIterator itb = b.getSliceIteratorFromAxes(null, bchoice);
			final int[] bpos = itb.getPos();
			
			while (itb.hasNext()) {
				double sum = 0.0;
				double com = 0.0;
				apos[aaxes[aaxes.length - 1]] = -1;
				bpos[baxes[aaxes.length - 1]] = -1;
				while (true) { // step through summing axes
					int e = aaxes.length - 1;
					for (; e >= 0; e--) {
						int ai = aaxes[e];
						int bi = baxes[e];

						apos[ai]++;
						bpos[bi]++;
						if (apos[ai] == ashape[ai]) {
							apos[ai] = 0;
							bpos[bi] = 0;
						} else
							break;
					}
					//Kahan's summation algorithm - used to reduce errors due to summing finite precision floating point numbers
					if (e == -1) break;
					final double y = a.getDouble(apos) * b.getDouble(bpos) - com;
					final double t = sum + y;
					com = (t - sum) - y;
					sum = t;
				}
				data.setObjectAbs(l++, sum);
			}
		}

		return data;
	}
	
	
}
