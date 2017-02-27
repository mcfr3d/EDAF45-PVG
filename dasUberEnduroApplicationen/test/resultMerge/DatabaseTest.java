package resultMerge;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import resultMerge.*;

public class DatabaseTest {

	private Database db;

	@Before
	public void SetUp() {
		db = new Database();
		db.setColumnHeaders(new String[] { "StartNr", "Namn" });
	}

	@After
	public void TearDown() {
		db = null;
	}

	@Test
	public void testDoubleNumber() {
		assertTrue(db.addRacer(1, "Gunther", "Tysk"));
		assertFalse(db.addRacer(1, "Adolf", "Tysk"));
	}

	@Test
	public void testUnsortedMassStart() {
		db = new Database("00.00.00", Database.ONE_LAP_RACE);
		db.setColumnHeaders(new String[] { "StartNr", "Namn" });
		db.addRacer(1, "Gunther", "Senior");
		db.addFinish(1, "01.00.00");
		db.addFinish(2, "01.00.02");
		db.addFinish(3, "00.59.00");
		
		String s = db.getResult(false);
		String header = "StartNr; Namn; TotalTid; Starttid; Måltid\n";
		assertEquals(
				"Senior\n" + header + "1; Gunther; 01.00.00; 00.00.00; 01.00.00\n" +
				"Icke existerande startnummer\n" + header + 
				"2; ; --.--.--; Start?; 01.00.02\n" + "3; ; --.--.--; Start?; 00.59.00\n"
				
				, s);
	}

	@Test
	public void testSortedMassStart() {
		db = new Database("00.00.00", Database.ONE_LAP_RACE);
		db.setColumnHeaders(new String[] { "StartNr", "Namn" });
		db.addRacer(1, "", "Senior");
		db.addRacer(2, "", "Senior");
		db.addRacer(3, "", "Senior");
		db.addFinish(1, "01.00.00");
		db.addFinish(2, "01.00.02");
		db.addFinish(3, "00.59.00");
		String s = db.getResult(true);
		assertEquals("Senior\n" + "Plac; StartNr; Namn; TotalTid; Starttid; Måltid\n"

				+ "1; 3; ; 00.59.00; 00.00.00; 00.59.00\n" + "2; 1; ; 01.00.00; 00.00.00; 01.00.00\n"
				+ "3; 2; ; 01.00.02; 00.00.00; 01.00.02\n", s);
	}

	@Test
	public void testAddStart() {
		db.addStart(1, "00.00.00");
		assertTrue(db.size() == 1);
	}

	@Test
	public void testAddFinish() {
		db.addFinish(1, "00.00.00");
		assertTrue(db.size() == 1);
	}

	@Test
	public void testSeveralStarts() {
		db.addStart(1, "00.00.00");
		db.addStart(1, "00.00.00");
		db.addStart(1, "00.00.01");
		assertTrue(db.size() == 1);
	}

	@Test
	public void testStartAndFinish() {
		db.addStart(1, "00.00.00");
		db.addFinish(1, "00.00.01");
		assertTrue(db.size() == 1);
	}

	@Test
	public void testRacerClass() {
		db.setRacerClass(1, "SENIOR");
		db.setRacerClass(2, "JUNIOR");
		assertTrue(db.size() == 2);
	}

	@Test
	public void testMultipleRacers() {
		db.addStart(1, "00.00.00");
		db.addFinish(2, "00.00.01");
		assertTrue(db.size() == 2);
	}

	@Test
	public void testEmptySort() {
		assertEquals("Sort not working with empty racerlist: ", "", db.getResult(false));
	}

	@Test
	public void testOneRacerOneLapUnsorted() {
		db.addRacer(1, "Test Tsson", "Tester");
		db.addStart(1, "00.00.00");
		db.addFinish(1, "00.00.01");
		String result = "Tester\nStartNr; Namn; TotalTid; Starttid; Måltid\n1; Test Tsson; 00.00.01; 00.00.00; 00.00.01; Omöjlig Totaltid?\n";
		assertEquals("Sort not working with one racer, one lap: ", result, db.getResult(false));
	}

	@Test
	public void testOneRacerMultipleLapsSort() {
		db = new Database(null, Database.MULTI_LAP_RACE);
		db.setColumnHeaders(new String[] { "StartNr", "Namn" });
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("01.00.00"));
		db.addRacer(1, "Test Tsson", "Tester");
		db.addStart(1, "00.00.00");
		db.addFinish(1, "00.00.01");
		db.addFinish(1, "01.00.00");
		db.addFinish(1, "01.00.01");
		assertTrue(db.isMultiLapRace());
		String result = "Tester\nPlac; StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Varv3\n"
				+ "1; 1; Test Tsson; 3; 01.00.01; 00.00.01; 00.59.59; 00.00.01\n";
		assertEquals("Sort not working with one racer, multiple lap: ", result, db.getResult(true));
	}

	@Test
	public void testOneLapRaceUnsorted() {
		db.addRacer(1, "Test Tsson", "Tester");
		db.addStart(1, "00.00.00");
		db.addFinish(1, "00.00.01");

		db.addRacer(4, "Sten Ssson", "Tester");
		db.addStart(4, "00.00.05");
		db.addFinish(4, "00.00.10");

		db.addRacer(3, "Oleg Osson", "Tester");
		db.addStart(3, "00.00.05");
		db.addFinish(3, "00.00.10");

		db.addRacer(2, "Per Psson", "Tester");
		db.addStart(2, "00.00.00");
		db.addFinish(2, "00.00.02");

		String raceClass = "Tester\n";
		String header = "StartNr; Namn; TotalTid; Starttid; Måltid\n";
		String racer1 = "1; Test Tsson; 00.00.01; 00.00.00; 00.00.01; Omöjlig Totaltid?\n";
		String racer2 = "2; Per Psson; 00.00.02; 00.00.00; 00.00.02; Omöjlig Totaltid?\n";
		String racer3 = "3; Oleg Osson; 00.00.05; 00.00.05; 00.00.10; Omöjlig Totaltid?\n";
		String racer4 = "4; Sten Ssson; 00.00.05; 00.00.05; 00.00.10; Omöjlig Totaltid?\n";
		String result = raceClass + header + racer1 + racer2 + racer3 + racer4;
		assertEquals("Sort not working with one racer, one lap: ", result, db.getResult(false));
	}

	@Test
	public void testMultipleLapRaceSort() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("01.00.00"));
		db = new Database(null, Database.MULTI_LAP_RACE);
		db.setColumnHeaders(new String[] { "StartNr", "Namn" });
		db.addRacer(1, "Test Tsson", "Tester");
		db.addStart(1, "00.00.00");
		db.addFinish(1, "00.00.01");
		db.addFinish(1, "01.00.00");
		db.addFinish(1, "01.00.01");

		db.addRacer(4, "Sten Ssson", "Tester");
		db.addStart(4, "00.00.05");
		db.addFinish(4, "00.00.10");
		db.addFinish(4, "01.00.05");
		db.addFinish(4, "01.00.10");

		db.addRacer(3, "Oleg Osson", "Tester");
		db.addStart(3, "00.00.05");
		db.addFinish(3, "00.00.10");
		db.addFinish(3, "01.00.05");
		db.addFinish(3, "01.00.10");

		db.addRacer(2, "Per Psson", "Tester");
		db.addStart(2, "00.00.00");
		db.addFinish(2, "00.00.02");
		db.addFinish(2, "01.00.00");
		db.addFinish(2, "01.00.02");

		String raceClass = "Tester\n";
		String header = "Plac; StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Varv3\n";
		String racer1 = "1; 1; Test Tsson; 3; 01.00.01; 00.00.01; 00.59.59; 00.00.01\n";
		String racer2 = "2; 2; Per Psson; 3; 01.00.02; 00.00.02; 00.59.58; 00.00.02\n";
		String racer3 = "3; 3; Oleg Osson; 3; 01.00.05; 00.00.05; 00.59.55; 00.00.05\n";
		String racer4 = "4; 4; Sten Ssson; 3; 01.00.05; 00.00.05; 00.59.55; 00.00.05\n";
		String result = raceClass + header + racer1 + racer2 + racer3 + racer4;
		assertEquals("Sort not working with one racer, one lap: ", result, db.getResult(true));
	}

	@Test
	public void testMultipleDifferentLapsRaceSort() {
		MultiLapRace.setMaxLaps(3);
		MultiLapRace.setStipulatedTime(new Time("01.00.00"));
		db = new Database(null, Database.MULTI_LAP_RACE);
		db.setColumnHeaders(new String[] { "StartNr", "Namn" });
		db.addRacer(1, "Test Tsson", "Tester");
		db.addStart(1, "00.00.00");
		db.addFinish(1, "00.00.01");
		db.addFinish(1, "01.00.00");
		db.addFinish(1, "01.00.01");

		db.addRacer(4, "Sten Ssson", "Tester");
		db.addStart(4, "00.00.05");
		db.addFinish(4, "00.00.10");
		db.addFinish(4, "01.00.06");

		db.addRacer(3, "Oleg Osson", "Tester");
		db.addStart(3, "00.00.05");
		db.addFinish(3, "00.00.10");

		db.addRacer(2, "Per Psson", "Tester");
		db.addStart(2, "00.00.00");
		db.addFinish(2, "00.00.02");
		db.addFinish(2, "01.00.01");
		String raceClass = "Tester\n";
		String header = "Plac; StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Varv3\n";
		String racer1 = "1; 1; Test Tsson; 3; 01.00.01; 00.00.01; 00.59.59; 00.00.01\n";
		String racer2 = "2; 2; Per Psson; 2; 01.00.01; 00.00.02; 00.59.59; \n";
		String racer3 = "3; 4; Sten Ssson; 2; 01.00.01; 00.00.05; 00.59.56; \n";
		String racer4 = "; 3; Oleg Osson; 1; --.--.--; 00.00.05; ; \n";

		String result = raceClass + header + racer1 + racer2 + racer3 + racer4;
		assertEquals("Sort not working with one racer, one lap: ", result, db.getResult(true));
	}

	public void testMissingDriver() {
		Database db = new Database();
		db.addStart(1, "00.00.00");
		db.addFinish(1, "00.00.01");
		Racer r = db.getRacers().get(1);
		assertEquals(r.getRacerClass(), "Ej Anmäld");
	}

}
