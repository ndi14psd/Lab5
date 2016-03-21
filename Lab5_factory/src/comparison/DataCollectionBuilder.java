package comparison;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DataCollectionBuilder {
	private final DataSource xSource;
	private final DataSource ySource;
	private final Resolution resolution;
	private String title;

	public DataCollectionBuilder(DataSource xSource, DataSource ySource, Resolution resolution) {
		this.xSource = xSource;
		this.ySource = ySource;
		this.resolution = resolution;
	}

	public DataCollectionBuilder(DataSource xData, DataSource yData, Resolution resolution, String title) {
		this(xData, yData, resolution);
		this.title = title;
	}

	public String getTitle() {
		return (title != null) ? title : xSource.getName() + " / " + ySource.getName();
	}

	public DataCollection getResult() {
		Map<String, Double> xData = adjustToCorrectFormat(xSource.getData());
		Map<String, Double> yData = adjustToCorrectFormat(ySource.getData());
		Map<String, MatchedDataPair> finalResult = matchData(xData, yData);

		return new DataCollection(getTitle(), xSource.getUnit(), ySource.getUnit(), finalResult);
	}

	private Map<String, Double> adjustToCorrectFormat(final Map<LocalDate, Double> data) {
		Map<String, List<Double>> correctResolution = changeResolution(data);
		Map<String, Double> result = compressListInMap(correctResolution);
		return result;
	}
	
	private Map<String, List<Double>> changeResolution(final Map<LocalDate, Double> data) {
		Map<String, List<Double>> result = new TreeMap<>();
		
		for (Entry<LocalDate, Double> entry : data.entrySet()) {
			String currentKey = resolution.createLabel(entry.getKey());
			List<Double> valueToBeAdded = Arrays.asList(entry.getValue());
			result.merge(currentKey, valueToBeAdded, (oldValues, newValues) -> mergeLists(oldValues, newValues));
		}
		return result;
	}
	
	private List<Double> mergeLists(final List<Double> xList, final List<Double> yList) {
		List<Double> resultingList = new ArrayList<>();
		resultingList.addAll(xList);
		resultingList.addAll(yList);
		return resultingList;
	}
	
	private Map<String, Double> compressListInMap(final Map<String, List<Double>> map) {
		return map.keySet().stream().collect(Collectors.toMap(
						date -> date, 
						date -> roundTwoDecimals(averageOfList(map.get(date)))));
	}

	private double averageOfList(final List<Double> list) {
		return list.stream().mapToDouble(Double::valueOf).average().getAsDouble();
	}

	private double roundTwoDecimals(double d) {
		return ((double) Math.round(d * 100) / 100);
	}

	private Map<String, MatchedDataPair> matchData(final Map<String, Double> xData, final Map<String, Double> yData) {
		return xData.keySet().stream().filter(yData::containsKey).collect(Collectors.toMap(
						key -> key, 
						key -> new MatchedDataPair(xData.get(key), yData.get(key))));
	}
}