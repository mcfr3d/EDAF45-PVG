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
		assertEquals("--.--.--; Start?; 12.00.00; Omöjlig Totaltid?",temp.genResult());
	}

	@Test
	public void testNoFinishTime() {
		temp.addStart("12.00.00");
		assertEquals("--.--.--; 12.00.00; Slut?; Omöjlig Totaltid?",temp.genResult());
	}

	@Test
	public void testMultipleFinishTimes() {
		temp.addFinish("12.00.00");
		temp.addFinish("12.15.00");
		assertEquals("--.--.--; Start?; 12.00.00; Flera måltider? 12.15.00; Omöjlig Totaltid?", temp.genResult());
	}
	
	@Test
	public void testMultipleStartTimes() {
		temp.addStart("12.00.00");
		temp.addStart("12.15.00");
		assertEquals("--.--.--; 12.00.00; Slut?; Flera starttider? 12.15.00; Omöjlig Totaltid?", temp.genResult());
	}

	@Test
	public void testImpossibleTotalTime() {
		temp.addStart("12.00.00");
		temp.addFinish("12.10.00");
		assertEquals("00.10.00; 12.00.00; 12.10.00; Omöjlig Totaltid?", temp.genResult());
	}
	
	@Test
	public void testPossibleTotalTime() {
		temp.addStart("12.00.00");
		temp.addFinish("12.15.00");
		assertEquals("00.15.00; 12.00.00; 12.15.00", temp.genResult());
	}
}
