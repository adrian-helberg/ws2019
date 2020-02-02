package ws1819.a2.donottouch.modelgenerator;

import ws1819.a2.model.Verein;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class VereinWriter {
    public static void main(String[] args) throws  IOException{
        writeData();
    }
    public static void writeData() throws IOException {
        try(PrintWriter pw = new PrintWriter(GeneratorKonstanten.VEREIN_DATEI)) {
            List<Verein> vereine = VereinGenerator.generateVereine(GeneratorKonstanten.MAX_KURSE);
            vereine.forEach(verein -> pw.println(verein.toFormattedString(GeneratorKonstanten.DELIM1, GeneratorKonstanten.DELIM2)));
        }
    }
}
