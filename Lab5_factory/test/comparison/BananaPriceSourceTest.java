package comparison;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class BananaPriceSourceTest {

	@Test
	public void testGetData() {
		DataSource bananas = new BananaPriceSource();
		assertFalse(bananas.getData().isEmpty());
		assertEquals(Double.valueOf(932.32), bananas.getData().get(LocalDate.parse("2015-12-31")));
		assertEquals(Double.valueOf(911.6), bananas.getData().get(LocalDate.parse("2013-06-30")));
		assertEquals(Double.valueOf(432.54), bananas.getData().get(LocalDate.parse("1992-07-31")));
	}

}
