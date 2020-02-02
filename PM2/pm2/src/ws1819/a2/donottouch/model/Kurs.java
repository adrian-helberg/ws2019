package ws1819.a2.donottouch.model;

import ws1819.a2.donottouch.model.enums.Sportart;
import ws1819.a2.donottouch.utils.DateTimeUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Kurs {


    private final Sportart sportart;
    private final LocalDateTime start;
    private final Duration dauer;
    private final int teilnehmer;

    public Kurs(Sportart sportart, LocalDateTime start, Duration dauer, int teilnehmer){
        this.sportart = sportart;
        this.start = start;
        this.dauer = dauer;
        this.teilnehmer = teilnehmer;
    }

    public Kurs copyOfPlus(int days) {
        return new Kurs(this.sportart, this.start.plusDays(days),this.dauer,this.teilnehmer);
    }

    public LocalDateTime getStart(){
        return start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kurs kurs = (Kurs) o;
        return teilnehmer == kurs.teilnehmer &&
                sportart == kurs.sportart &&
                Objects.equals(start, kurs.start) &&
                Objects.equals(dauer, kurs.dauer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sportart, start, dauer, teilnehmer);
    }

    @Override
    public String toString() {
        return "K(" + sportart +
                ", " + DateTimeUtil.toString(start) +
                ", " + DateTimeUtil.durationToString(dauer) +
                ", " + teilnehmer +
                ')';
    }

    public String toFormattedString(String delim) {
        return String.format("%s%5$s%s%5$s%s%5$s%s",sportart, DateTimeUtil.toString(start), DateTimeUtil.durationToString(dauer),teilnehmer,delim);
    }

    public String getLocalDateYearMonthOnly() {
        return String.format("%s-%s",start.getYear(),start.getMonthValue());
    }
}
