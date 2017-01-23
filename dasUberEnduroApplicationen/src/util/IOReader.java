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
		return info;
	}

	public static void readTimes(String startPath, String finishPath, Database db)
			throws FileNotFoundException, IOException {
		List<String> start = read(startPath);
		List<String> finish = read(finishPath);

		for (String s : start) {
			int firstDelimiter = s.indexOf(";");
			db.addStart(Integer.parseInt(s.substring(0, firstDelimiter)), s.substring(firstDelimiter + 2));
		}
		for (String s : finish) {
			int firstDelimiter = s.indexOf(";");
			db.addFinish(Integer.parseInt(s.substring(0, firstDelimiter)), s.substring(firstDelimiter + 2));
		}
	}
}
