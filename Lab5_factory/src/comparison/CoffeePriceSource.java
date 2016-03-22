package comparison;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CoffeePriceSource implements DataSource {

	private static final String LINK = "https://www.quandl.com/api/v3/datasets/WSJ/COFFEE_BRZL.json";

	@Override
	public String getName() {
		return "Coffee price";
	}

	@Override
	public String getUnit() {
		return "Dollars/pound";
	}

	@Override
	public Map<LocalDate, Double> getData() {
		UrlFetcher fetcher = new UrlFetcher(LINK);
		JsonToMapParser parser = new JsonToMapParser(fetcher.getContent());
		Map<String, Object> dataset = (Map) parser.getResult().get("dataset");
		List<List> data = (List<List>) dataset.get("data");
		List<LocalDate> dates = data.stream().map(list -> LocalDate.parse(list.get(0).toString()))
				.collect(Collectors.toList());
		List<Double> values = data.stream().map(list -> Double.parseDouble(list.get(1).toString()))
				.collect(Collectors.toList());
		Map<LocalDate, Double> result = new TreeMap<>();
		for (int i = 0; i < dates.size(); i++) {
			result.put(dates.get(i), values.get(i));
		}
		return result;
	}
}
