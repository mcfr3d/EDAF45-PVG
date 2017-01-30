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
		assertEquals("1; 02.00.00; 02.00.00; ; 00.00.00; 02.00.00; ", temp2.genResult());
	}

}
