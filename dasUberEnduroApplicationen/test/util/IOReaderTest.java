package util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import resultMerge.Database;
import resultMerge.Racer;

public class IOReaderTest {

	@Test
	public void testWriteOneLine() throws IOException{
		Database db = new Database();
		IOReader.readTimes("starttider.txt", "maltider.txt", db);
		HashMap<Integer, Racer> map= db.getRacers();
		for (int i : map.keySet()) {
			Racer r = map.get(i);
		}
		assertEquals()
	}

	@Test
	public void testWriteTwoLines() throws IOException {
		String tmp1 = "1; Emil Wihlander; 0.00.00; 0.00.00; 0.00.00";
		String tmp2 = "2; Emil Wihlander; 0.00.00; 0.00.00; 0.00.00";
		IO io = new IO();
		IOReader ior = new IOReader();
		io.setPath("output.uber");
		ior.setPath("output.uber");
		io.initFile();
		io.write(tmp1);
		io.write(tmp2);
		
		String[] ls = ior.read();
		assertEquals(ls.length,2);
		assertEquals(ls[1], tmp1);
		assertEquals(ls[1], tmp2);
	}
	
	@Test
	public void testWriteNoLines() throws IOException {
		String header = "StartNr; Namn; Totaltid; Starttid; Måltid";
		
		IO io = new IO();
		IOReader ior = new IOReader();
		io.setPath("output.uber");
		ior.setPath("output.uber");
		io.initFile();
		
		
		String[] ls = ior.read();
		assertEquals(ls.length,0);
	}
}
