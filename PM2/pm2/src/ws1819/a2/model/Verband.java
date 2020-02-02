package ws1819.a2.model;

import ws1819.a2.donottouch.model.Kurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Verband {

    List<Verein> vereine;
    public Verband(){
        this.vereine = new ArrayList<>();
    }
    public void add(Verein verein){
        this.vereine.add(verein);
    }

    public void addAll(List<Verein> vereine){
        this.vereine.addAll(vereine);
    }

    // 7 Pkt
    public Map<String,List<Kurs>> gruppiereNachJahr(){
         return vereine.stream().flatMap(x -> x.getKurse().stream()).collect(Collectors.groupingBy(x -> x.getLocalDateYearMonthOnly()));
    }
}
