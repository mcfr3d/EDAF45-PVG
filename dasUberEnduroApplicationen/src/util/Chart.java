package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Chart {

	private List<List<String>> rows;

	public Chart() {

		rows = new ArrayList<>();
	}
	public Chart(String s) {
		this();
		fromString(s);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (List<String> row : rows) {
			for (String word : row) {
				sb.append(word);
				sb.append("; ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append("\n");
		}
		return sb.toString();
	}

	public void fromString(String s) {
		String lines[] = s.split("\\r?\\n");
		for (int i = 0; i < lines.length; ++i) {
			String[] words = lines[i].split(";");
			List<String> l = new ArrayList<>();
			for (int j = 0; j < words.length; j++)
				l.add(words[j].trim());
			rows.add(l);
		}
	}

	public List<List<String>> getRows() {
		
		return rows;
	}
	
	public String get(int row, int col) {

		return "";
	}

	public void set(int row, int col, String s) {

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

}
