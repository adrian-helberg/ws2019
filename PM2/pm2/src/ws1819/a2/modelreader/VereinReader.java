package ws1819.a2.modelreader;

import ws1819.a2.donottouch.model.Kurs;
import ws1819.a2.donottouch.model.enums.Sportart;
import ws1819.a2.donottouch.model.enums.VereinsName;
import ws1819.a2.donottouch.modelgenerator.GeneratorKonstanten;
import ws1819.a2.donottouch.utils.DateTimeUtil;
import ws1819.a2.donottouch.utils.PathUtil;
import ws1819.a2.model.Verein;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Gesamt: 17 Pkt
public class VereinReader {

    private static final String RESOURCE_NAME = "VereinsDaten";


    public static List<Verein> readData() throws IOException {
        List<Verein> result = new ArrayList<>();
        URL url = VereinReader.class.getResource("../../resources/" + RESOURCE_NAME);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(GeneratorKonstanten.DELIM1);
                Verein verein = new Verein(VereinsName.fromNameString(split[0]));

                for (int i = 1; i < split.length; i++) {
                    String[] split2 = split[i].split(GeneratorKonstanten.DELIM2);
                    Kurs kurs = new Kurs(
                            Sportart.fromNameString(split2[0]),
                            DateTimeUtil.fromString(split2[1]),
                            DateTimeUtil.durationFromString(split2[2]),
                            Integer.parseInt(split2[3])
                    );
                    verein.add(kurs);
                }
                result.add(verein);
            }
        }
        return result;
    }
}
