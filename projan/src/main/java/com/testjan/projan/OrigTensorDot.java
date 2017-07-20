package com.testjan.projan;

/**
 * The original tensorDotProduct from Eclipse January
 */

import java.util.Arrays;

import org.eclipse.january.dataset.DTypeUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.SliceIterator;

public class OrigTensorDot {

	/**
	 * Calculate the tensor dot product over given axes. This is the sum of
	 * products of elements selected from the given axes in each dataset
	 * 
	 * @param a
	 * @param b
	 * @param axisa
	 *            axis dimensions in a to sum over (can be -ve)
	 * @param axisb
	 *            axis dimensions in b to sum over (can be -ve)
	 * @return tensor dot product
	 */
	public static Dataset tensorDotProduct(final Dataset a, final Dataset b, final int[] axisa, final int[] axisb) {
		if (axisa.length != axisb.length) {
			throw new IllegalArgumentException("Numbers of summing axes must be same");
		}
		final int[] ashape = a.getShapeRef();
		final int[] bshape = b.getShapeRef();
		final int arank = ashape.length;
		final int brank = bshape.length;
		final int[] aaxes = new int[axisa.length];
		final int[] baxes = new int[axisa.length];
		for (int i = 0; i < axisa.length; i++) {
			int n;

			n = axisa[i];
			if (n < 0)
				n += arank;
			if (n < 0 || n >= arank)
				throw new IllegalArgumentException("Summing axis outside valid rank of 1st dataset");
			aaxes[i] = n;

			n = axisb[i];
			if (n < 0)
				n += brank;
			if (n < 0 || n >= brank)
				throw new IllegalArgumentException("Summing axis outside valid rank of 2nd dataset");
			baxes[i] = n;

			if (ashape[aaxes[i]] != bshape[n])
				throw new IllegalArgumentException("Summing axes do not have matching lengths");
		}

		final boolean[] achoice = new boolean[arank];
		final boolean[] bchoice = new boolean[brank];
		Arrays.fill(achoice, true);
		Arrays.fill(bchoice, true);
		for (int i = 0; i < aaxes.length; i++) { // flag which axes to iterate
													// over
			achoice[aaxes[i]] = false;
			bchoice[baxes[i]] = false;
		}

		int drank = arank + brank - 2 * aaxes.length;
		int[] dshape = new int[drank];
		int d = 0;
		for (int i = 0; i < arank; i++) {
			if (achoice[i])
				dshape[d++] = ashape[i];
		}
		for (int i = 0; i < brank; i++) {
			if (bchoice[i])
				dshape[d++] = bshape[i];
		}
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
					if (e == -1)
						break;
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
