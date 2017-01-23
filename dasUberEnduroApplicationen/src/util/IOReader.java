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
			int racerId = Integer.parseInt(s.substring(0, firstDelimiter));
			String time = s.substring(firstDelimiter + 2);
			db.addStart(racerId, time);
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
		List<String> names = read(namePath);

		for(int i = 1; i < names.size(); i++) {
			String s = names.get(i);
			int firstDelimiter = s.indexOf(";");
			db.setName(Integer.parseInt(s.substring(0, firstDelimiter)), s.substring(firstDelimiter + 2));
		}
	}
}
