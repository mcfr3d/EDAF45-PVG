package resultMerge;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.*;

import resultMerge.Racer;

public class OneLapRaceTest {
	OneLapRace temp;

	@Before
	public void init() {
		temp = new OneLapRace();
	}

	@Test
	public void testNoStartTime() {
		temp.addTime(new Time("12.00.00", false));
		assertEquals("--.--.--; Start?; 12.00.00", temp.genResult());
	}

	@Test
	public void testNoFinishTime() {
		temp.addTime(new Time("12.00.00", true));
		assertEquals("--.--.--; 12.00.00; Slut?", temp.genResult());
	}

	@Test
	public void testMultipleFinishTimes() {
		temp.addTime(new Time("12.00.00", false));
		temp.addTime(new Time("12.15.00", false));
		assertEquals("--.--.--; Start?; 12.00.00; Flera måltider? 12.15.00", temp.genResultWithErrors(null));
	}

	@Test
	public void testMultipleStartTimes() {
		temp.addTime(new Time("12.00.00", true));
		temp.addTime(new Time("12.15.00", true));
		assertEquals("--.--.--; 12.00.00; Slut?; Flera starttider? 12.15.00", temp.genResultWithErrors(null));
	}

	@Test
	public void testImpossibleTotalTime() {
		temp.addTime(new Time("12.00.00", true));
		temp.addTime(new Time("12.10.00", false));
		assertEquals("00.10.00; 12.00.00; 12.10.00; Omöjlig Totaltid?", temp.genResultWithErrors(null));
	}

	@Test
	public void testPossibleTotalTime() {
		temp.addTime(new Time("12.00.00", true));
		temp.addTime(new Time("12.15.00", false));
		assertEquals("00.15.00; 12.00.00; 12.15.00", temp.genResult());
	}

	@Test
	public void testSortedByTotalTime() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		a.addTime(new Time("12.00.00", true));
		a.addTime(new Time("13.20.00", false));
		b.addTime(new Time("12.30.00", true));
		b.addTime(new Time("13.45.00", false));
		assertEquals(1, a.compareTo(b));
	}

	@Test
	public void testSortedMissingFinishTime() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		a.addTime(new Time("12.00.00", true));
		b.addTime(new Time("12.30.00", true));
		b.addTime(new Time("12.45.00", false));
		assertEquals(1, a.compareTo(b));
	}

	@Test
	public void testSortedMissingStartTime() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		a.addTime(new Time("12.20.00", false));
		b.addTime(new Time("12.30.00", true));
		b.addTime(new Time("12.45.00", false));
		assertEquals(1, a.compareTo(b));
	}

	@Test
	public void testSortedAllMissingTime() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		a.addTime(new Time("12.20.00", false));
		b.addTime(new Time("12.30.00", true));
		assertEquals(0, a.compareTo(b));
	}

	@Test
	public void testSortedAllNoTimes() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		assertEquals(0, a.compareTo(b));
	}

	@Test
	public void testSortedOneHaveNoTimes() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		a.addTime(new Time("12.30.00", true));
		a.addTime(new Time("12.45.00", false));
		assertEquals(-1, a.compareTo(b));
	}

}
