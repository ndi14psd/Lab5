package comparison;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class CoffeePriceSourceTest {

	@Test
	public void testGetData() {
		DataSource coffee = new CoffeePriceSource();
		assertFalse(coffee.getData().isEmpty());
		assertEquals(Double.valueOf(1.2065), coffee.getData().get(LocalDate.parse("2016-02-22")));
		assertEquals(Double.valueOf(1.2706), coffee.getData().get(LocalDate.parse("2015-12-10")));
		assertEquals(Double.valueOf(1.2728), coffee.getData().get(LocalDate.parse("2007-12-21")));
	}

}
