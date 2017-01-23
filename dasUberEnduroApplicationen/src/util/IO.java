package util;

import java.io.IOException;
import java.io.PrintWriter;

public class IO {
	private String path;
	
	public IO() {
		
	}
	
	public void write(String s) {
		if (path == null) {
			System.exit(1);
		}
		try{
		    PrintWriter writer = new PrintWriter(path, "UTF-8");
		    writer.println(s);
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}
	
	public void initFile() {
		write("StartNr; Namn; Totaltid; Starttid; Måltid");
	}
	
	public void setPath(String s) {
		path = s;
	}
}
