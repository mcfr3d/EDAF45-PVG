package resultMerge;

import resultMerge.TotalTimeCalculator;
import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

public class TotalTimeCalculatorTest {

	@Test
	public void test() {

		String finish = "18.27.17";
		String start = "10.30.30";

		String difference = TotalTimeCalculator.computeDifference(start, finish);

		assertEquals(difference, "07.56.47");
	}

	@Test
	public void testFaultySyntax() {

		String finish = "18:27-17";
		String start = "10.30:30";

		String difference = TotalTimeCalculator.computeDifference(start, finish);

		assertEquals(difference, "--.--.--");
	}

	@Test
	public void testNegative() {

		String finish = "10:27-17";
		String start = "17.30:30";

		String difference = TotalTimeCalculator.computeDifference(start, finish);

		assertEquals(difference, "--.--.--");
	}

}
