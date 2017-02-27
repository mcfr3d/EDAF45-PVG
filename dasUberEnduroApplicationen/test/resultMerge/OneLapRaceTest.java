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
		temp.addFinish("12.00.00");
		assertEquals("--.--.--; Start?; 12.00.00", temp.genResult());
	}

	@Test
	public void testNoFinishTime() {
		temp.addStart("12.00.00");
		assertEquals("--.--.--; 12.00.00; Slut?", temp.genResult());
	}

	@Test
	public void testMultipleFinishTimes() {
		temp.addFinish("12.00.00");
		temp.addFinish("12.15.00");
		assertEquals("--.--.--; Start?; 12.00.00; Flera måltider? 12.15.00", temp.genResultWithErrors(null));
	}

	@Test
	public void testMultipleStartTimes() {
		temp.addStart("12.00.00");
		temp.addStart("12.15.00");
		assertEquals("--.--.--; 12.00.00; Slut?; Flera starttider? 12.15.00", temp.genResultWithErrors(null));
	}

	@Test
	public void testImpossibleTotalTime() {
		temp.addStart("12.00.00");
		temp.addFinish("12.10.00");
		assertEquals("00.10.00; 12.00.00; 12.10.00; Omöjlig Totaltid?", temp.genResultWithErrors(null));
	}

	@Test
	public void testPossibleTotalTime() {
		temp.addStart("12.00.00");
		temp.addFinish("12.15.00");
		assertEquals("00.15.00; 12.00.00; 12.15.00", temp.genResult());
	}

	@Test
	public void testSortedByTotalTime() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		a.addStart("12.00.00");
		a.addFinish("13.20.00");
		b.addStart("12.30.00");
		b.addFinish("13.45.00");
		assertEquals(1, a.compareTo(b));
	}

	@Test
	public void testSortedMissingFinishTime() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		a.addStart("12.00.00");
		b.addStart("12.30.00");
		b.addFinish("12.45.00");
		assertEquals(1, a.compareTo(b));
	}

	@Test
	public void testSortedMissingStartTime() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		a.addFinish("12.20.00");
		b.addStart("12.30.00");
		b.addFinish("12.45.00");
		assertEquals(1, a.compareTo(b));
	}

	@Test
	public void testSortedAllMissingTime() {
		OneLapRace a = new OneLapRace();
		OneLapRace b = new OneLapRace();
		a.addFinish("12.20.00");
		b.addStart("12.30.00");
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
		a.addStart("12.30.00");
		a.addFinish("12.45.00");
		assertEquals(-1, a.compareTo(b));
	}

}
