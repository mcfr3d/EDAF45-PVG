package resultMerge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EtappRaceTest {
	
	private EtappRace tmp;
	
	@Before
	public void init() {
		tmp = new EtappRace(1);
	}
	
	@Test
	public void test1Etapp() throws Exception {
		tmp.addTime(new Time("12.00.00",true,1));
		tmp.addTime(new Time("13.00.00",false,1));
		String res = "01.00.00; 1; 01.00.00; 12.00.00; 13.00.00";
		assertEquals(res, tmp.genResult());
		
	}

}
