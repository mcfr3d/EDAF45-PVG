package util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class ResultWriterTest {

	@Test
	public void testEmptyDatabase() {
		fail("Not yet implemented");
	}

	@Test
	public void testDatabaseOneEntry() throws IOException {
		Database db = new Database();
		
		List<String> ls = Files.readAllLines(Paths.get("./output.txt"));
		assertEquals(ls.size(),2);
		assertEquals(ls.get(1), "StartNr; Namn; Totaltid; Starttid; Måltid");
		assertEquals(ls.get(2), null);
	}

	@Test
	public void testNoDatabase() {
		ResultWriter.write("output.txt", null);
		
	}
}
