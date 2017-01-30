package util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import resultMerge.Database;
import resultMerge.Racer;

public class IOReaderTest {
	@Before
	public void init() {
		try {
			File f1 = new File("starttider.txt");
			f1.createNewFile();
			File f2 = new File("maltider.txt");
			f2.createNewFile();
			File f3 = new File("namnfil.txt");
			f3.createNewFile();
			File f4 = new File("starttider2.txt");
			f4.createNewFile();
			PrintWriter writer1 = new PrintWriter(f1, "UTF-8");
			PrintWriter writer2 = new PrintWriter(f2, "UTF-8");
			PrintWriter writer3 = new PrintWriter(f3, "UTF-8");
			PrintWriter writer4 = new PrintWriter(f4, "UTF-8");
			writer1.print("1; 12.00.00" + "\n" + "2; 12.00.00" + "\n" + "3; 12.00.00" + "\n" + "4; 12.00.00" + "\n"
					+ "5; 12.00.00");
			writer2.print("2; 12.21.00" + "\n" + "4; 12.23.00" + "\n" + "5; 12.24.00" + "\n" + "3; 12.43.00" + "\n"
					+ "1; 13.00.00" + "\n" + "6; 13.00.01");
			writer3.println("StartNr; Namn" + "\n" + "SENIOR" + "\n" + "1; Anders Asson" + "\n" + "2; Bengt Bsson"
					+ "\n" + "JUNIOR" + "\n" + "3; Chris Csson" + "\n" + "4; David Dsson" + "\n" + "5; Erik Esson"
					+ "\n" + "6; Fredrik Fsson");
			writer4.print("6; 12.00.00");
			writer1.close();
			writer2.close();
			writer3.close();
			writer4.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReaderWith5Entries() throws IOException {
		String start = "12.00.00";
		String[] finish = { "13.00.00", "12.21.00", "12.43.00", "12.23.00", "12.24.00" , "13.00.01"};
		String[] names = { "Anders Asson", "Bengt Bsson", "Chris Csson", "David Dsson", "Erik Esson", "Fredrik Fsson" };
		String[] classes = { "SENIOR", "SENIOR", "JUNIOR", "JUNIOR", "JUNIOR", "JUNIOR" };
		Database db = new Database();
		IOReader.readStart("starttider.txt", db);
		IOReader.readFinish("maltider.txt", db);
		IOReader.readNames("namnfil.txt", db);
		IOReader.readStart("starttider2.txt", db);
		HashMap<Integer, Racer> map = db.getRacers();
		assertEquals(map.size(), 6);
		for (int i : map.keySet()) {
			Racer r = map.get(i);
			String s[] = r.toString().split("; ");
			assertEquals(s[0], Integer.toString(i)); //s[0] borde returnera startnummer
			assertEquals(s[1], names[i - 1]); //s[1] borde returnera namn
			System.out.println( s[3] + ", " + start);
			assertEquals(s[3], start); //s[3] borde returnera starttid
			assertEquals(s[4], finish[i - 1]); //s[4] borde returnera sluttid
			assertEquals(r.getRacerClass(), classes[i - 1]); //kollar racerklass
		}
	}

	@Test
	public void testIfFileIsMissing() {
		Database db = new Database();
		try {
			IOReader.readFinish("doesntexist.txt", db);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
