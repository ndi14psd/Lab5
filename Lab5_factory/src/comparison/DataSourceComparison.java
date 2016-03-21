package comparison;

import java.util.Map.Entry;

import servlet.JsonFormatter;


public class DataSourceComparison {

	private DataCollection result;
	private DataSource sourceOne;
	private DataSource sourceTwo;

	public DataSourceComparison(String source1, String source2) {
		sourceOne = DataSourceFactory.create(source1);
		sourceTwo = DataSourceFactory.create(source2);
		result = new DataCollectionBuilder(sourceOne, sourceTwo, Resolution.MONTH).getResult();
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
	
	public static void main(String[] args) {
		String bananas = "bananas";
		String goals = "goals";
		String comp = new DataSourceComparison(bananas, goals).getComparedData();
		System.out.println(new JsonFormatter().format(comp));
	}
}
