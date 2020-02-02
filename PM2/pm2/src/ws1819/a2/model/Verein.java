package ws1819.a2.model;

import ws1819.a2.donottouch.model.Kurs;
import ws1819.a2.donottouch.model.enums.VereinsName;

import java.util.*;
import java.util.stream.Collectors;

public class Verein {
    private final VereinsName vereinsName;
    private List<Kurs> kurse;

    public Verein(VereinsName name) {
        this.vereinsName = name;
        kurse = new ArrayList();
    }

    public Verein(Verein vorlage){
        this(vorlage.vereinsName);
        this.addAll(vorlage.kurse);
    }

    public void add(Kurs kurs) {
        this.kurse.add(kurs);
    }

    public void addAll(List<Kurs> kurse) {
        this.kurse.addAll(new ArrayList<>(kurse));
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Verein verein = (Verein) o;
        return vereinsName == verein.vereinsName &&
                Objects.equals(kurse, verein.kurse);
    }

    // TODO
    // 4 Pkt
    public Map<String,List<Kurs>> gruppiereNachJahrMonat(){
        return kurse.stream().collect(Collectors.groupingBy(Kurs::getLocalDateYearMonthOnly));
    }

    // TODO
    // 4 Pkt
    public Kurs fruehesterKurs(){
        return kurse.stream().min(Comparator.comparing(Kurs::getStart)).orElse(null);
    }


    public List<Kurs> getKurse() {
        return new ArrayList<>(kurse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vereinsName, kurse);
    }

    @Override
    public String toString() {
        return String.format("%s[%s]",vereinsName,kurse);
    }

    public String toFormattedString(String delim1, String delim2){
        List<String> formatierteKurse = kurse.stream().map(k -> k.toFormattedString(delim2)).collect(Collectors.toList());
        String formatierteKurseString = formatierteKurse.stream().collect(Collectors.joining(delim1));
        return String.format("%s%s%s",vereinsName,delim1,formatierteKurseString);
    }
}
