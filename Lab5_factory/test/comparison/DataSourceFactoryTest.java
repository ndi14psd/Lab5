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
		assertEquals(new BananaPriceSource().getClass(), DataSourceFactory.create("bananas").getClass());
		assertEquals(new CoffeePriceSource().getClass(), DataSourceFactory.create("coffee").getClass());
	}
	
	@Test(expected=RuntimeException.class)
	public void testWrongValues() throws Exception {
		DataSourceFactory.create("invalid parameter!");
	}

}
