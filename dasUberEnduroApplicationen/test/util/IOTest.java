package util;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class IOTest {

	@Test
	public void testWriteOneLine() {
		String tmp = "1; Emil Wihlander; 0.00.00; 0.00.00; 0.00.00";
		IO io = new IO();
		io.setLocation("output.uber");
		io.write(tmp);
		
		List<String> ls = Files.readAllLines(Paths.get("./output.uber"));
		assertEquals(ls.size(),1);
		assertEquals(ls.get(0), tmp);
		
	}

	@Test
	public void testWriteTwoLines() {
		String tmp1 = "1; Emil Wihlander; 0.00.00; 0.00.00; 0.00.00";
		String tmp2 = "2; Emil Wihlander; 0.00.00; 0.00.00; 0.00.00";
		IO io = new IO();
		io.setLocation("output.uber");
		io.write(tmp1);
		io.write(tmp2);
		
		List<String> ls = Files.readAllLines(Paths.get("./output.uber"));
		assertEquals(ls.size(),2);
		assertEquals(ls.get(0), tmp1);
		assertEquals(ls.get(1), tmp2);
	}
	
	@Test
	public void testWriteNoLines() {
		String tmp = "1; Emil Wihlander; 0.00.00; 0.00.00; 0.00.00";
	}
}
