package comparison;

import java.util.HashMap;
import java.util.Map;

public class DataSourceFactory {
	
	private static Map<String, DataSource> sources = new HashMap<>();
	
	static {
		sources.put("goals", new FootballGoalsSource());
		sources.put("temperatures", new TemperatureSource());
	}
	
	private DataSourceFactory() { }
	
	public static DataSource get(String source ) {
		return sources.get(source);
	}

}
