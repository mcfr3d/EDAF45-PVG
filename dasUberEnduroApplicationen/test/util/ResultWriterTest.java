package util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import resultMerge.Database;

public class ResultWriterTest {
	String path = "output.txt";
	Database db;
	
	
	@Before
	public void init() {
		db = new Database();
		db.setName(7,"Emil");
		db.addStart(7,"00.00.00");
		db.addFinish(7,"01.00.00");
		db.setRacerClass(7, "DAMER");
	}
	
	@Test
	public void testMultiLapRace() throws IOException {
		db = new Database(null, true);
		db.setName(7,"Emil");
		db.addStart(7,"00.00.00");
		db.addFinish(7,"01.00.00");
		
		db.setName(9,"Jacob");
		db.addStart(9,"02.00.00");
		db.addFinish(9,"04.00.00");
		db.addFinish(9,"05.00.00");
		db.addFinish(9,"06.00.00");

		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(3, ls.size());
		assertEquals("StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Varv3; Start; Varvning1; Varvning2; Mål", ls.get(0));
		assertEquals("7; Emil; 1; 01.00.00; 01.00.00; ; ; 00.00.00; 01.00.00; ;", ls.get(1));
		assertEquals("9; Jacob; 3; 04.00.00; 02.00.00; 01.00.00; 01.00.00; 02.00.00; 04.00.00; 05.00.00; 06.00.00", ls.get(2));
		
	}
	
	@Test
	public void testNoRaceClasses() throws IOException {
		db = new Database();
		db.setName(7,"Emil");
		db.addStart(7,"00.00.00");
		db.addFinish(7,"01.00.00");
		
		db.setName(9,"Jacob");
		db.addStart(9,"02.00.00");
		db.addFinish(9,"04.00.00");
		
		db.setName(10,"Jesper");
		db.addStart(10,"02.00.01");
		db.addFinish(10,"04.00.01");

		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(4, ls.size());
		assertEquals("StartNr; Namn; Totaltid; Starttid; Måltid", ls.get(0));
		assertEquals("7; Emil; 01.00.00; 00.00.00; 01.00.00", ls.get(1));
		assertEquals("9; Jacob; 02.00.00; 02.00.00; 04.00.00", ls.get(2));
		assertEquals("10; Jesper; 02.00.00; 02.00.01; 04.00.01", ls.get(3));
	}

	@Test
	public void testDatabaseOneEntry() throws IOException {
		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(ls.size(), 3);
		assertEquals("DAMER", ls.get(0));
		assertEquals("StartNr; Namn; Totaltid; Starttid; Måltid", ls.get(1));
		assertEquals("7; Emil; 01.00.00; 00.00.00; 01.00.00", ls.get(2));
	}
	@Test
	public void testDatabaseMultipleEntry() throws IOException {
		db.setName(9,"Jacob");
		db.addStart(9,"02.00.00");
		db.addFinish(9,"04.00.00");
		db.setRacerClass(9, "SENIOR");
		
		db.setName(10,"Emil");
		db.addStart(10,"02.00.01");
		db.addFinish(10,"04.00.01");
		db.setRacerClass(10, "DAMER");
		
		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(ls.size(), 7);
		assertEquals("DAMER", ls.get(0));
		assertEquals("StartNr; Namn; Totaltid; Starttid; Måltid", ls.get(1));
		assertEquals("7; Emil; 01.00.00; 00.00.00; 01.00.00", ls.get(2));
		assertEquals("10; Emil; 02.00.00; 02.00.01; 04.00.01", ls.get(3));
		assertEquals("SENIOR", ls.get(4));
		assertEquals("StartNr; Namn; Totaltid; Starttid; Måltid", ls.get(5));
		assertEquals("9; Jacob; 02.00.00; 02.00.00; 04.00.00", ls.get(6));
	}
	@Test
	public void testDatabaseFaultyEntries() throws IOException {
		db.setName(9,"Jacob");
		db.addStart(9,"02.00.00");
		db.addFinish(9,"04.00.00");
		db.addFinish(9,"05.00.00");
		db.setRacerClass(9, "SENIOR");
		
		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(ls.size(), 6);
		assertEquals("DAMER", ls.get(0));
		assertEquals("StartNr; Namn; Totaltid; Starttid; Måltid", ls.get(1));
		assertEquals("7; Emil; 01.00.00; 00.00.00; 01.00.00", ls.get(2));
		assertEquals("SENIOR", ls.get(3));
		assertEquals("StartNr; Namn; Totaltid; Starttid; Måltid", ls.get(4));
		assertEquals("9; Jacob; 02.00.00; 02.00.00; 04.00.00; Flera måltider? 05.00.00", ls.get(5));
	}

}
