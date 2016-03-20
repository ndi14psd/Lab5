package comparison;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataCollectionBuilder {
	private DataSource xSource;
	private DataSource ySource;
	private Resolution resolution;
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
		Map<String, Double> xData = adjustToResolution(xSource.getData());
		Map<String, Double> yData = adjustToResolution(ySource.getData());
		Map<String, MatchedDataPair> finalResult = matchData(xData, yData);
		
		return new DataCollection(getTitle(), xSource.getUnit(), ySource.getUnit(), finalResult);
	}

	private Map<String, Double> adjustToResolution(Map<LocalDate, Double> data) {
		Map<String, List<Double>> result = getWithProperResolution(data);
		return result.keySet().stream()
				.collect(Collectors.toMap(date -> date, date -> roundTwoDecimals(averageOfList(result.get(date)))));
	}

	private Map<String, List<Double>> getWithProperResolution(Map<LocalDate, Double> data) {
		Map<String, List<Double>> result = new TreeMap<>();
		for (Entry<LocalDate, Double> entry : data.entrySet()) {
			result.merge(resolution.createLabel(entry.getKey()), Arrays.asList(entry.getValue()), 
					(existingList, newList) -> Stream.concat(existingList.stream(), newList.stream()).collect(Collectors.toList()));
		}
		return result;
	}

	private double averageOfList(List<Double> list) {
		return list.stream().mapToDouble(Double::valueOf).average().getAsDouble();
	}

	private double roundTwoDecimals(double d) {
		return ((double) Math.round(d * 100) / 100);
	}

	private Map<String, MatchedDataPair> matchData(Map<String, Double> xData, Map<String, Double> yData) {
		return xData.keySet().stream().filter(yData::containsKey)
				.collect(Collectors.toMap(key -> key, key -> new MatchedDataPair(xData.get(key), yData.get(key))));
	}


}