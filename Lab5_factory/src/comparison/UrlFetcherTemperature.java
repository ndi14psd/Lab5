package comparison;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

//public class CsvToMapParser {

// private final String link;
//
// public CsvToMapParser(String link) {
// this.link = link;
// }
//
// public Map<String, String> getResult() {
// UrlFetcher url = new UrlFetcher(link);
//// String csv = url.getContent();
// File file = new File("H:/Programvaruteknik/lab5/smhi-vader.txt");
//
// String r = "";
// String csv = "";
// try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
// while( (r = reader.readLine()) != null) {
// csv += r;
// }
// } catch (IOException e) {}
// System.out.println(csv);
//
// Map<String, String> map = new HashMap<>();
// String[] line = csv.split(";");
//
// for(int i = 0; i < line.length; i++){
// if(line[i].startsWith("Y"))
// map.put(line[i - 2], line[i - 1]);
// }
// return map;
// }
/**
 * @author thomas, Jonatan Högberg, Daniel Carlström
 * @version 28/2-2016 This class sorts out dates and temperature from the files.
 */
public class UrlFetcherTemperature {

	private final URL url;

	public UrlFetcherTemperature(String urlString) {
		try {
			url = new URL(urlString);
		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Map<LocalDate, Double> getContent() {

		Map<LocalDate, Double> temperature = new TreeMap<>();
		String temp;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			while (null != (temp = br.readLine())) {
				String[] collumns = temp.split(";");

				try {
					temperature.put(LocalDate.parse(collumns[2]), Double.parseDouble(collumns[3]));
				} catch (Exception e) {
					temperature.clear();
				}
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		return temperature;
	}

}
