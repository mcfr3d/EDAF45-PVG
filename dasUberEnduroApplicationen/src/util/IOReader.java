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
			if(firstDelimiter != -1) {
				db.addStart(Integer.parseInt(s.substring(0, firstDelimiter)), s.substring(firstDelimiter + 2));
			} else if(!s.equals("")) {
				System.err.println("feeel: " + s);
				throw new IOException(s);
			}
		}
	}

	public static void readFinish(String finishPath, Database db) throws FileNotFoundException, IOException {
		List<String> finish = read(finishPath);

		for (String s : finish) {
			int firstDelimiter = s.indexOf(";");
			db.addFinish(Integer.parseInt(s.substring(0, firstDelimiter)), s.substring(firstDelimiter + 2));
		}
	}

	public static void readNames(String path, Database db) throws Exception {

		ArrayList<String> lines = new ArrayList<String>(read(path));

		String currentClass = "";

		String[] columnHeaders = lines.get(0).split("\\;\\ ");

		if (columnHeaders.length < 2)
			throw new Exception("Syntax error");

		db.setColumnHeaders(columnHeaders);

		for (int i = 1; i < lines.size(); i++) {

			String[] words = lines.get(i).split("\\;\\ ");

			if (words.length == 1) {

				currentClass = words[0];

			} else {

				if (words.length != columnHeaders.length)
					throw new Exception("Syntax error");

				int racerIndex = Integer.parseInt(words[0]);
				String racerName = words[1];
				db.addRacer(racerIndex, racerName, currentClass);

				for (int j = 2; j < columnHeaders.length; ++j) {

					db.addOptionalData(racerIndex, words[j]);
				}
			}

		}

	}
}
