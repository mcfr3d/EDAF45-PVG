package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import resultMerge.Database;

public class IOReader {

	private static String readStringFromFile(String path) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String s = "";
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			s += line + "\n";
		}
		br.close();
		return s;
	}
	
	public static void readStart(String path, Database db) throws Exception, FileNotFoundException, IOException {
		readStart(path, db, -1);
	}
	
	public static void readStart(String path, Database db, int leg) throws Exception, FileNotFoundException, IOException {
		Chart c = new Chart(readStringFromFile(path));
		int index = 0;
		try{
		for (List<String> row : c.getRows()) {
			index++;
			if (row.size() != 2)
				throw new Exception("ERROR: Invalid start time at row " + index + ": \n" + row.toString());

			String time = row.get(1);
			String numberOrClass = row.get(0);
			try{
			   db.addStart(Integer.parseInt(numberOrClass), time, leg);
			}
			catch(NumberFormatException e) {
				for(int startNumber : db.getRacersInClass(numberOrClass))
					db.addStart(startNumber, time, leg);
			}
			
		}
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
			
		}
	}
	
	
	
	public static void readFinish(String path, Database db) throws Exception, FileNotFoundException, IOException {
		readFinish(path, db, -1);
	}
	
	public static void readFinish(String path, Database db, int leg) throws Exception, FileNotFoundException, IOException {
		Chart c = new Chart(readStringFromFile(path));
		for (List<String> row : c.getRows()) {
			if (row.size() != 2)
				throw new Exception("invalid finish time row");
			db.addFinish(Integer.parseInt(row.get(0)), row.get(1), leg);
		}
	}


	public static void readNames(String path, Database db) throws Exception {
		Chart c = new Chart(readStringFromFile(path));
		List<List<String>> rows = c.getRows();
		String currentClass = "";
		if (rows.get(0).size() < 2)
			throw new Exception("need atleast 2 columns in header row");
		db.setColumnHeaders(rows.get(0).toArray(new String[0]));
		for (int i = 1; i < rows.size(); i++) {
			List<String> row = rows.get(i);
			if (row.size() == 1) {
				currentClass = row.get(0);
			} else {
				if (row.size() != rows.get(0).size())
					throw new Exception("Syntax error");
				int racerIndex = Integer.parseInt(row.get(0));
				String racerName = row.get(1);
				db.addRacer(racerIndex, racerName, currentClass);
				for (int j = 2; j < rows.get(0).size(); ++j) {
					db.addOptionalData(racerIndex, row.get(j));
				}
			}
		}
	}

}
