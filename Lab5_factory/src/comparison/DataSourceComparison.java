package comparison;

import java.util.Map.Entry;


public class DataSourceComparison {

	private DataCollection result;
	private DataSource sourceOne;
	private DataSource sourceTwo;

	public DataSourceComparison(String source1, String source2) {
		sourceOne = DataSourceFactory.get(source1);
		sourceTwo = DataSourceFactory.get(source2);
		result = new DataCollectionBuilder(sourceOne, sourceTwo, Resolution.DAY).getResult();
		System.out.println("Den gjorde jämförelsen.");
	}

	public String getComparedData() {
		String s = "{ \"matchningar\":[";
		for (Entry<String, MatchedDataPair> entry : result.getData().entrySet()) {
			s += "{\"date\":    \"" + entry.getKey() + "\",\"" + sourceOne.getName() + "(" + sourceOne.getUnit() + ")\":\"" + entry.getValue().getXValue()
					+ "\",\"" + sourceTwo.getName() + "(" + sourceTwo.getUnit() + ")\":\"" + entry.getValue().getYValue() + "\"},";
		}
		return s.substring(0, s.lastIndexOf(',')) + " ]}";
	}
	
	public static void main(String[] args) {
		String source1 = "temperatures";
		String source2 = "goals";
		String comp = new DataSourceComparison(source1, source2).getComparedData();
		System.out.println(comp);
	}

}
