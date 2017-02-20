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
		Database db = new Database();
		db.addRacer(1, "A A", "");
		db.addRacer(1, "A A", "");
		db.addRacer(1, "A A", "");
		db.setMassStart("00.00.00");
		for(Racer r: db.getRacers().values()) {
			assertEquals("00.00.00", r.getFirstStartTime());
		}
	}
	
}