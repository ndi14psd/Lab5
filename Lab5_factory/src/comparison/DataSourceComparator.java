package comparison;

import java.util.Map.Entry;

import servlet.JsonFormatter;


public class DataSourceComparator {

	private DataCollection result;
	private DataSource sourceOne;
	private DataSource sourceTwo;

	public DataSourceComparator(String source1, String source2) {
		sourceOne = DataSourceFactory.create(source1);
		sourceTwo = DataSourceFactory.create(source2);
		result = new DataCollectionBuilder(sourceOne, sourceTwo, Resolution.DAY).getResult();
		System.out.println("Den gjorde jämförelsen.");
	}

	public String getComparedData() {
		if(result.getData().isEmpty()) 
			return "No matches between data sources!";
		
		String s = "{ \"matchningar\":[";
		for (Entry<String, MatchedDataPair> entry : result.getData().entrySet()) {
			s += "{\"date\":    \"" + entry.getKey() + "\",\"" + sourceOne.getName() + "(" + sourceOne.getUnit() + ")\":\"" + entry.getValue().getXValue()
					+ "\",\"" + sourceTwo.getName() + "(" + sourceTwo.getUnit() + ")\":\"" + entry.getValue().getYValue() + "\"},";
		}
		return s.substring(0, s.lastIndexOf(',')) + " ]}";
	}
}
