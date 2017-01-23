package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import resultMerge.Database;
import resultMerge.Racer;

public class ResultWriter {

	
	public static void write(String path, Database db) {
		
		HashMap<Integer, Racer> map = db.getRacers();
		
		try{
		
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.println("StartNr; Namn; Totaltid; Starttid; Måltid");
		
			for(Map.Entry<Integer,Racer> entry : map.entrySet()) {
			
				String s = entry.getValue().toString();
				
				System.out.println(s);
				
				writer.println(s);
			}
			
			writer.close();
		
		} 
		catch(IOException e) {
			
			
		}
		
	}

}
