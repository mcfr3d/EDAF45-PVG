package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resultMerge.Database;

public class IOReader {

	
	private static String readStringFromFile(String path) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String s = "";
		while(true) {
			String line = br.readLine();
			if(line == null) break;
			s += line + "\n";
		}
		br.close();
		return s;
	}
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

	public static void readStart(String startPath, Database db) throws Exception, FileNotFoundException, IOException {		
		Chart c = new Chart(readStringFromFile(startPath));
		for(List<String> row : c.getRows()) {
			if(row.size() != 2) throw new Exception("invalid start time row");
			db.addStart(Integer.parseInt(row.get(0)), row.get(1));
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

		String[] columnHeaders = lines.get(0).split(";");
		if (columnHeaders.length < 2)
			throw new Exception("Syntax error");
		for(int i = 0; i<columnHeaders.length; i++)
			columnHeaders[i] = columnHeaders[i].trim();

		db.setColumnHeaders(columnHeaders);

		for (int i = 1; i < lines.size(); i++) {

			String[] words = lines.get(i).split(";");
			for(int j = 0; j<words.length; j++)
				words[j] = words[j].trim();
				

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
