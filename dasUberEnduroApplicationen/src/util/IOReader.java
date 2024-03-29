package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

	public static void readStart(String path, Database db, int leg)
			throws Exception, FileNotFoundException, IOException {
		Chart c = new Chart(readStringFromFile(path));
		int index = 0;
		try {
			for (List<String> row : c.getRows()) {
				index++;
				if (row.size() != 2)
					throw new IllegalArgumentException("Invalid format: " + row.toString()
							+ " Expected 2 \";\"-separated Strings, found " + row.size());

				String time = row.get(1);
				String numberOrClass = row.get(0);
				try {
					db.addStart(Integer.parseInt(numberOrClass), time, leg);
				} catch (NumberFormatException e) {
					List<Integer> racersInClass = db.getRacersInClass(numberOrClass);
					if(racersInClass.size() == 0) {
						System.err.println("Found no racers in class " + numberOrClass);
						System.err.println("while parsing line " + index + " in file " + path);
					}
					for (int startNumber : racersInClass)
						db.addStart(startNumber, time, leg);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("while parsing line " + index + " in file " + path);
			System.exit(1);
		}
	}

	public static void readFinish(String path, Database db) throws Exception, FileNotFoundException, IOException {
		readFinish(path, db, -1);
	}

	public static void readFinish(String path, Database db, int leg)
			throws Exception, FileNotFoundException, IOException {
		Chart c = new Chart(readStringFromFile(path));
		int index = 0;
		try {
			for (List<String> row : c.getRows()) {
				index++;
				if (row.size() != 2)
					throw new IllegalArgumentException("Invalid format: " + row.toString()
							+ " Expected 2 \";\"-separated Strings, found " + row.size());
				int id;
				try {
					id = Integer.parseInt(row.get(0));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Expected starnumber, found:" + row.get(0));
				}
				db.addFinish(id, row.get(1), leg);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("while parsing line " + index + " in file " + path);
			System.exit(1);
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
