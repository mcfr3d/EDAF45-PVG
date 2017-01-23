package registrationTest;

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
		temp.addFinish("1234");
		assertEquals("1; person; --.--.--; Start?; 1234",temp.toString());
	}

	@Test
	public void testNoFinishTime() {
		temp.addStart("12345");
		assertEquals("1; person; --.--.--; 12345; Slut?",temp.toString());
	}

	@Test
	public void testMultipleFinishTimes() {
		temp.addFinish("12345");
		temp.addFinish("54321");
		assertEquals("1; person; --.--.--; Start?; Flera måltider? 12345; 54321", temp.toString());
	}
	
	@Test
	public void testMultipleStartTimes() {
		temp.addStart("12:00");
		temp.addStart("12:05");
		assertEquals("1; person; --.--.--; Flera starttider? 12:00; 12:05; Slut?", temp.toString());
	}
}
