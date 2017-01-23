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
	public void testMassStartBefore(){
		Database db = new Database();
		db.setMassStart("00.00.00");
		db.addFinish(1,"01.00.00");
		db.addFinish(2,"01.00.00");
		db.addFinish(3,"01.00.00");
		for(Racer r: db.getRacers().values()) {
			assertEquals("00.00.00", r.getFirstStartTime());
		}
	}
	
	@Test
	public void testMassStartAfter(){
		Database db = new Database();
		db.addFinish(1,"01.00.00");
		db.addFinish(2,"01.00.00");
		db.addFinish(3,"01.00.00");
		db.setMassStart("00.00.00");
		for(Racer r: db.getRacers().values()) {
			assertEquals("00.00.00", r.getFirstStartTime());
		}
	}
	
}