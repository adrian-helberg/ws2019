package ws1819.a2.donottouch.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Random;

public class DateTimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final int MONTHS = 12;
    private static final String DURATION_PREFIX = "PT";

    public static LocalDateTime fromString(String localDateTimestring) {
        return LocalDateTime.parse(localDateTimestring, DATE_TIME_FORMATTER);
    }

    public static String toString(LocalDateTime localDateTime){
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    public static Duration durationFromString(String durationString){
        return  Duration.parse(DURATION_PREFIX+durationString);
    }

    public static String durationToString(Duration duration) {
        return duration.toString().substring(2);
    }

   public static LocalDateTime generateDateTime(LocalDate minDate, LocalDate maxDate, LocalTime minStartTime, LocalTime maxEndTime, int timeIntervallInMinutes, Duration minDauer, Duration maxDauer) {
        Random rand = RandomUtil.getRand();
        int year = minDate.plusYears(rand.nextInt(maxDate.getYear()-minDate.getYear())).getYear();
        int month = rand.nextInt(MONTHS)+1;
        int dayOfMonth = rand.nextInt(maxDate.with(TemporalAdjusters.lastDayOfMonth()).getMonthValue())+1;
        int hour = minStartTime.plusHours(rand.nextInt(maxEndTime.getHour()-minStartTime.getHour())).getHour();
        Duration dauer = generateDuration(minDauer,maxDauer,timeIntervallInMinutes);
        return LocalDateTime.of(LocalDate.of(year,month,dayOfMonth),
                LocalTime.of(hour+dauer.toHoursPart(),dauer.toMinutesPart()));
    }

    public static Duration generateDuration(Duration minDauer, Duration maxDauer,int timeIntervallInMinutes){
        Random rand = RandomUtil.getRand();
        int minutes = (int)minDauer.toMinutes() +rand.nextInt((int)maxDauer.minus(minDauer).toMinutes()/timeIntervallInMinutes+1)*timeIntervallInMinutes;
        return Duration.ofMinutes(minutes);
    }
}
