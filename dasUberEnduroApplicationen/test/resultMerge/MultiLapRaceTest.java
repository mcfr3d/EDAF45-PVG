package resultMerge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MultiLapRaceTest {
	MultiLapRace temp;
	MultiLapRace temp2;

	
	@Before
	public void init() {
		temp = new MultiLapRace();
		temp.resetStatic();
	}
	
	@Test
	public void testOneLap() {
		MultiLapRace.setMaxLaps(1);
		MultiLapRace.setStipulatedTime(new Time("00.11.00"));
		temp.addTime(new Time("00.00.00", true));
		temp.addTime(new Time("01.00.00", false));
		assertEquals("1; 01.00.00; 01.00.00; 00.00.00; 01.00.00", temp.genResultWithErrors(null));
	}
	@Test
	public void testTwoLap() {
		MultiLapRace.setMaxLaps(2);
		MultiLapRace.setStipulatedTime(new Time("01.11.00"));
		temp.addTime(new Time("00.00.00", true));
		temp.addTime(new Time("01.00.01", false));
		temp.addTime(new Time("02.00.03", false));
		assertEquals("2; 02.00.03; 01.00.01; 01.00.02; 00.00.00; 01.00.01; 02.00.03", temp.genResultWithErrors(null));
	}
	@Test
	public void testTwoDrivers() {
		MultiLapRace.setMaxLaps(2);
		MultiLapRace.setStipulatedTime(new Time("01.11.00"));
		temp2 = new MultiLapRace(); 
		temp.addTime(new Time("00.00.00", true));
		temp.addTime(new Time("01.00.00", false));
		temp.addTime(new Time("02.00.00", false));
		
		temp2.addTime(new Time("00.00.00", true));
		temp2.addTime(new Time("02.00.00", false));
		assertEquals("1; 02.00.00; 02.00.00; ; 00.00.00; ; 02.00.00", temp2.genResultWithErrors(null));
		assertEquals("2; 02.00.00; 01.00.00; 01.00.00; 00.00.00; 01.00.00; 02.00.00", temp.genResultWithErrors(null));
	}
	
	/*
	@Test
	public void testNoStart() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.41.00"));
		temp.addTime("12.30.00");
		temp.addTime("13.00.00");
		temp.addTime("13.23.34");
		assertEquals("0; --.--.--; ; ; ; Start?; 12.30.00; 13.00.00; Slut?", temp.genResultWithErrors());
	}*/
	
	@Test
	public void testNoFinish() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("03.39.00"));
		temp.addTime(new Time("12.01.00", true));
		assertEquals("0; --.--.--; ; ; ; 12.01.00; ; ; Slut?", temp.genResultWithErrors(null));
	}
	
	@Test
	public void testMultipleStartTimes(){
		MultiLapRace.setStipulatedTime(new Time("00.42.00"));
		MultiLapRace.setMaxLaps(3);
		temp.addTime(new Time("12.02.00", true));
		temp.addTime(new Time("12.05.00", true));
		temp.addTime(new Time("12.22.00", false));
		temp.addTime(new Time("12.42.00", false));
		temp.addTime(new Time("13.05.06", false));
		assertEquals("3; 01.03.06; 00.20.00; 00.20.00; 00.23.06; 12.02.00; 12.22.00; 12.42.00; 13.05.06; Flera starttider? 12.05.00 ", temp.genResultWithErrors(null));
	}
	
	@Test
	public void testImpossibleLapTime() {
		MultiLapRace.setStipulatedTime(new Time("00.42.00"));
		MultiLapRace.setMaxLaps(3);
		temp.addTime(new Time("12.03.00", true));
		temp.addTime(new Time("12.23.00", false));
		temp.addTime(new Time("12.43.00", false));
		temp.addTime(new Time("12.52.07", false));
		System.out.println(temp.genResultWithErrors(null));
		assertEquals("3; 00.49.07; 00.20.00; 00.20.00; 00.09.07; 12.03.00; 12.23.00; 12.43.00; 12.52.07; Omöjlig varvtid?", temp.genResultWithErrors(null));
	}

	@Test
	public void testSortedDifferentLaps() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.39.00"));
		MultiLapRace a = new MultiLapRace();
		a.addTime(new Time("12.00.00", true));
		a.addTime(new Time("12.20.00", false));
		a.addTime(new Time("12.40.00", false));
		MultiLapRace b = new MultiLapRace();
		b.addTime(new Time("12.00.00", true));
		b.addTime(new Time("12.17.00", false));
		b.addTime(new Time("12.38.00", false));
		b.addTime(new Time("12.59.00", false));
		assertEquals(1,a.compareTo(b));
	}

	@Test
	public void testSortedDifferentLapsOneInvalid() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.39.00"));
		MultiLapRace a = new MultiLapRace();
		a.addTime(new Time("12.00.00", true));
		a.addTime(new Time("12.20.00", false));
		a.addTime(new Time("12.40.00", false));
		MultiLapRace b = new MultiLapRace();
		b.addTime(new Time("12.17.00", false));
		b.addTime(new Time("12.38.00", false));
		b.addTime(new Time("12.59.00", false));
		assertEquals(-1,a.compareTo(b));
	}

	@Test
	public void testSortedDifferentLapsInvalidLap() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.39.00"));
		MultiLapRace a = new MultiLapRace();
		a.addTime(new Time("12.00.00", true));
		a.addTime(new Time("12.10.00", false));// Less than minimum lap time -> invalid
		a.addTime(new Time("12.40.00", false));
		MultiLapRace b = new MultiLapRace();
		b.addTime(new Time("12.00.00", true));
		b.addTime(new Time("12.17.00", false));
		b.addTime(new Time("12.38.00", false));
		b.addTime(new Time("12.59.00", false));
		assertTrue(0 < a.compareTo(b));
	}
	
	@Test
	public void testSortedAllInvalid() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.11.00"));
		MultiLapRace a = new MultiLapRace();
		a.addTime(new Time("12.10.00", false));
		a.addTime(new Time("12.40.00", false));
		MultiLapRace b = new MultiLapRace();
		b.addTime(new Time("12.00.00", true));
		b.addTime(new Time("12.1.00", false));
		b.addTime(new Time("12.2.00", false));
		b.addTime(new Time("12.3.00", false));
		assertEquals(0,a.compareTo(b));
	}
	
	@Test
	public void testSortedSameLaps() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.41.00"));
		MultiLapRace a = new MultiLapRace();
		a.addTime(new Time("12.00.00", true));
		a.addTime(new Time("12.20.00", false));
		a.addTime(new Time("12.40.00", false));
		a.addTime(new Time("13.00.00", false));
		MultiLapRace b = new MultiLapRace();
		b.addTime(new Time("12.00.00", true));
		b.addTime(new Time("12.17.00", false));
		b.addTime(new Time("12.38.00", false));
		b.addTime(new Time("12.59.00", false));
		assertEquals(1,a.compareTo(b));
	}
	@Test
	public void testSortedSameLapsSameTotalTime() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.41.00"));
		MultiLapRace a = new MultiLapRace();
		a.addTime(new Time("12.00.00", true));
		a.addTime(new Time("12.20.00", false));
		a.addTime(new Time("12.40.00", false));
		a.addTime(new Time("13.00.00", false));
		MultiLapRace b = new MultiLapRace();
		b.addTime(new Time("12.00.00", true));
		b.addTime(new Time("12.17.00", false));
		b.addTime(new Time("12.38.00", false));
		b.addTime(new Time("13.00.00", false));
		assertEquals(0,a.compareTo(b));
	}
	
	

}
