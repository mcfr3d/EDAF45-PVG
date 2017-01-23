package util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import resultMerge.Database;

public class ResultWriterTest {

	@Test
	public void testDatabaseOneEntry() throws IOException {
		
		Database db = new Database();
		
		String path = "output.txt";
		db.setName(7,"Emil");
		db.addStart(7,"00.01.00");
		db.addFinish(7,"00.01.01");

		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(ls.size(),2);
		assertEquals(ls.get(0), "StartNr; Namn; Totaltid; Starttid; Måltid");
		assertEquals(ls.get(1), "7; Emil; 00.00.01; 00.01.00; 00.01.01");
	}
	@Test
	public void testDatabaseMultipleEntry() throws IOException {
		
		Database db = new Database();
		
		String path = "output.txt";
		
		db.setName(7,"Emil");
		db.addStart(7,"00.01.00");
		db.addFinish(7,"00.01.01");

		db.setName(9,"Jacob");
		db.addStart(9,"00.20.00");
		db.addFinish(9,"00.20.08");
		
		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(ls.size(),3);
		assertEquals(ls.get(0), "StartNr; Namn; Totaltid; Starttid; Måltid");
		assertEquals(ls.get(1), "7; Emil; 00.00.01; 00.01.00; 00.01.01");
		assertEquals(ls.get(2), "9; Jacob; 00.00.08; 00.20.00; 00.20.08");
	}
	@Test
	public void testDatabaseFaultyEntries() throws IOException {
		
		Database db = new Database();
		
		String path = "output.txt";
		
		db.setName(7,"Emil");
		db.addStart(7,"00.01.00");
		db.addFinish(7,"00.01.01");

		db.setName(9,"Jacob");
		db.addStart(9,"00.20.00");
		db.addStart(9,"00.20.07");
		db.addFinish(9,"00.20.08");
		
		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(ls.size(),3);
		assertEquals(ls.get(0), "StartNr; Namn; Totaltid; Starttid; Måltid");
		assertEquals(ls.get(1), "7; Emil; 00.00.01; 00.01.00; 00.01.01");
		assertEquals(ls.get(2), "9; Jacob; 00.00.08; 00.20.00; 00.20.08; Flera starttider? 00.20.07");
	}

}
