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

public class ChartTest {

	String testData1;
	String testData2;
	String testData3;
	
	public ChartTest() {
		
		testData1 = "a; b; c\n";
		testData1 += "a; b; c; d\n";
		testData1 += "a; b; c; abc\n";
		testData1 += "a; b; c\n";
		
		testData2 = "a  ;  b; c \n";
		testData2 += "a  ; b  ; c; d   \n";
		testData2 += "   a; b;   c;   abc  \n";
		testData2 += "a ;    b  ; c\n";
		
		testData3 = "a; b; c\r\n";
		testData3 += "a; b; c; d\r\n";
		testData3 += "a; b; c; abc\r\n";
		testData3 += "a; b; c\r\n";
	}
	
	
	@Test
	public void testFromString() {

		Chart c = new Chart();
		c.fromString(testData1);
		assertEquals(testData1,c.toString());
	}
	@Test
	public void testWhitespace() {

		Chart c = new Chart();
		c.fromString(testData2);
		assertEquals(testData1,c.toString());
	}
	@Test
	public void testWindowsNewline() {

		Chart c = new Chart();
		c.fromString(testData3);
		assertEquals(testData1,c.toString());
	}
	
}
