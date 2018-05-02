package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class UniversalHashFamily {

	int b;
	int[][] h;
	int u;

	public UniversalHashFamily(int tableSize, int keySize) {
		b = (int) (Math.log(tableSize) / Math.log(2));
		u = keySize;
		h = new int[b][u];
		Random random = new Random();
		for (int i = 0; i < b; i++) {
			for (int j = 0; j < u; j++) {
				h[i][j] = random.nextInt(2);
			}
		}
	}

	private int[] getHased(int[] x) {
		int[] hx = new int[b];
		ArrayList<Integer> aux = new ArrayList<Integer>();
		for (int i = 0; i < u; i++) {
			if (x[i] == 1) {
				aux.add(i);
			}
		}
		for (int i = 0; i < b; i++) {
			Iterator<Integer> itr = aux.iterator();
			while (itr.hasNext()) {
				hx[i] = (hx[i] + h[i][itr.next()]) % 2;
			}
		}
		return hx;

	}

	public int getHashedIndex(int key) {
		int[] x = new int[u];
		for (int i = 0; i < u; ++i) {
			x[i] = (key >> i) & 1;
		}
		return this.getInteger(this.getHased(x));
	}

	private int getInteger(int[] x) {
		int result = 0;
		for (int i = 0; i < x.length; i++) {
			result += x[i] * Math.pow(2, i);
		}
		return result;
	}
}