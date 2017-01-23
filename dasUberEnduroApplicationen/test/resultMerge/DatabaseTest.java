package resultMerge;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import resultMerge.*;

public class DatabaseTest {

	@Test
	public void testAddStart(){
		Database db = new Database();
		db.addStart(1,"00.00.00");
		assertTrue(db.size() == 1);
	}
	
	@Test
	public void testAddFinish(){
		Database db = new Database();
		db.addFinish(1,"00.00.00");
		assertTrue(db.size() == 1);
	}
	
	@Test
	public void testSeveralStarts(){
		Database db = new Database();
		db.addStart(1,"00.00.00");
		db.addStart(1,"00.00.00");
		db.addStart(1,"00.00.01");
		assertTrue(db.size() == 1);
	}
	
	@Test
	public void testStartAndFinish(){
		Database db = new Database();
		db.addStart(1,"00.00.00");
		db.addFinish(1,"00.00.01");
		assertTrue(db.size() == 1);
	}
	
	@Test
	public void testMultipleRacers(){
		Database db = new Database();
		db.addStart(1,"00.00.00");
		db.addFinish(2,"00.00.01");
		assertTrue(db.size() == 2);
	}
	
}