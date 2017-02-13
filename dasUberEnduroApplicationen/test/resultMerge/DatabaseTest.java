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
		db = new Database("00.00.00", false);
		db.setColumnHeaders(new String[] { "StartNr", "Namn" });
		db.addFinish(1, "01.00.00");
		db.addFinish(2, "01.00.02");
		db.addFinish(3, "00.59.00");
		db.setName(1, "Gunther");
		String s = db.getResult(false);
		assertEquals(s,
				"Ej Anmäld\n" + "StartNr; Namn; Totaltid; Starttid; Måltid\n" + "1; Gunther; 01.00.00; 00.00.00; 01.00.00\n"
						+ "2; ; 01.00.02; 00.00.00; 01.00.02\n" + "3; ; 00.59.00; 00.00.00; 00.59.00\n");
	}

	@Test
	public void testSortedMassStart() {
		db = new Database("00.00.00", false);
		db.setColumnHeaders(new String[] { "StartNr", "Namn" });
		db.addFinish(1, "01.00.00");
		db.addFinish(2, "01.00.02");
		db.addFinish(3, "00.59.00");
		String s = db.getResult(true);
		assertEquals(s,
				"Ej Anmäld\n" + "Plac; StartNr; Namn; Totaltid; Starttid; Måltid\n"
						+ "1; 3; ; 00.59.00; 00.00.00; 00.59.00\n" + "2; 1; ; 01.00.00; 00.00.00; 01.00.00\n"
						+ "3; 2; ; 01.00.02; 00.00.00; 01.00.02\n");
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
	public void testOneRacerOneLapSort() {
		db.addRacer(1, "Test Tsson", "Tester");
		db.addStart(1, "00.00.00");
		db.addFinish(1, "00.00.01");
		String result = "Tester\nPlac; StartNr; Namn; Totaltid; Starttid; Måltid\n1; 1; Test Tsson; 00.00.01; 00.00.00; 00.00.01; Omöjlig Totaltid?\n";
		assertEquals("Sort not working with one racer, one lap: ", result, db.getResult(true));
	}

	@Test
	public void testOneRacerMultipleLapsSort() {
		db = new Database(null, true);
		db.setColumnHeaders(new String[] { "StartNr", "Namn" });
		db.addRacer(1, "Test Tsson", "Tester");
		db.addStart(1, "00.00.00");
		db.addFinish(1, "00.00.01");
		db.addFinish(1, "01.00.00");
		db.addFinish(1, "01.00.01");
		assertTrue(db.isMultiLapRace());
		String result = "Tester\nPlac; StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Varv3; Start; Varvning1; Varvning2; Mål\n1; 1; Test Tsson; 3; 01.00.01; 00.00.01; 00.59.59; 00.00.01; 00.00.00; 00.00.01; 01.00.00; 01.00.01; Omöjlig varvtid?\n";
		assertEquals("Sort not working with one racer, multiple lap: ", result, db.getResult(true));
	}

	@Test
	public void testOneLapRaceSort() {
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
		String header = "Plac; StartNr; Namn; Totaltid; Starttid; Måltid\n";
		String racer1 = "1; 1; Test Tsson; 00.00.01; 00.00.00; 00.00.01; Omöjlig Totaltid?\n";
		String racer2 = "2; 2; Per Psson; 00.00.02; 00.00.00; 00.00.02; Omöjlig Totaltid?\n";
		String racer3 = "3; 3; Oleg Osson; 00.00.05; 00.00.05; 00.00.10; Omöjlig Totaltid?\n";
		String racer4 = "4; 4; Sten Ssson; 00.00.05; 00.00.05; 00.00.10; Omöjlig Totaltid?\n";
		String result = raceClass + header + racer1 + racer2 + racer3 + racer4;
		assertEquals("Sort not working with one racer, one lap: ", result, db.getResult(true));
	}

	@Test
	public void testMultipleLapRaceSort() {
		db = new Database(null, true);
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
		String header = "Plac; StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Varv3; Start; Varvning1; Varvning2; Mål\n";
		String racer1 = "1; 1; Test Tsson; 3; 01.00.01; 00.00.01; 00.59.59; 00.00.01; 00.00.00; 00.00.01; 01.00.00; 01.00.01; Omöjlig varvtid?\n";
		String racer2 = "2; 2; Per Psson; 3; 01.00.02; 00.00.02; 00.59.58; 00.00.02; 00.00.00; 00.00.02; 01.00.00; 01.00.02; Omöjlig varvtid?\n";
		String racer3 = "3; 3; Oleg Osson; 3; 01.00.05; 00.00.05; 00.59.55; 00.00.05; 00.00.05; 00.00.10; 01.00.05; 01.00.10; Omöjlig varvtid?\n";
		String racer4 = "4; 4; Sten Ssson; 3; 01.00.05; 00.00.05; 00.59.55; 00.00.05; 00.00.05; 00.00.10; 01.00.05; 01.00.10; Omöjlig varvtid?\n";
		String result = raceClass + header + racer1 + racer2 + racer3 + racer4;
		assertEquals("Sort not working with one racer, one lap: ", result, db.getResult(true));
	}

	@Test
	public void testMultipleDifferentLapsRaceSort() {
		db = new Database(null, true);
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

		db.addRacer(3, "Oleg Osson", "Tester");
		db.addStart(3, "00.00.05");
		db.addFinish(3, "00.00.10");

		db.addRacer(2, "Per Psson", "Tester");
		db.addStart(2, "00.00.00");
		db.addFinish(2, "00.00.02");
		db.addFinish(2, "01.00.00");
		String raceClass = "Tester\n";
		String header = "Plac; StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Varv3; Start; Varvning1; Varvning2; Mål\n";
		String racer1 = "1; 1; Test Tsson; 3; 01.00.01; 00.00.01; 00.59.59; 00.00.01; 00.00.00; 00.00.01; 01.00.00; 01.00.01; Omöjlig varvtid?\n";
		String racer2 = "2; 2; Per Psson; 2; 01.00.00; 00.00.02; 00.59.58; ; 00.00.00; 00.00.02; 01.00.00; ; Omöjlig varvtid?\n";
		String racer3 = "3; 3; Oleg Osson; 1; 00.00.05; 00.00.05; ; ; 00.00.05; 00.00.10; ; ; Omöjlig varvtid?\n";
		String racer4 = "4; 4; Sten Ssson; 2; 01.00.00; 00.00.05; 00.59.55; ; 00.00.05; 00.00.10; 01.00.05; ; Omöjlig varvtid?\n";
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