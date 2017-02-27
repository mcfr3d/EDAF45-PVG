package resultMerge;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigReaderTest {
	private String path = "config.json";
	private String lap = 
			"{\n" + 
			"\t\"race type\" : \"varvlopp\",\n" +
			"\t\"stipulated time\" : \"01.00.01\",\n" +
			"\t\"name file\" : \"namnfil.txt\",\n" +
			"\t\"start files\" : [\"starttider.txt\"],\n" +
			"\t\"finish files\" : [\"maltider1.txt\",\"maltider2.txt\"]\n" +
			"}";
	private String onelap = 
			"{\n" + 
			"\t\"race type\" : \"maraton\",\n" +
			"\t\"name file\" : \"namnfil.txt\",\n" +
			"\t\"start files\" : [\"starttider.txt\"],\n" +
			"\t\"finish files\" : [\"maltider.txt\"]\n" +
			"}";
	private String leg =
			"{\n" + 
			"\t\"race type\" : \"etapplopp\",\n" +
			"\t\"name file\" : \"namnfil.txt\",\n" +
			"\t\"number of etapps\" : 2,\n" +
			"\t\"etapper\" : [\n" +
			"\t{\n" +
			"\t\"minimum time\" : \"00.05.00\",\n" +
			"\t\"multiplier\" : 1,\n" +
			"\t}," +
			"\t{\n" +
			"\t\"minimum time\" : \"00.05.00\",\n" +
			"\t\"multiplier\" : 1,\n" +
			"\t}" +
			"\t]," +
			"\t\"start files\" : [\n" +
			"\t{\"etapp\": 1, \"file\": \"starttider1.txt\"},\n" +
			"\t{\"etapp\": 2, \"file\": \"starttider2.txt\"}],\n" +
			"\t\"finish files\" : [\n" +
			"\t{\"etapp\": 1, \"file\": \"maltider1.txt\"},\n" +
			"\t{\"etapp\": 2, \"file\": \"maltider2.txt\"}]\n" +
			"}";
	@Before
	public void before() {
	}
	
	@After
	public void after() {
	    File f = new File(path);
	    f.delete();
	}
	@Test
	public void testConfigLap() {
		createConfig(lap);
	}
	@Test
	public void testConfigOneLap() {
		createConfig(onelap);
	}
	@Test
	public void testConfigLegs() {
		createConfig(leg);
	}

	private void createConfig(String config) {
		Writer writer = null;
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(path), "utf-8"));
		    writer.write(config);
		} catch (IOException ex) {
		  // report
		} finally {
		   try {writer.close();} catch (Exception ex) {/*ignore*/}
		}
		try {
			ConfigReader.readConfig(path);
		} catch (Exception e) {
		}
	}
}
