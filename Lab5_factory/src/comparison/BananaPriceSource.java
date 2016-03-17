package comparison;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BananaPriceSource implements DataSource {
	
	private static final String LINK = "https://www.quandl.com/api/v3/datasets/ODA/PBANSOP_USD.json";

	@Override
	public String getName() {
		return "Banana price"; 
	}

	@Override
	public String getUnit() {
		return "Dollars/metric ton";
	}

	@Override
	public Map<LocalDate, Double> getData() {
		UrlFetcher fetcher = new UrlFetcher(LINK);
		JsonToMapParser parser = new JsonToMapParser(fetcher.getContent());
		Map<String, Object> dataset = (Map) parser.getResult().get("dataset");
		List<List> data = (List<List>) dataset.get("data");
		List<LocalDate> dates = data.stream()
				.map(list -> LocalDate.parse(list.get(0).toString()))
				.collect(Collectors.toList());
		List<Double> values = data.stream()
				.map(list -> Double.parseDouble(list.get(1).toString()))
				.collect(Collectors.toList());
		Map<LocalDate, Double> result = new TreeMap<>();
		for (int i = 0; i < dates.size(); i++) {
			result.put(dates.get(i), values.get(i));
		}
		return result;
	}
	
	public static void main(String[] args) {
		new BananaPriceSource().getData();
	}

}
