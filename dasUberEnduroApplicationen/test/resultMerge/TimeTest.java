package resultMerge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TimeTest {

	Time t1;
	Time t2;
	Time t3;
	Time t4;
	Time t5;
	Time t6;
	
	@Before
	public void init() throws Exception {
		t1 = new Time("12.00.00");
		t2 = new Time("13.00.00");
		t3 = new Time("23.00.00");
		t4 = new Time("01.00.00");
		t5 = new Time("22.00.00");
		t6 = new Time("21.59.59");
	}
	
	@Test
	public void testRegularTime() {
		assertEquals("01.00.00", Time.diff(t2, t1).toString());
	}

	@Test
	public void test23Hours() {
		assertEquals( "23.00.00", Time.diff(t1, t2).toString());
	}
	
	@Test
	public void testPastMidnightTime() {
		assertEquals("02.00.00", Time.diff(t4, t3).toString());
	}
	
	@Test
	public void testEtappTime23HoursAndPastMidnight() {
		assertEquals("23.00.00", Time.diff(t5, t3).toString());
	}
	
	@Test
	public void testEtapp24HourRace() {
		assertEquals("00.00.00", Time.diff(t3, t3).toString());

	}
	
	@Test
	public void testEtappLongestPossibleTime() {
		assertEquals("23.59.59", Time.diff(t6, t5).toString());

	}
}
