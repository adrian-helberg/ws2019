package ws1718.a2;

import ws1718.a2.donottouch.Kurs;
import ws1718.a2.donottouch.Studie;
import ws1819.a2.donottouch.utils.PathUtil;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StudieDataReader {
	
	private String filename;
	private String sep1;
	private String sep2;

	public StudieDataReader(String filename,String sep1, String sep2) {
		this.filename = filename;	
		this.sep1 = sep1;
		this.sep2 = sep2;
	}

	// 20 Pkt
	public List<Studie> readStudieList() throws FileNotFoundException{
		List<Studie> result = new ArrayList<>();

		URL url = getClass().getResource("../resources/" + filename);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(sep1);
				Studie studie = new Studie(split[0]);
				for (int i = 1; i < split.length; i++) {
					String[] s = split[i].split(sep2);
					Kurs kurs = new Kurs(Integer.parseInt(s[0]));
					studie.put(kurs, Integer.parseInt(s[1]));
				}
				result.add(studie);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
