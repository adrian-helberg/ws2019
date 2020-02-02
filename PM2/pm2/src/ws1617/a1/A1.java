package ws1617.a1;

import java.util.*;

public class A1 {

	/**
	 * TODO 5 Pkt
	 * 
	 * Berechnet die Formel summe k = 3..n 8*k/(k**2-1)**2 Wirft eine
	 * IllegalArgumentException, wenn n außerhalb des gültigen Wertebereichs,
	 * d.h. n <3.
	 * 
	 * @param n
	 *            Schrittweite der Näherung
	 * @return double berechneter Wert der Näherungsformel
	 */
	public static double sum(int n) {
		if (n < 3) throw new IllegalArgumentException("n < 3");
		double sum = 0;
		for (int k = 3; k <= n; k++) {
			int zaehler = 8 * k;
			double nenner = Math.pow(Math.pow(k, 2) - 1, 2);
			sum += zaehler / nenner;
		}
		return sum;
	}

	/**
	 * TODO 5 Pkt
	 * Berechnet das erste n, für die der exakte Wert von der
	 * Näherungsformel um < eps abweicht. Für dieses n gilt dann: exakt-sum(n) <
	 * eps. Der Wert von exakt ist 13.0/18 = 0.7222222222222222
	 * 
	 * @param eps
	 *            (double) maximaler Fehler der Näherung < 1
	 * @return int das erste n, für das gilt: exakt-sum(n) < eps
	 */
	public static int fehlerSumKleinerEps(double eps) {
		int n = 3;
		while ((13.0/18.0)-sum(n)> eps) {
			n++;
		}
		return n;
	}

	/**
	 * TODO (10 Pkt)
	 * 
	 * osterWelt ist ein unregelmäßiges 3-dimensionales Array, das an
	 * zufälligen Positionen "easter egg" Einträge enthält. Die Methode soll die
	 * Positionen der "easter egg" Einträge als Liste von 3-elementigen Listen
	 * zurückgeben. Das erste Element der 3-elem Liste ist die x, das zweite die
	 * y, das dritte die z Position des jeweiligen "easter egg" Eintrages. Nutzen Sie die
	 * Konstante EASTER_EGG, um Tippfehler zu vermeiden.
	 * 
	 * @param osterWelt
	 *            3-dimensionales unregelmäßiges Arrays
	 * @return Liste mit 3 elementigen Listen, die die x,y,z Position der
	 *         "easter egg" Einträge enthält.
	 */
	private static final String EASTER_EGG = "easter egg";

	public static List<List<Integer>> sucheOsterEier(String[][][] osterWelt) {
		List<List<Integer>> result = new ArrayList<>();
		for (int i = 0; i < osterWelt.length; i++) {
			for (int j = 0; j < osterWelt[i].length; j++) {
				for (int k = 0; k < osterWelt[i][j].length; k++) {
					if (Objects.equals(osterWelt[i][j][k], EASTER_EGG)) {
						List<Integer> tmp = new ArrayList<>();
						tmp.add(i);
						tmp.add(j);
						tmp.add(k);
						result.add(tmp);
					}
				}
			}
		}
		return result;
	}

	/**
	 * TODO (10Pkt)
	 * 
	 * Gegeben eine beliebig geschachtelte Collection von
	 * Collections. Bestimmen Sie die maximale Schachtelungstiefe.
	 * 
	 * @param col
	 *            die Collection von Collection
	 * @return int die maximale Schachtelungstiefe. Die äußere Collection wird
	 *         nicht mitgezählt
	 */
	public static int maxDepth(Collection<?> col) {
		return maxDepthR(col, 0);
	}

	public static int maxDepthR(Collection<?> col, int d) {
		int depth = 0;
		for (Object o : col) {
			if (o instanceof Collection) {
				depth = Math.max(maxDepthR((Collection<?>) o, d + 1), depth);
			}
		}
		return Math.max(d, depth);

	}
}
