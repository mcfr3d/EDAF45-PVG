package util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import resultMerge.Database;

public class ResultWriterTest {
	String path = "output.txt";
	Database db;
	
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void testMultiLapRace() throws IOException {
		db = new Database(null, true);
		db.addRacer(7,"Emil", "");
		db.addStart(7,"00.00.00");
		db.addFinish(7,"01.00.00");
		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(2, ls.size());
		assertEquals("StartNr; Namn; #Varv; TotalTid; Varv1; Start; Mål", ls.get(0));
		assertEquals("7; Emil; 1; 01.00.00; 01.00.00; 00.00.00; 01.00.00", ls.get(1));
		
	}
	
	@Test
	public void testRaceClasses() throws IOException {
		db = new Database();
		db.addRacer(7,"Emil", "Damer");
		db.addStart(7,"00.00.00");
		db.addFinish(7,"01.00.00");
		
		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(3, ls.size());
		assertEquals("Damer", ls.get(0));
		assertEquals("StartNr; Namn; Totaltid; Starttid; Måltid", ls.get(1));
		assertEquals("7; Emil; 01.00.00; 00.00.00; 01.00.00", ls.get(2));
		
	}

	@Test
	public void testDatabaseMultipleEntry() throws IOException {
		db = new Database();
		db.addRacer(9,"Jacob", "SENIOR");
		db.addStart(9,"02.00.00");
		db.addFinish(9,"04.00.00");
		
		db.addRacer(10,"Emil", "DAMER");
		db.addStart(10,"02.00.01");
		db.addFinish(10,"03.00.01");
		
		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(ls.size(), 6);
		TreeSet<String> set = new TreeSet<>();
		set.add(ls.get(0));
		set.add(ls.get(3));
		assertEquals("DAMER", set.first());
		assertEquals("SENIOR", set.last());

		assertEquals("StartNr; Namn; Totaltid; Starttid; Måltid", ls.get(1));
		assertEquals("StartNr; Namn; Totaltid; Starttid; Måltid", ls.get(4));
		
		set = new TreeSet<>();
		set.add(ls.get(2));
		set.add(ls.get(5));
		assertEquals("9; Jacob; 02.00.00; 02.00.00; 04.00.00", set.last());		
		assertEquals("10; Emil; 01.00.00; 02.00.01; 03.00.01", set.first());
	}
}
