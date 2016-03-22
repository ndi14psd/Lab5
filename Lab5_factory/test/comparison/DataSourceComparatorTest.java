package comparison;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DataSourceComparatorTest {

	private static final Map<LocalDate, Double> MAP_1 = new HashMap<>();
	private static final Map<LocalDate, Double> MAP_2 = new HashMap<>();
	private static final String COMPARED_STRING = "{ \"matchningar\":[{\"date\":    \"1887 11\",\"Source 1(Price per kg)\":\"5.1\",\"Source 2(Degrees)\":\"0.9\"} ]}";

	@Test
	public void testGetComparedString() {
		DataSource source1 = mock(DataSource.class);
		DataSource source2 = mock(DataSource.class);

		when(source1.getName()).thenReturn("Source 1");
		when(source1.getUnit()).thenReturn("Price per kg");
		when(source1.getData()).thenReturn(MAP_1);

		when(source2.getName()).thenReturn("Source 2");
		when(source2.getUnit()).thenReturn("Degrees");
		when(source2.getData()).thenReturn(MAP_2);

		DataSourceComparator comparator = new DataSourceComparator(source1, source2);
		assertEquals(comparator.getComparedData(), COMPARED_STRING);
	}

	static {
		MAP_1.put(LocalDate.of(2, 05, 1), 5.0);
		MAP_1.put(LocalDate.of(1887, 11, 21), 5.1);
		MAP_1.put(LocalDate.of(2018, 01, 10), 5.2);

		MAP_2.put(LocalDate.of(9999, 12, 30), 999999.0);
		MAP_2.put(LocalDate.of(1887, 11, 21), 0.9);
		MAP_2.put(LocalDate.of(2016, 03, 22), -2.0);
	}

}
