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
	public void testMissingStartAndFinish(){
		String res = "0; --.--.--; ; Start?; Slut?";
		assertEquals(res, tmp.genResultWithErrors());
	}
	
	@Test
	public void test1Etapp() {
		tmp.addTime(new Time("12.00.00",true,1));
		tmp.addTime(new Time("13.00.00",false,1));
		String res = "1; 01.00.00; 01.00.00; 12.00.00; 13.00.00";
		assertEquals(res, tmp.genResultWithErrors());
	}
	
	@Test
	public void testMissingStart() {
		tmp.addTime(new Time("13.00.00",false,1));
		String res = "0; --.--.--; ; Start?; 13.00.00";
		assertEquals(res, tmp.genResultWithErrors());
	}
	@Test
	public void testMissingFinish() {
		tmp.addTime(new Time("12.00.00", true, 1));
		String res = "0; --.--.--; ; 12.00.00; Slut?";
		assertEquals(res, tmp.genResultWithErrors());
	}
	@Test
	public void test2Etapp() {
		tmp = new EtappRace(2);
		tmp.addTime(new Time("12.00.00",true,1));
		tmp.addTime(new Time("13.00.00",false,1));
		tmp.addTime(new Time("13.15.00",true,2));
		tmp.addTime(new Time("13.20.00",false,2));
		String res = "2; 01.05.00; 01.00.00; 00.05.00; 12.00.00; 13.00.00; 13.15.00; 13.20.00";
		assertEquals(res, tmp.genResultWithErrors());
	}

}
