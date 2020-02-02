package ws1617.a2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Klasse für die Repräsentation von Containern, die mit Paletten beladen
 * werden. Gesamtpunktzahl: 13 Pkt
 * 
 * @author Birgit Wendholt
 *
 */
public class Container {

	private int id;
	private int nettoGewicht;
	private List<Palette> inhalt = new ArrayList<>();
	private static int counter;

	/**
	 * Konstruktor ist gegeben
	 *
	 * @param nettoGewicht
	 *            Nettogewicht des Containers in kg (ganzzahlig).
	 */
	public Container(int nettoGewicht) {
		this.id = ++counter;
		this.nettoGewicht = nettoGewicht;
	}

	public Palette paletteMitId(String id){
		for(Palette pal:inhalt ) {
			if (pal.getId().equals(id)) return pal;
		}
		return null;
	}
	public String getId() {
		return "C"+id;
	}

	/**
	 * Aufbereitung als Zeichenkette ist gegeben.
	 */
	@Override
	public String toString() {
		return String.format("%s %2.2f€/%4.2fkg:%s\n", getId(),  wert(), gewicht(), inhalt);
	}
	
	/*
	 * Lädt eine Palette in den Container
	 */
	public void beladen(Palette pal){
		inhalt.add(pal);
	}

	/////!!!!!!!!!!!!TODOS!!!!!!!!!!!!!!!!!!!/////
	/**
	 * a. und b. zusammen 7 Pkt
	 * TODO Bestimmt das Gewicht des Containers aus dem Nettogewicht und
	 * der Summe der Gewichte aller Paletten.
	 * 
	 * @return Gewicht (in kg) als double
	 */
	public double gewicht() {
		return inhalt.stream().mapToDouble(Palette::gewicht).sum() + nettoGewicht;
	}

	/**
	 * TODO  Bestimmt den Wert des Containers aus der Summe der Werte
	 * aller Paletten.
	 * 
	 * @return Wert (in €) als double
	 */
	public double wert() {
		return inhalt.stream().mapToDouble(Palette::wert).sum();
	}

	/**
	 * TODO (6Pkt) Bestimmt die GewichtKlasse des Containers. Testet fuer jede
	 * Gewichtsklasse, ob das Gewicht des Containers in der Klasse enthalten
	 * ist. Lösung mit GewichtsKlasse#enthaltenIn(double), einem Strom über die
	 * Gewichtsklassen und einer geeignete Filtermethode.
	 * 
	 * @return GewichtsKlasse des Containers
	 */
	public GewichtsKlasse gewichtsKlasse() {
		return Arrays.stream(GewichtsKlasse.values())
				.filter(x -> x.enthaltenIn(gewicht()))
				.collect(Collectors.toList()).get(0);
	}


}
