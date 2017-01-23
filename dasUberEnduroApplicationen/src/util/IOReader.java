package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IOReader {
	String path;
	BufferedReader br;
	ArrayList<String> info;
	
	
	public IOReader(String path) throws FileNotFoundException {
		this.path = path;
		br = new BufferedReader(new FileReader(path));
		info = new ArrayList<String>();
	}
	
	public ArrayList<String> read() throws FileNotFoundException, IOException {
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		    	
		    	
		    	
		    	
		    	info.add(line);
		        line = br.readLine();
		    }
		    
		    return info;
		}
	}
}
