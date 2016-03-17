package comparison;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class TemperatureSource implements DataSource {

	private final String csvLink;

	public TemperatureSource() {
		csvLink = "http://opendata-download-metobs.smhi.se/api/version/latest/parameter/2/station/107420/period/corrected-archive/data.csv";
	}

	@Override
	public String getName() {
		return "Temperature";
	}

	@Override
	public String getUnit() {
		return "Degrees Celcius";
	}
	
	@Override
	public Map<LocalDate, Double> getData() {
		UrlFetcherTemperature parser = new UrlFetcherTemperature(csvLink);
		return parser.getContent();
	}
}
