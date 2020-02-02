package ws1819.a2.donottouch.modelgenerator;

import ws1819.a2.donottouch.model.enums.VereinsName;
import ws1819.a2.donottouch.utils.RandomUtil;
import ws1819.a2.model.Verein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VereinGenerator {

    public static List<Verein> generateVereine(int anzahlKurse){
        RandomUtil.reset();
        List<Verein> vereine = new ArrayList<>();
        Arrays.asList(VereinsName.values()).forEach((name) -> {
            Verein verein = new Verein(name);
            verein.addAll(KursGenerator.generateKurse(anzahlKurse));
            vereine.add(verein);
        });
        return vereine;
    }

}
