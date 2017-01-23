package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import resultMerge.Database;
import resultMerge.Racer;

public class RegistrationWriter {

	
	public static void write(String path, String registration) {		
		
		try{
			//TODO: make sure we dont delete everything inside the file.
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.println(registration);		
			writer.close();
		} 
		catch(IOException e) {
			System.out.println("File does not exist");		
		}
	}

}
