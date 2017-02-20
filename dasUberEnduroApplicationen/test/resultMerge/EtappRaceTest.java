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
	public void testEtappWidthMinimumTime() {
		tmp.addTime(new Time("12.00.00",true,1));
		tmp.addTime(new Time("12.04.00",false,1));
		String res = "1; 00.04.00; 00.04.00; 12.00.00; 12.04.00; etapp 1 omöjlig tid";
		assertEquals(res, tmp.genResultWithErrors(db));
		
	}

}
