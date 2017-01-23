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

}
