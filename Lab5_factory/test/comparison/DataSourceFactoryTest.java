package comparison;

import static org.junit.Assert.*;
import org.junit.Test;

public class DataSourceFactoryTest {

	@Test
	public void testGetCorrectDataSources() {
		DataSource goals = new FootballGoalsSource();
		DataSource temperatures = new TemperatureSource();
		assertEquals(goals.getClass(), DataSourceFactory.create("goals").getClass());
		assertEquals(temperatures.getClass(), DataSourceFactory.create("temperatures").getClass());
	}
	
	@Test
	public void testWrongValues() throws Exception {
		assertEquals(null, DataSourceFactory.create("invalid parameter!"));
	}

}
