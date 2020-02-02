package ws1819.a1;

import java.util.*;

public class A1 {

    // 5
    public static double geoReiheIt(double a0, double q, int n) {
        if (n < 0) throw new IllegalArgumentException("n < 0");
        double sum = 0.0;
        for (int k = 0; k <= n; k++) {
            sum += a0 * Math.pow(q, k);
        }
        return sum;
    }

    // 5
    public static double geoReiheRek(double a0, double q, int n){
        if (n < 0) throw new IllegalArgumentException("n < 0");
        if (n == 0) return a0;
        return a0 * Math.pow(q, n) + geoReiheRek(a0, q, n - 1);
    }


    // 5 n für eps
    public static int n_fuer_eps(double eps,double a0,double q) {
        int n = 0;
        while (Math.abs(geoReiheIt(a0, q, n) - a0 / (1-q)) > eps) {
            n++;
        }
        return n;
    }

    // 10 Pkt
    public static double[][] miniTabCalc(double[][] table){
        int spalten = table[0].length + 2;
        double[][] result = new double[table.length][spalten];
        for (int i = 0; i < table.length; i++) {
            double reihenSumme = 0.0;
            for (int j = 0; j < table[0].length; j++) {
                reihenSumme += table[i][j];
                result[i][j] = table[i][j];
            }
            result[i][spalten - 2] = reihenSumme;
            result[i][spalten - 1] = reihenSumme / table[0].length;
        }
        return result;
    }

    // 10 Pkt Umgang mit Maps
    public static Map<Integer, List<Integer>> merge(Map<Integer,List<Integer>> m1, Map<Integer, List<Integer>> m2) {
        Map<Integer, List<Integer>> result = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : m1.entrySet()) {
            result.computeIfAbsent(entry.getKey(), x -> new ArrayList<>());
            result.get(entry.getKey()).addAll(entry.getValue());
        }

        for (Map.Entry<Integer, List<Integer>> entry : m2.entrySet()) {
            result.computeIfAbsent(entry.getKey(), x -> new ArrayList<>());
            result.get(entry.getKey()).addAll(entry.getValue());
        }
        return result;
    }
}
