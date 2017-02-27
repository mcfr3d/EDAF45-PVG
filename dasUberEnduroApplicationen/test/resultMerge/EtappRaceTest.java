package resultMerge;

import static org.junit.Assert.*;

import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Test;

public class EtappRaceTest {
	
	private EtappRace tmp;
	private Database db;
	private static String etappConfig = "{\"etapper\":[{\"minimum time\":\"00.05.00\"}]}";
	
	@Before
	public void init() {
		tmp = new EtappRace(1);
		JSONObject root = new JSONObject(new JSONTokener(etappConfig));
		db = new Database(null,Database.ETAPP_RACE);
		db.setEtappInfo(new EtappInfo(root.getJSONArray("etapper")));
	}
	
	@Test
	public void testMissingStartAndFinish(){
		String res = "0; --.--.--; ; Start?; Slut?";
		assertEquals(res, tmp.genResultWithErrors(null));
	}
	
	@Test
	public void test1Etapp() {
		tmp.addTime(new Time("12.00.00",true,1));
		tmp.addTime(new Time("13.00.00",false,1));
		String res = "1; 01.00.00; 01.00.00; 12.00.00; 13.00.00";
		assertEquals(res, tmp.genResultWithErrors(null));
	}
	
	@Test
	public void testMissingStart() {
		tmp.addTime(new Time("13.00.00",false,1));
		String res = "0; --.--.--; ; Start?; 13.00.00";
		assertEquals(res, tmp.genResultWithErrors(null));
	}
	@Test
	public void testMissingFinish() {
		tmp.addTime(new Time("12.00.00", true, 1));
		String res = "0; --.--.--; ; 12.00.00; Slut?";
		assertEquals(res, tmp.genResultWithErrors(null));
	}
	@Test
	public void test2Etapp() {
		tmp = new EtappRace(2);
		tmp.addTime(new Time("12.00.00",true,1));
		tmp.addTime(new Time("13.00.00",false,1));
		tmp.addTime(new Time("13.15.00",true,2));
		tmp.addTime(new Time("13.20.00",false,2));
		String res = "2; 01.05.00; 01.00.00; 00.05.00; 12.00.00; 13.00.00; 13.15.00; 13.20.00";
		assertEquals(res, tmp.genResultWithErrors(null));
	}
	@Test
	public void testEtappWithMinimumTime() {
		tmp.addTime(new Time("12.00.00",true,1));
		tmp.addTime(new Time("12.04.00",false,1));
		String res = "1; 00.04.00; 00.04.00; 12.00.00; 12.04.00; Omöjlig tid Etapp1";
		assertEquals(res, tmp.genResultWithErrors(db));
		
	}
	@Test
	public void testEtappToManyStartTimes() {
		tmp.addTime(new Time("12.00.00",true,1));
		tmp.addTime(new Time("12.01.00",true,1));
		tmp.addTime(new Time("12.02.00",true,1));
		tmp.addTime(new Time("12.10.00",false,1));
		String res = "1; 00.10.00; 00.10.00; 12.00.00; 12.10.00; Flera starttider Etapp1 12.01.00 12.02.00";
		assertEquals(res, tmp.genResultWithErrors(db));
	}
	@Test
	public void testEtappToManyFinishTimes() {
		tmp.addTime(new Time("12.00.00",true,1));
		tmp.addTime(new Time("12.08.00",false,1));
		tmp.addTime(new Time("12.09.00",false,1));
		tmp.addTime(new Time("12.10.00",false,1));
		String res = "1; 00.08.00; 00.08.00; 12.00.00; 12.08.00; Flera måltider Etapp1 12.09.00 12.10.00";
		assertEquals(res, tmp.genResultWithErrors(db));
	}
	
	@Test
	public void testEtappAllErrors() {
		//Etapp1 saknar start och slut

		db = new Database(null,Database.ETAPP_RACE);
		tmp = new EtappRace(6);
		
		tmp.addTime(new Time("12.00.00",true,2));
		
		tmp.addTime(new Time("12.04.00",false,3));
		
		tmp.addTime(new Time("12.11.00",true,4));
		tmp.addTime(new Time("12.12.00",true,4));
		tmp.addTime(new Time("12.20.00",false,4));
		
		tmp.addTime(new Time("12.21.00",true,5));
		tmp.addTime(new Time("12.30.00",false,5));
		tmp.addTime(new Time("12.31.00",false,5));
		
		tmp.addTime(new Time("12.40.00",true,6));
		tmp.addTime(new Time("12.41.00",true,6));
		tmp.addTime(new Time("12.50.00",false,6));
		tmp.addTime(new Time("12.51.00",false,6));
		
		String res = "3; 00.28.00; ; ; ; 00.09.00; 00.09.00; 00.10.00; Start?; Slut?; 12.00.00; Slut?; Start?; 12.04.00; 12.11.00; 12.20.00; 12.21.00; 12.30.00; 12.40.00; 12.50.00; Flera starttider Etapp4 12.12.00; Flera måltider Etapp5 12.31.00; Flera starttider Etapp6 12.41.00; Flera måltider Etapp6 12.51.00";
		assertEquals(res, tmp.genResultWithErrors(null));
	}

}
