package ws1718.a2;

import ws1718.a2.donottouch.Kurs;
import ws1718.a2.donottouch.Studie;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class StudieDataStatistics {

	// 3Pkt
	public static Integer punkteProStudie(Studie studie) {
		return studie.getPunkte().stream().mapToInt(Integer::intValue).sum();
	}

	// 5Pkt
	public static Studie cleversterStudie(List<Studie> lstd) {
		return lstd.stream().max(Comparator.comparingInt(StudieDataStatistics::punkteProStudie)).orElse(null);
	}

	// 5Pkt
	public static int anzahlKurseGesamt(List<Studie> lstd) {
		return lstd.stream().map(Studie::getKurse).mapToInt(Set::size).sum();
	}

	// 7Pkt
	public static Set<Kurs> belegteKurse(List<Studie> lstd) {
		return lstd.stream().flatMap(x -> x.getKurse().stream()).collect(Collectors.toSet());
	}
}
