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
		temp.addStart("00.00.00");
		temp.addFinish("01.00.00");
		assertEquals("1; 01.00.00; 01.00.00; 00.00.00; 01.00.00", temp.genResultWithErrors(null));
	}
	@Test
	public void testTwoLap() {
		MultiLapRace.setMaxLaps(2);
		MultiLapRace.setStipulatedTime(new Time("01.11.00"));
		temp.addStart("00.00.00");
		temp.addFinish("01.00.01");
		temp.addFinish("02.00.03");
		assertEquals("2; 02.00.03; 01.00.01; 01.00.02; 00.00.00; 01.00.01; 02.00.03", temp.genResultWithErrors(null));
	}
	@Test
	public void testTwoDrivers() {
		MultiLapRace.setMaxLaps(2);
		MultiLapRace.setStipulatedTime(new Time("01.11.00"));
		temp2 = new MultiLapRace(); 
		temp.addStart("00.00.00");
		temp.addFinish("01.00.00");
		temp.addFinish("02.00.00");
		
		temp2.addStart("00.00.00");
		temp2.addFinish("02.00.00");
		assertEquals("1; 02.00.00; 02.00.00; ; 00.00.00; ; 02.00.00", temp2.genResultWithErrors(null));
		assertEquals("2; 02.00.00; 01.00.00; 01.00.00; 00.00.00; 01.00.00; 02.00.00", temp.genResultWithErrors(null));
	}
	
	/*
	@Test
	public void testNoStart() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.41.00"));
		temp.addFinish("12.30.00");
		temp.addFinish("13.00.00");
		temp.addFinish("13.23.34");
		assertEquals("0; --.--.--; ; ; ; Start?; 12.30.00; 13.00.00; Slut?", temp.genResultWithErrors());
	}*/
	
	@Test
	public void testNoFinish() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("03.39.00"));
		temp.addStart("12.01.00");
		assertEquals("0; --.--.--; ; ; ; 12.01.00; ; ; Slut?", temp.genResultWithErrors(null));
	}
	
	@Test
	public void testMultipleStartTimes(){
		MultiLapRace.setStipulatedTime(new Time("00.42.00"));
		MultiLapRace.setMaxLaps(3);
		temp.addStart("12.02.00");
		temp.addStart("12.05.00");
		temp.addFinish("12.22.00");
		temp.addFinish("12.42.00");
		temp.addFinish("13.05.06");
		assertEquals("3; 01.03.06; 00.20.00; 00.20.00; 00.23.06; 12.02.00; 12.22.00; 12.42.00; 13.05.06; Flera starttider? 12.05.00 ", temp.genResultWithErrors(null));
	}
	
	@Test
	public void testImpossibleLapTime() {
		MultiLapRace.setStipulatedTime(new Time("00.42.00"));
		MultiLapRace.setMaxLaps(3);
		temp.addStart("12.03.00");
		temp.addFinish("12.23.00");
		temp.addFinish("12.43.00");
		temp.addFinish("12.52.07");
		System.out.println(temp.genResultWithErrors(null));
		assertEquals("3; 00.49.07; 00.20.00; 00.20.00; 00.09.07; 12.03.00; 12.23.00; 12.43.00; 12.52.07; Omöjlig varvtid?", temp.genResultWithErrors(null));
	}

	@Test
	public void testSortedDifferentLaps() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.39.00"));
		MultiLapRace a = new MultiLapRace();
		a.addStart("12.00.00");
		a.addFinish("12.20.00");
		a.addFinish("12.40.00");
		MultiLapRace b = new MultiLapRace();
		b.addStart("12.00.00");
		b.addFinish("12.17.00");
		b.addFinish("12.38.00");
		b.addFinish("12.59.00");
		assertEquals(1,a.compareTo(b));
	}

	@Test
	public void testSortedDifferentLapsOneInvalid() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.39.00"));
		MultiLapRace a = new MultiLapRace();
		a.addStart("12.00.00");
		a.addFinish("12.20.00");
		a.addFinish("12.40.00");
		MultiLapRace b = new MultiLapRace();
		b.addFinish("12.17.00");
		b.addFinish("12.38.00");
		b.addFinish("12.59.00");
		assertEquals(-1,a.compareTo(b));
	}

	@Test
	public void testSortedDifferentLapsInvalidLap() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.39.00"));
		MultiLapRace a = new MultiLapRace();
		a.addStart("12.00.00");
		a.addFinish("12.10.00");// Less than minimum lap time -> invalid
		a.addFinish("12.40.00");
		MultiLapRace b = new MultiLapRace();
		b.addStart("12.00.00");
		b.addFinish("12.17.00");
		b.addFinish("12.38.00");
		b.addFinish("12.59.00");
		assertTrue(0 < a.compareTo(b));
	}
	
	@Test
	public void testSortedAllInvalid() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.11.00"));
		MultiLapRace a = new MultiLapRace();
		a.addFinish("12.10.00");
		a.addFinish("12.40.00");
		MultiLapRace b = new MultiLapRace();
		b.addStart("12.00.00");
		b.addFinish("12.1.00");
		b.addFinish("12.2.00");
		b.addFinish("12.3.00");
		assertEquals(0,a.compareTo(b));
	}
	
	@Test
	public void testSortedSameLaps() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.41.00"));
		MultiLapRace a = new MultiLapRace();
		a.addStart("12.00.00");
		a.addFinish("12.20.00");
		a.addFinish("12.40.00");
		a.addFinish("13.00.00");
		MultiLapRace b = new MultiLapRace();
		b.addStart("12.00.00");
		b.addFinish("12.17.00");
		b.addFinish("12.38.00");
		b.addFinish("12.59.00");
		assertEquals(1,a.compareTo(b));
	}
	@Test
	public void testSortedSameLapsSameTotalTime() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("00.41.00"));
		MultiLapRace a = new MultiLapRace();
		a.addStart("12.00.00");
		a.addFinish("12.20.00");
		a.addFinish("12.40.00");
		a.addFinish("13.00.00");
		MultiLapRace b = new MultiLapRace();
		b.addStart("12.00.00");
		b.addFinish("12.17.00");
		b.addFinish("12.38.00");
		b.addFinish("13.00.00");
		assertEquals(0,a.compareTo(b));
	}
	
	

}
