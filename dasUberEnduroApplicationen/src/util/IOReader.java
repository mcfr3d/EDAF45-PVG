package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resultMerge.Database;

public class IOReader {

	private static List<String> read(String path) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		List<String> info = new ArrayList<String>();
		String line = br.readLine();
		while (line != null) {
			info.add(line);
			line = br.readLine();
		}
		br.close();
		return info;
	}

	public static void readStart(String startPath, Database db) throws FileNotFoundException, IOException {
		List<String> start = read(startPath);

		for (String s : start) {
			int firstDelimiter = s.indexOf(";");
			db.addStart(Integer.parseInt(s.substring(0, firstDelimiter)), s.substring(firstDelimiter + 2));
		}
	}

	public static void readFinish(String finishPath, Database db) throws FileNotFoundException, IOException {
		List<String> finish = read(finishPath);

		for (String s : finish) {
			int firstDelimiter = s.indexOf(";");
			db.addFinish(Integer.parseInt(s.substring(0, firstDelimiter)), s.substring(firstDelimiter + 2));
		}
	}

	public static void readNames(String namePath, Database db) throws FileNotFoundException, IOException {
		List<String> nameList = read(namePath);
		
		String currentClass = "Default";
		for(int i = 1; i < nameList.size(); i++) {
			String s = nameList.get(i);
			if (Character.isDigit(s.charAt(0))) {
				int firstDelimiter = s.indexOf(";");
				db.setName(Integer.parseInt(s.substring(0, firstDelimiter)), s.substring(firstDelimiter + 2));
				db.setClass(Integer.parseInt(s.substring(0, firstDelimiter)), currentClass);
			} else {
				currentClass = s;
			}
		}
	}
}
