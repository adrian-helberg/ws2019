package ws1819.a2.donottouch.modelgenerator;

import ws1819.a2.donottouch.model.Kurs;
import ws1819.a2.donottouch.model.enums.Sportart;
import ws1819.a2.donottouch.utils.DateTimeUtil;
import ws1819.a2.donottouch.utils.RandomUtil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KursGenerator {

    private static final int MIN_DURATION_IN_MINUTES = 60;
    private static final int MAX_DURATION_IN_MINUTES = 120;
    private static final int TIME_INTERVALL_IN_MINUTES = 30;
    private static final LocalTime MIN_START_TIME = LocalTime.of(10,00);
    private static final LocalTime MAX_END_TIME  = LocalTime.of(22,00);
    private static final LocalDate MIN_DATE = LocalDate.of(2018,1,1);
    private static final LocalDate MAX_DATE= LocalDate.of(2019,2,24).with(TemporalAdjusters.lastDayOfMonth());
    private static final Duration MIN_DAUER = Duration.ofMinutes(MIN_DURATION_IN_MINUTES);
    private static final Duration MAX_DAUER = Duration.ofMinutes(MAX_DURATION_IN_MINUTES);
    private static final int MIN_TEILNEHMER = 8;
    private static final int MAX_TEILNEHMER = 30;


    public static List<Kurs> generateKurse(int num){
        List<Kurs> kurse = new ArrayList<>();
        Random rand = RandomUtil.getRand();
        int anzahlKurse = rand.nextInt(num)+1;
        for (int i = 0; i< anzahlKurse; i++) {
            LocalDateTime now = LocalDateTime.now();
            Kurs kurs = new Kurs(Sportart.values()[rand.nextInt(Sportart.values().length)],
                    DateTimeUtil.generateDateTime(MIN_DATE,MAX_DATE,MIN_START_TIME,MAX_END_TIME,TIME_INTERVALL_IN_MINUTES,MIN_DAUER,MAX_DAUER), DateTimeUtil.generateDuration(MIN_DAUER,MAX_DAUER,TIME_INTERVALL_IN_MINUTES),MIN_TEILNEHMER +rand.nextInt(MAX_TEILNEHMER - MIN_TEILNEHMER));
            kurse.add(kurs);
        }
        return kurse;
    }
}
