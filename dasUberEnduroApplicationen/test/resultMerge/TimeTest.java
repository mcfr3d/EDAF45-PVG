package resultMerge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TimeTest {

	Time t1;
	Time t2;
	
	@Before
	public void init() throws Exception {
		t1 = new Time("12.00.00");
		t2 = new Time("13.00.00");
		
	}
	
	@Test
	public void testRegularTime() {
		assertEquals(Time.diffAsString(t1, t2), "01.00.00");
	}

	@Test
	public void testNegativeTime() {
		assertEquals(Time.diffAsString(t2, t1), "--.--.--");
	}
}
