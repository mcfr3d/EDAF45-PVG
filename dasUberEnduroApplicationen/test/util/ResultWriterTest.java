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
	public void testEmptyDatabase() {
		fail("Not yet implemented");
	}

	@Test
	public void testDatabaseOneEntry() throws IOException {
		
		Database db = new Database();
		
		String path = "output.txt";
		db.addStart(1, "00:01:00");
		db.addFinish(1, "00:01:01");

		ResultWriter.write(path, db);
		
		List<String> ls = Files.readAllLines(Paths.get(path));
		assertEquals(ls.size(),2);
		assertEquals(ls.get(0), "StartNr; Namn; Totaltid; Starttid; Måltid");
		assertEquals(ls.get(1), "1; 00:01:00; 00:01:01");
	}

	@Test
	public void testNoDatabase() {
		ResultWriter.write("output.txt", null);
		
	}
}
