package comparison;

import java.util.Map.Entry;

import servlet.JsonFormatter;


public class DataSourceComparator {

	private DataCollection result;
	private DataSource sourceOne;
	private DataSource sourceTwo;

	public DataSourceComparator(DataSource sourceOne, DataSource sourceTwo) {
		this.sourceOne = sourceOne;
		this.sourceTwo = sourceTwo;
		result = new DataCollectionBuilder(sourceOne, sourceTwo, Resolution.MONTH).getResult();
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
