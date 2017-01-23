package registrationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import resultMerge.Racer;

public class RacerTest {

	@Test
	public void testNoStartTime() {
		Racer temp = new Racer(1);
		temp.addFinish("1234");
		assertEquals();
	}

}
