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
		temp.addStart("00.00.00");
		temp.addFinish("01.00.00");
		assertEquals("1; 01.00.00; 01.00.00; 00.00.00; 01.00.00", temp.genResult());
	}
	@Test
	public void testTwoLap() {
		temp.addStart("00.00.00");
		temp.addFinish("01.00.00");
		temp.addFinish("02.00.00");
		assertEquals("2; 02.00.00; 01.00.00; 01.00.00; 00.00.00; 01.00.00; 02.00.00", temp.genResult());
	}
	@Test
	public void testTwoDrivers() {
		temp2 = new MultiLapRace(); 
		temp.addStart("00.00.00");
		temp.addFinish("01.00.00");
		temp.addFinish("02.00.00");
		
		temp2.addStart("00.00.00");
		temp2.addFinish("02.00.00");
		assertEquals("2; 02.00.00; 01.00.00; 01.00.00; 00.00.00; 01.00.00; 02.00.00", temp.genResult());
		assertEquals("1; 02.00.00; 02.00.00; ; 00.00.00; 02.00.00;", temp2.genResult());
	}
	
	@Test
	public void testNoStart() {
		temp.addFinish("12.30.00");
		temp.addFinish("13.00.00");
		temp.addFinish("13.23.34");
		assertEquals("2; --.--.--; ; 00.30.00; 00.23.34; Start?; 12.30.00; 13.00.00; 13.23.34", temp.genResult());
	}
	
	@Test
	public void testNoFinish() {
		temp2 = new MultiLapRace(); 
		temp2.addStart("00.00.00");
		temp2.addFinish("02.00.00");
		temp2.addFinish("03.00.00");
		temp2.addFinish("04.00.00");
		temp.addStart("12.01.00");
		assertEquals("0; --.--.--; ; ; ; 12.01.00; Slut?; ;", temp.genResult());
	}
	
	@Test
	public void testMultipleStartTimes(){
		temp.addStart("12.02.00");
		temp.addStart("12.05.00");
		temp.addFinish("12.22.00");
		temp.addFinish("12.42.00");
		temp.addFinish("13.05.06");
		assertEquals("3; 01.03.06; 00.20.00; 00.20.00; 00.23.06; 12.02.00; 12.22.00; 12.42.00; 13.05.06; Flera starttider? 12.05.00", temp.genResult());
	}
	
	@Test
	public void testImpossibleLapTime() {
		temp.addStart("12.03.00");
		temp.addFinish("12.23.00");
		temp.addFinish("12.43.00");
		temp.addFinish("12.52.07");
		assertEquals("3; 00.49.07; 00.20.00; 00.20.00; 00.09.07; 12.03.00; 12.23.00; 12.43.00; 12.52.07; Omöjlig varvtid?", temp.genResult());
	}

}
