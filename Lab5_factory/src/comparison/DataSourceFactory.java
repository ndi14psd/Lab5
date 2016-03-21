package comparison;

import java.util.HashMap;
import java.util.Map;

public class DataSourceFactory {
	
	private final static Map<String, Class<? extends DataSource>> sources = new HashMap<>();
	
	static {
		sources.put("goals", FootballGoalsSource.class);
		sources.put("temperatures", TemperatureSource.class);
		sources.put("bananas", BananaPriceSource.class);
	}
	
	private DataSourceFactory() { }
	
	public static DataSource create(String source) {
		try {
			return sources.get(source).newInstance();
		} catch (InstantiationException | IllegalAccessException | NullPointerException e) {
			throw new RuntimeException(e);
		}
	}

}
