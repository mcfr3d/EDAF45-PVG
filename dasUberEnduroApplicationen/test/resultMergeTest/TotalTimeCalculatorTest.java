package resultMergeTest;

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
		
		//System.out.print(difference);
		
		assertEquals(difference,"07.56.47");
	}
	
	
	
	
}
