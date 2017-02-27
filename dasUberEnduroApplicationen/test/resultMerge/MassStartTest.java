package resultMerge;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import resultMerge.*;

public class MassStartTest {
	
	@Test
	public void testMassStartAfter(){
		Database db = new Database("00.00.00", Database.ONE_LAP_RACE);
		db.addRacer(1, "A A", "");
		db.addRacer(1, "A A", "");
		db.addRacer(1, "A A", "");
		for(Racer r: db.getRacers().values()) {
			assertEquals("00.00.00", r.getFirstStartTime());
		}
	}
	
}