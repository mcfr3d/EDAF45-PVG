package registration;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.*;

import resultMerge.Racer;

public class RacerTest {
	Racer temp;
	
	@Before
	public void init() {
		temp = new Racer(1);
		temp.setName("person");
	}

	@Test
	public void testNoStartTime() {
		temp.addFinish("12.00.00");
		assertEquals("1; person; --.--.--; Start?; 12.00.00; Omöjlig Totaltid?",temp.toString());
	}

	@Test
	public void testNoFinishTime() {
		temp.addStart("12.00.00");
		assertEquals("1; person; --.--.--; 12.00.00; Slut?; Omöjlig Totaltid?",temp.toString());
	}

	@Test
	public void testMultipleFinishTimes() {
		temp.addFinish("12.00.00");
		temp.addFinish("12.15.00");
		assertEquals("1; person; --.--.--; Start?; 12.00.00; Flera måltider? 12.15.00; Omöjlig Totaltid?", temp.toString());
	}
	
	@Test
	public void testMultipleStartTimes() {
		temp.addStart("12.00.00");
		temp.addStart("12.15.00");
		assertEquals("1; person; --.--.--; 12.00.00; Slut?; Flera starttider? 12.15.00; Omöjlig Totaltid?", temp.toString());
	}

	@Test
	public void testImpossibleTotalTime() {
		temp.addStart("12.00.00");
		temp.addFinish("12.10.00");
		System.out.println(temp.toString());
		assertEquals("1; person; 00.10.00; 12.00.00; 12.10.00; Omöjlig Totaltid?", temp.toString());
	}
	
	@Test
	public void testPossibleTotalTime() {
		temp.addStart("12.00.00");
		temp.addFinish("12.15.00");
		System.out.println(temp.toString());
		assertEquals("1; person; 00.15.00; 12.00.00; 12.15.00", temp.toString());
	}
	//TODO
//	@Test
//	public void testRacerClass(){
//		temp.setRacerClass("Juniorer");
//	}
	
}
