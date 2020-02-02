import ws1819.a1.A1;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class A1Test {

    private double q1=0.3;
    private double a01=3.5;
    private double exaktQ1;
    private double delta = 1e-15;
    private double epsQ1N0 = 1.5+delta;
    private double epsQ1N8 = 9.841500000007386E-5+delta;
    private double epsQ1N22 = 4.7055692675712635E-12+delta;
    private double epsQ1N22Rek =4.707345624410664E-12;

    private double q2= -0.2;
    private double a02=7.3;
    private double exaktQ2;
    private double epsQ2N0 = 1.8250000000000002+delta;
    private double epsQ2N8 = 4.671999999317222E-6+delta; //
    private double epsQ2N8Rek = 4.672000001093579E-6+delta;
    private double epsQ2N22 = 0.0+delta;

    private int n0=0;
    private int n8=8;
    private int n22=22;

    private double[][] table1;
    private double[][] table1Calc;
    private double[][] table2;
    private double[][] table2Calc;
    private double[][] tableEmpty = {{},{},{}};
    private double[][] tableEmptyCalc = {{0.0, Double.NaN},{0.0,Double.NaN},{0.0,Double.NaN}};

    private Map<Integer, List<Integer>> mili1;
    private Map<Integer, List<Integer>> mili2;
    private Map<Integer, List<Integer>> mapMergeResult;
    private Map<Integer,List<Integer>> mili1Clone;
    private Map<Integer,List<Integer>> mili2Clone;
    private Map<Integer,List<Integer>> emptyMap;


    @Before
    public void fixture(){
        exaktQ1 = a01/(1-q1);
        exaktQ2 = a02/(1-q2);
        tableFixture();
        mapMergeFixture();
    }

    @Test
    public void testGeoReiheIt(){
        assertEquals(exaktQ1,A1.geoReiheIt(a01,q1,n0),epsQ1N0);
        assertEquals(exaktQ1,A1.geoReiheIt(a01,q1,n8),epsQ1N8);
        assertEquals(exaktQ1,A1.geoReiheIt(a01,q1,n22), epsQ1N22);
        assertEquals(exaktQ2,A1.geoReiheIt(a02,q2,n0),epsQ2N0);
        assertEquals(exaktQ2,A1.geoReiheIt(a02,q2,n8),epsQ2N8);
        assertEquals(exaktQ2,A1.geoReiheIt(a02,q2,n22),epsQ2N22);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeoReiheItNeg(){
        A1.geoReiheIt(a01,q1,-4);
    }

    @Test
    public void testGeoReiheRek(){
        /*for (int n = 0; n < 32; n++) {
            System.out.println(n + ":" +(exaktQ2 - A1.geoReiheRek(a02, q2, n)));
        }*/
        assertEquals(exaktQ1,A1.geoReiheRek(a01,q1,n0),epsQ1N0);
        assertEquals(exaktQ1,A1.geoReiheRek(a01,q1,n8),epsQ1N8);
        assertEquals(exaktQ1,A1.geoReiheRek(a01,q1,n22), epsQ1N22Rek);
        assertEquals(exaktQ2,A1.geoReiheRek(a02,q2,n0),epsQ2N0);
        assertEquals(exaktQ2,A1.geoReiheRek(a02,q2,n8),epsQ2N8Rek);
        assertEquals(exaktQ2,A1.geoReiheRek(a02,q2,n22),epsQ2N22);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeoReiheRekNeg(){
        A1.geoReiheRek(a01,q1,-4);
    }

    @Test
    public void testNFuerEps(){
        // Achtung Tests nur korrekt, wenn n_fuer_eps die iterative Lösung nutzt
        assertEquals(n0,A1.n_fuer_eps(epsQ1N0,a01,q1));
        assertEquals(n8,A1.n_fuer_eps(epsQ1N8,a01,q1));
        assertEquals(n22,A1.n_fuer_eps(epsQ1N22,a01,q1));
        assertEquals(n0,A1.n_fuer_eps(epsQ2N0,a02,q2));
        assertEquals(n8,A1.n_fuer_eps(epsQ2N8,a02,q2));
        assertEquals(n22,A1.n_fuer_eps(epsQ2N22,a02,q2));
    }

    private  void tableFixture(){
        table1 = new double[][]{{1,1,1},{1,1,1},{1,1,1}};
        table1Calc = new double[][]{{1,1,1,3,1},{1,1,1,3,1},{1,1,1,3,1}};
        table2 =  new double[][]{{1,2,3,4},{2,3,4,5},{3,4,5,6},{4,5,6,7}};
        //System.out.println(Arrays.deepToString(table2));
        table2Calc =  new double[][]{{1,2,3,4,10,2.5},{2,3,4,5,14,14/4.0},{3,4,5,6,18,18/4.0},{4,5,6,7,22,22/4.0}};
        //System.out.println(Arrays.deepToString(table2Calc));
        tableEmpty = new double[][]{{},{},{}};
        tableEmptyCalc = new double[][]{{0.0, Double.NaN},{0.0,Double.NaN},{0.0,Double.NaN}};
    }

    @Test
    public void testMiniTabCalcPos(){
        assertArrayEquals(table1Calc,A1.miniTabCalc(table1));
        assertArrayEquals(table2Calc,A1.miniTabCalc(table2));
    }


    @Test
    public void testMiniTabCalcGrenze(){
        assertArrayEquals(tableEmptyCalc,A1.miniTabCalc(tableEmpty));
        assertArrayEquals(table2Calc,A1.miniTabCalc(table2));
    }

    private void mapMergeFixture(){
        mili1 =new HashMap<>();
        Integer[] keys = {1,2,3};
        Integer[] values = {1,2,3};
        for( int i = 0; i < keys.length; i++){
            final int local = i;
            mili1.put(keys[i], Arrays.asList(values).stream().map(val -> val + local).collect(Collectors.toList()));
        }
        // mili1: {1=[1, 2, 3], 2=[2, 3, 4], 3=[3, 4, 5]}
        mili2 = new HashMap<>();
        Integer[] keys2 = {3,4,5};
        Integer[] values2 = {4,5,6};
        for( int i = 0; i < keys2.length; i++){
            final int local = i;
            mili2.put(keys2[i],Arrays.asList(values2).stream().map(val -> val + local).collect(Collectors.toList()));
        }
        // mili2: {3=[4, 5, 6], 4=[5, 6, 7], 5=[6, 7, 8]}

        // DAS IST NUR EIN BEISPIEL UND NICHT DIE LOESUNG
        mapMergeResult = new HashMap<>();
        mapMergeResult.put(1,new ArrayList<>(mili1.get(1)));
        mapMergeResult.put(2,new ArrayList<>(mili1.get(2)));
        List<Integer> value3 = new ArrayList<>(mili1.get(3));
        value3.addAll(new ArrayList<>(mili2.get(3)));
        mapMergeResult.put(3,value3);
        mapMergeResult.put(4,new ArrayList<>(mili2.get(4)));
        mapMergeResult.put(5,new ArrayList<>(mili2.get(5)));

        // System.out.println(mapMergeResult);
        // mapMergeResult: {1=[1, 2, 3], 2=[2, 3, 4], 3=[3, 4, 5, 4, 5, 6], 4=[5, 6, 7], 5=[6, 7, 8]}
        mili1Clone = mili1.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),entry -> new ArrayList<>(entry.getValue())));
        mili2Clone = mili2.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),entry -> new ArrayList<>(entry.getValue())));

        emptyMap = new HashMap<>();
    }

    @Test
    public void testMapMerge() {
        assertEquals(mapMergeResult,A1.merge(mili1, mili2));
        assertEquals(mili1Clone, mili1);
        assertEquals(mili2Clone, mili2);
    }

    @Test
    public void testMapMergeGrenze(){
        assertEquals(mili1Clone,A1.merge(emptyMap, mili1));
        assertEquals(mili1Clone, mili1);
        assertEquals(new HashMap<>(),emptyMap);
        assertEquals(mili2Clone,A1.merge(mili2,emptyMap));
        assertEquals(mili2Clone, mili2);
        assertEquals(new HashMap<>(),emptyMap);
    }
}
