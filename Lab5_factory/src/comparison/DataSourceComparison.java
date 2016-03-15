package comparison;

import java.util.Map.Entry;


public class DataSourceComparison {

	private DataCollection result;

	public DataSourceComparison(String source1, String source2) {
		DataSource sourceOne = DataSourceFactory.get(source1);
		DataSource sourceTwo = DataSourceFactory.get(source2);
		result = new DataCollectionBuilder(sourceOne, sourceTwo, Resolution.DAY).getResult();
	}

	public String getComparedData() {
		String s = "{ \"matchningar\":[";
		for (Entry<String, MatchedDataPair> entry : result.getData().entrySet()) {
			s += "{\"date\":    \"" + entry.getKey() + "\",\"goals\":   \"" + entry.getValue().getXValue()
					+ "\",\"degrees\": \"" + entry.getValue().getYValue() + "\"},";
		}
		return s.substring(0, s.lastIndexOf(',')) + " ]}";
	}

}
