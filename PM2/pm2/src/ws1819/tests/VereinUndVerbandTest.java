package ws1819.tests;

import ws1819.a2.donottouch.modelgenerator.GeneratorKonstanten;
import ws1819.a2.donottouch.modelgenerator.VereinGenerator;
import ws1819.a2.donottouch.model.Kurs;
import ws1819.a2.model.Verein;
import ws1819.a2.model.Verband;
import ws1819.a2.donottouch.model.enums.Sportart;
import ws1819.a2.donottouch.model.enums.VereinsName;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class VereinUndVerbandTest {

    private List<Verein> vereine;
    private Verein vereinInGruendung;
    private Verband verband;
    private Verein aktiverVerein1;
    private Verein aktiverVerein2;
    private Verein aktiverVerein3;
    private Verein aktiverVerein4;
    private Verein aktiverVerein5;
    private Kurs fruehesterKurs1;
    private Kurs fruehesterKurs2;
    private Kurs fruehesterKurs3;
    private Kurs fruehesterKurs4;
    private Kurs fruehesterKurs5;

    private Verein aktiverVerein1Modifiziert;
    private Map<String,List<Kurs>> vereinGruppenNachJahrMonat;
    private Map<String,List<Kurs>> verbandGruppenNachJahrMonat;

    @Before
    public void fixture(){
        vereine  = VereinGenerator.generateVereine(GeneratorKonstanten.MAX_KURSE);
        verband = new Verband();
        verband.addAll(vereine);

        aktiverVerein1 = vereine.get(0);
        aktiverVerein2 = vereine.get(1);
        aktiverVerein3 = vereine.get(2);
        aktiverVerein4 = vereine.get(3);
        aktiverVerein5 = vereine.get(4);
        vereinInGruendung = new Verein(VereinsName.STRONGPERFORMER);

    }

      private void fixtureFruehesterKurs() {
        fruehesterKurs1 = new Kurs(Sportart.FUSSBALL,LocalDateTime.of(2018,10,2,20,30), Duration.ofHours(2),8);
        fruehesterKurs2 = new Kurs(Sportart.TURNEN,LocalDateTime.of(2018,1,2,11,0),Duration.ofHours(1).minusMinutes(-30),12);
        fruehesterKurs3 = new Kurs(Sportart.FUSSBALL,LocalDateTime.of(2018,4,1,17,0),Duration.ofHours(2),9);
        fruehesterKurs4 = new Kurs(Sportart.LAUFEN,LocalDateTime.of(2018,2,2,14,30),Duration.ofHours(1).minusMinutes(-30),28);
        fruehesterKurs5 = new Kurs(Sportart.LEICHTATHLETIK,LocalDateTime.of(2018,1,1,15,0),Duration.ofHours(1).minusMinutes(-30),17);
    }

    @Test
    public void fruehesterKursGrenzfall() {
        assertNull(new Verein(VereinsName.STRONGPERFORMER).fruehesterKurs());
    }

    @Test
    public void fruehesterKurs(){
        fixtureFruehesterKurs();
        assertEquals(fruehesterKurs1,aktiverVerein1.fruehesterKurs());
        assertEquals(fruehesterKurs2,aktiverVerein2.fruehesterKurs());
        assertEquals(fruehesterKurs3,aktiverVerein3.fruehesterKurs());
        assertEquals(fruehesterKurs4,aktiverVerein4.fruehesterKurs());
        assertEquals(fruehesterKurs5,aktiverVerein5.fruehesterKurs());
    }


    @Test
    public void testVereinGruppiereNachJahrMonatGrenzfall(){
        assertEquals(new HashMap<String,List<Kurs>>(),vereinInGruendung.gruppiereNachJahrMonat());

    }
    private void fixtureVereinGruppiereNachJahrMonat() {
        // Kopie eines Vereins
        aktiverVerein1Modifiziert = new Verein(aktiverVerein1);
        List<Kurs> plusKurse = new ArrayList<>();

        // Jeder Kurs wird dupliziert und dabei der Tag hochgezählt
        aktiverVerein1Modifiziert.getKurse().forEach(kurs -> plusKurse.add(kurs.copyOfPlus(1)));
        aktiverVerein1Modifiziert.addAll(plusKurse);

        // Kurse werden nach Datum und Zeit sortiert
        List<Kurs> nachDatumUndZeitSortierteKurse = new ArrayList<>(aktiverVerein1Modifiziert.getKurse());
        Collections.sort(nachDatumUndZeitSortierteKurse, new Comparator<Kurs>() {
            @Override
            public int compare(Kurs o1, Kurs o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });

        vereinGruppenNachJahrMonat = new HashMap<>();
        vereinGruppenNachJahrMonat.put("2018-10", nachDatumUndZeitSortierteKurse.subList(0,2));
        vereinGruppenNachJahrMonat.put("2018-12", nachDatumUndZeitSortierteKurse.subList(2,4));

        // Inhalt von vereinGruppenNachJahrMonat
        // 2018-12 ->[K(Leichtathletik, 01.12.2018 19:00, 1H30M, 22), K(Leichtathletik, 02.12.2018 19:00, 1H30M, 22)]
        // 2018-10 ->[K(Fußball, 02.10.2018 20:30, 2H, 8), K(Fußball, 03.10.2018 20:30, 2H, 8)]

    }

    @Test
    public void testVereinGruppiereNachJahrMonat(){
        fixtureVereinGruppiereNachJahrMonat();
        // Inhalt von vereinGruppenNachJahrMonat
        // 2018-12 ->[K(Leichtathletik, 01.12.2018 19:00, 1H30M, 22), K(Leichtathletik, 02.12.2018 19:00, 1H30M, 22)]
        // 2018-10 ->[K(Fußball, 02.10.2018 20:30, 2H, 8), K(Fußball, 03.10.2018 20:30, 2H, 8)]
        assertEquals(vereinGruppenNachJahrMonat,aktiverVerein1Modifiziert.gruppiereNachJahrMonat());
    }


    @Test
    public void testVerbandGruppiereNachJahrMonatGrenzfall(){
        Verband leererVerband = new Verband();
        assertEquals(new HashMap<String,List<Kurs>>(),leererVerband.gruppiereNachJahr());
    }

    private void fixtureVerbandGruppiereNachJahrMonat() {
        // Alle Kurse in einer Liste sammeln, sortieren und dann in die Map eintragen
        List<Kurs> nachDatumUndZeitSortierteKurse = new ArrayList<>();
        for (Verein verein:vereine) {
            nachDatumUndZeitSortierteKurse.addAll(verein.getKurse());
        }
        Collections.sort(nachDatumUndZeitSortierteKurse, new Comparator<Kurs>() {
            @Override
            public int compare(Kurs o1, Kurs o2) {
                return o1.getStart().with(TemporalAdjusters.firstDayOfMonth()).compareTo(o2.getStart().with(TemporalAdjusters.firstDayOfMonth()));
            }
        });

        verbandGruppenNachJahrMonat = new HashMap<>();
        verbandGruppenNachJahrMonat.put("2018-1",nachDatumUndZeitSortierteKurse.subList(0,2));
        verbandGruppenNachJahrMonat.put("2018-2",nachDatumUndZeitSortierteKurse.subList(2,5));
        verbandGruppenNachJahrMonat.put("2018-3",nachDatumUndZeitSortierteKurse.subList(5,6));
        verbandGruppenNachJahrMonat.put("2018-4",nachDatumUndZeitSortierteKurse.subList(6,8));
        verbandGruppenNachJahrMonat.put("2018-5",nachDatumUndZeitSortierteKurse.subList(8,9));
        verbandGruppenNachJahrMonat.put("2018-6",nachDatumUndZeitSortierteKurse.subList(9,11));
        verbandGruppenNachJahrMonat.put("2018-9",nachDatumUndZeitSortierteKurse.subList(11,12));
        verbandGruppenNachJahrMonat.put("2018-10",nachDatumUndZeitSortierteKurse.subList(12,14));
        verbandGruppenNachJahrMonat.put("2018-11",nachDatumUndZeitSortierteKurse.subList(14,15));
        verbandGruppenNachJahrMonat.put("2018-12",nachDatumUndZeitSortierteKurse.subList(15,17));
    }

    @Test
    public void testClientGruppiereNachJahrMonat(){
        fixtureVerbandGruppiereNachJahrMonat();

        // DAS ERWARTETE ERGEBNIS IN DER AUSGABE

        //2018-5 ->[K(Schwimmen, 01.05.2018 20:00, 2H, 9)]
        //2018-4 ->[K(Fußball, 01.04.2018 17:00, 2H, 9), K(Turnen, 02.04.2018 21:00, 1H, 26)]
        //2018-6 ->[K(Laufen, 01.06.2018 17:00, 1H, 16), K(Volleyball, 02.06.2018 16:00, 2H, 15)]
        //2018-11 ->[K(Fußball, 01.11.2018 22:00, 1H30M, 12)]
        //2018-1 ->[K(Turnen, 02.01.2018 11:00, 1H30M, 12), K(Leichtathletik, 01.01.2018 15:00, 1H30M, 17)]
        //2018-12 ->[K(Leichtathletik, 01.12.2018 19:00, 1H30M, 22), K(Leichtathletik, 02.12.2018 20:00, 1H, 21)]
        //2018-3 ->[K(Handball, 02.03.2018 15:00, 2H, 14)]
        //2018-2 ->[K(Handball, 01.02.2018 15:00, 2H, 8), K(Laufen, 02.02.2018 14:30, 1H30M, 28), K(Leichtathletik, 01.02.2018 20:00, 1H30M, 26)]
        //2018-10 ->[K(Fußball, 02.10.2018 20:30, 2H, 8), K(Turnen, 01.10.2018 22:30, 1H30M, 16)]
        //2018-9 ->[K(Fußball, 02.09.2018 14:00, 1H, 19)]

        assertEquals(sortiereWerteNachDatumZeit(verbandGruppenNachJahrMonat),sortiereWerteNachDatumZeit(verband.gruppiereNachJahr()));
    }

    private Map<String,List<Kurs>> sortiereWerteNachDatumZeit(Map<String,List<Kurs>> inMap){
        Map<String,List<Kurs>> outMap = new HashMap<>();
        for(Map.Entry<String,List<Kurs>> entry : inMap.entrySet()){
            List<Kurs> kopie = new ArrayList<>(entry.getValue());
            Collections.sort(kopie,
                    new Comparator<Kurs>() {
                        @Override
                        public int compare(Kurs o1, Kurs o2) {
                            return o1.getStart().compareTo(o2.getStart());
                        }
                    });
            outMap.put(entry.getKey(),kopie);
        }
        return outMap;
    }
}
