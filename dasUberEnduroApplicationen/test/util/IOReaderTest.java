package util;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import resultMerge.Database;
import resultMerge.Racer;

public class IOReaderTest {
	String path = "dasUberEnduroApplicationen/mapp_med_shit/";

	@Before
	public void init() {
		try {
			PrintWriter writer1 = new PrintWriter(path + "starttider.txt", "UTF-8");
			PrintWriter writer2 = new PrintWriter(path + "maltider.txt", "UTF-8");
			PrintWriter writer3 = new PrintWriter(path + "namnfil.txt", "UTF-8");
			writer1.print("1; 12.00.00" + "\n" + "2; 12.00.00" + "\n" + "3; 12.00.00" + "\n" + "4; 12.00.00" + "\n"
					+ "5; 12.00.00");
			writer2.print("2; 12.21.00" + "\n" + "4; 12.23.00" + "\n" + "5; 12.24.00" + "\n" + "3; 12.43.00" + "\n"
					+ "1; 13.00.00");
			writer3.println("StartNr; Namn" + "\n" + "SENIOR" + "\n" + "1; Anders Asson" + "\n" + "2; Bengt Bsson"
					+ "\n" + "JUNIOR" + "\n" + "3; Chris Csson" + "\n" + "4; David Dsson" + "\n" + "5; Erik Esson");
			writer1.close();
			writer2.close();
			writer3.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReaderWith5Entries() throws IOException {
		String[] start = { "12.00.00", "12.00.00", "12.00.00", "12.00.00", "12.00.00" };
		String[] finish = { "13.00.00", "12.21.00", "12.43.00", "12.23.00", "12.24.00" };
		String[] names = { "Anders Asson", "Bengt Bsson", "Chris Csson", "David Dsson", "Erik Esson" };
		String[] classes = { "SENIOR", "SENIOR", "JUNIOR", "JUNIOR", "JUNIOR" };
		Database db = new Database();
		IOReader.readStart(path + "starttider.txt", db);
		IOReader.readFinish(path + "maltider.txt", db);
		IOReader.readNames(path + "namnfil.txt", db);
		HashMap<Integer, Racer> map = db.getRacers();
		assertEquals(map.size(), 5);
		for (int i : map.keySet()) {
			Racer r = map.get(i);
			String s[] = r.toString().split("; ");
			assertEquals(s[0], Integer.toString(i));
			assertEquals(s[1], names[i-1]);
			assertEquals(s[3], start[i-1]);
			assertEquals(s[4], finish[i-1]);
			assertEquals(r.getRaceClass(), classes[i-1]);
		}
	}
	
	@Test
	public void testIfFileIsMissing() {
		Database db = new Database();
		try {
			IOReader.readStart(path + "maltider.txt", db);
			IOReader.readNames(path + "namnfil.txt", db);
			IOReader.readFinish(path + "doesntexist.txt", db);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
