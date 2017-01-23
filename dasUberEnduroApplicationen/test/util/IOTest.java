package util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class IOTest {

	@Test
	public void testWriteOneLine() throws IOException{
		String header = "StartNr; Namn; Totaltid; Starttid; Måltid";
		String tmp = "1; Emil Wihlander; 0.00.00; 0.00.00; 0.00.00";
		IO io = new IO();
		io.setPath("output.uber");
		io.initFile();
		io.write(tmp);
		
		List<String> ls = Files.readAllLines(Paths.get("./output.uber"));
		assertEquals(ls.size(),2);
		assertEquals(ls.get(0), header);
		assertEquals(ls.get(1), tmp);
		
	}

	@Test
	public void testWriteTwoLines() throws IOException {
		String tmp1 = "1; Emil Wihlander; 0.00.00; 0.00.00; 0.00.00";
		String tmp2 = "2; Emil Wihlander; 0.00.00; 0.00.00; 0.00.00";
		IO io = new IO();
		io.setPath("output.uber");
		io.initFile();
		io.write(tmp1);
		io.write(tmp2);
		
		List<String> ls = Files.readAllLines(Paths.get("./output.uber"));
		assertEquals(ls.size(),3);
		assertEquals(ls.get(1), tmp1);
		assertEquals(ls.get(2), tmp2);
	}
	
	@Test
	public void testWriteNoLines() throws IOException {
		String header = "StartNr; Namn; Totaltid; Starttid; Måltid";
		
		IO io = new IO();
		io.setPath("output.uber");
		io.initFile();
		
		List<String> ls = Files.readAllLines(Paths.get("./output.uber"));
		assertEquals(ls.size(),1);
		assertEquals(ls.get(0), header);
	}
}
