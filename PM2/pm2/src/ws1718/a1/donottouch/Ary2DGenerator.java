package ws1718.a1.donottouch;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Ary2DGenerator implements Supplier<double[][][]> {

	private final Random repro = new Random(1987654);
	private int maxLength;

	public Ary2DGenerator(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public double[][][] get() {
		int l1 = repro.nextInt(maxLength) + 1;
		int l2 = repro.nextInt(maxLength) + 1;
		double[][] m1 = new double[l1][l2];
		double[][] m2 = new double[l1][];
		for (int row = 0; row < l1; row++) {
			int lx = repro.nextInt(l2);
			m2[row] = new double[lx];
			for (int col = 0; col < lx; col++) {
				double val = repro.nextInt(2);
				m1[row][col] = m2[row][col] = val;
			}
		}
		int swap = Math.min(repro.nextInt(l1) + 1, l1 - 1);
		m2[swap] = Arrays.copyOf(m2[swap], l2);
		return new double[][][] { m1, m2 };
	}

	public static void main(String[] args) {
		Ary2DGenerator ag1 = new Ary2DGenerator(10);
		Ary2DGenerator ag2 = new Ary2DGenerator(10);

		IntStream.range(0, 20).forEach(e_ -> {
			double[][][] res1 = ag1.get();
			System.out.println(ppAry2D(res1[0]));
			System.out.println(ppAry2D(res1[1]));
			System.out.println("--------------------------------------------------------");
		});
		
		
	}

	private static String ppAry2D(double[][] ds) {
		return Arrays.deepToString(ds).replaceAll("\\[", "{").replaceAll("\\]", "}");
	}
}
