package util;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import resultMerge.Database;
import resultMerge.Racer;

public class IOReaderTest {
	String path = "test/util/";

	@Test
	public void testReaderWith5Entries() throws IOException{
		String[] start = {"12.00.00", "12.01.00", "12.02.00", "12.03.00", "12.04.00"};
		String[] finish = {"13.23.34", "13.15.16", "13.05.06", "13.12.07", "13.16.07"};
		String[] names = {"Anders Asson", "Bengt Bsson", "Chris Csson", "David Dsson", "Erik Esson"};
		Database db = new Database();
		IOReader.readStart(path + "starttider.txt", db);
		IOReader.readFinish(path + "maltider.txt", db);
		IOReader.readNames(path + "namnfil.txt", db);
		HashMap<Integer, Racer> map= db.getRacers();
		assertEquals(map.size(), 5);
		for (int i : map.keySet()) {
			Racer r = map.get(i);
			String[] s = r.toString().split("; ");
			assertEquals(s[0], Integer.toString(i));
			assertEquals(s[1], names[i-1]);
			assertEquals(s[3], start[i-1]);
			assertEquals(s[4], finish[i-1]);
		}
	}
	
	@Test
	public void testIfFileIsMissing() {
		Database db = new Database();
		try {
			IOReader.readStart(path + "maltider.txt", db);
			IOReader.readNames(path + "namnfil.txt", db);
			IOReader.readFinish(path + "doesntexist.txt", db);
			fail();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {}
	}
}
