package ws1718.a1;

import ws1718.a1.donottouch.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A1 {

	// 5Pkt
	public static int sumNIter(int n) {
		if (n < 1) throw new IllegalArgumentException("n < 1");
		int sum = 0;
		for (int k = 1; k <= n; k++) {
			sum += k;
		}
		return sum;
	}

	// 5 Pkt
	public static int sumNRek(int n) {
		if (n < 1) throw new IllegalArgumentException("n < 1");
		if (n == 1) return 1;
		return n + sumNRek(n - 1);
	}

	// 8Pkt
	public static double[][] toRegular(double[][] in) {
		int maxDepth = 0;
		for(double[] dd : in) {
			maxDepth = Math.max(maxDepth, dd.length);
		}

		double[][] result = new double[in.length][maxDepth];
		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < maxDepth; j++) {
				if (j < in[i].length) {
					result[i][j] = in[i][j];
				} else {
					result[i][j] = 0.0;
				}
			}
		}
		return result;
	}


	// 10Pkt
	public static List<Node> collectLeafs(Node parent) {
		List<Node> list = new ArrayList<>();
		if (parent.isLeaf()) {
			list.add(parent);
			return list;
		}
		for (Node n : parent.getChildren()) {
			list.addAll(collectLeafs(n));
		}
		return list;
	}

	// 6Pkt
	public static <T> T[] reverse(T[] ary) {
		T[] result = (T[]) new Object[ary.length];
		for (int i = 0; i < ary.length; i++) {
			result[i] = ary[ary.length-1-i];
		}
		return result;
	}
}
